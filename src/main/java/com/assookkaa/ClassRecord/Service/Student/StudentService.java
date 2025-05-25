package com.assookkaa.ClassRecord.Service.Student;

import com.assookkaa.ClassRecord.Config.Filter.JwtUtil;
import com.assookkaa.ClassRecord.Dto.Response.Grading.Category.GradesPerCategory;
import com.assookkaa.ClassRecord.Dto.Response.Grading.GradeComputation;
import com.assookkaa.ClassRecord.Dto.Response.Grading.GradingResponse;
import com.assookkaa.ClassRecord.Dto.Response.Grading.SemesterGradeComputation;
import com.assookkaa.ClassRecord.Dto.Response.Students.StudentsSubjectList;
import com.assookkaa.ClassRecord.Entity.*;
import com.assookkaa.ClassRecord.Repository.*;
import com.assookkaa.ClassRecord.Utils.Objects.Grading.GradingFunc;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {

    private final GradingFunc gradingFunc;
    private final JwtUtil jwtUtil;
    private final EnrollmentRepository enrollmentRepository;
    private final TeachingLoadDetailsRespository teachingLoadDetailsRespository;
    private final GradingRepository gradingRepository;
    private final GradingDetailRepository gradingDetailRepository;


    public StudentService(GradingFunc gradingFunc,
                          JwtUtil jwtUtil,
                          EnrollmentRepository enrollmentRepository,
                          TeachingLoadDetailsRespository teachingLoadDetailsRespository,
                          GradingRepository gradingRepository, GradingDetailRepository gradingDetailRepository) {
        this.gradingFunc = gradingFunc;
        this.jwtUtil = jwtUtil;
        this.enrollmentRepository = enrollmentRepository;
        this.teachingLoadDetailsRespository = teachingLoadDetailsRespository;
        this.gradingRepository = gradingRepository;
        this.gradingDetailRepository = gradingDetailRepository;
    }

    public List<StudentsSubjectList> getEnrolledSubjects(String token) {
        // Get student
        String username = jwtUtil.getUsernameFromToken(token);
        Students student = gradingFunc.findStudentByUsername(username);

        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        List<Enrollments> enrollments = enrollmentRepository.findAllByStudentId(student.getId());

        if (enrollments.isEmpty()) {
            throw new RuntimeException("No enrollments found for student");
        }

        List<Integer> enrollmentIds = enrollments.stream()
                .map(Enrollments::getId)
                .toList();

        List<TeachingLoadDetails> teachingLoadDetails =
                teachingLoadDetailsRespository.findByEnrollmentsIdIn(enrollmentIds);

        if (teachingLoadDetails.isEmpty()) {
            throw new RuntimeException("No subjects found for these enrollments");
        }

        // Map to DTO
        return teachingLoadDetails.stream()
                .map(detail -> {
                    TeachingLoad teachingLoad = detail.getTeachingLoad();
                    Subjects subject = detail.getSubject();

                    String teacherFName = teachingLoad.getTeachers().getTeacher().getFname();
                    String teacherLNname = teachingLoad.getTeachers().getTeacher().getLname();

                    String teacherFullName = teacherFName + " " + teacherLNname;

                    return StudentsSubjectList.builder()
                            .teachingLoadDetailId(detail.getId())
                            .subjectName(subject.getSubjectDesc())
                            .subjectDesc(subject.getSubjectName())
                            .status(teachingLoad.getStatus())
                            .academicYear(teachingLoad.getAcademic_year())
                            .semName(teachingLoad.getSem().getSem_name())
                            .teacher(teacherFullName)
                            .section(detail.getSection())
                            .schedule(detail.getSchedule())
                            .build();
                })
                .toList();

    }

    public GradeComputation getGradesPerTerm (String token, Integer teachingLoadDetailId, Integer termId) {
        //find weights match with
        String username = jwtUtil.getUsernameFromToken(token);
        Students students = gradingFunc.findStudentByUsername(username);

        Enrollments enrollments = enrollmentRepository
                .findByStudentIdAndTeachingLoadDetailId(students.getId(), teachingLoadDetailId);
        if (enrollments == null) {
            throw new RuntimeException("Enrollment not found ");
        }
        System.out.println(enrollments.getId());
        //find term
        Term term = gradingFunc.findTermById(termId);

        Map<Integer, Double> catWeights = gradingFunc.getCategoryWeights(teachingLoadDetailId);

        //find grading and its cat
        List <Grading> gradings = gradingRepository.findByTeachingLoadDetailsIdAndTermId(teachingLoadDetailId, termId);

        return gradingFunc.computeEachStudentGrade(
                enrollments,
                gradings,
                term,
                catWeights
        );
    }

    public SemesterGradeComputation getGradesPerSem (String token, Integer teachingLoadDetailId) {
        String username = jwtUtil.getUsernameFromToken(token);
        Students students = gradingFunc.findStudentByUsername(username);

        enrollmentRepository.findByStudentIdAndTeachingLoadDetailId(students.getId(), teachingLoadDetailId);
        Term term1 = gradingFunc.findTermById(1);
        Term term2 = gradingFunc.findTermById(2);

       GradeComputation term1Grade = this.getGradesPerTerm(token, teachingLoadDetailId, term1.getId());
       GradeComputation term2Grade = this.getGradesPerTerm(token, teachingLoadDetailId, term2.getId());

        return getSemesterGradeComputation(students, term1Grade, term2Grade);
    }
    //static
    private static SemesterGradeComputation getSemesterGradeComputation(Students students, GradeComputation term1Grade, GradeComputation term2Grade) {
        SemesterGradeComputation res = new SemesterGradeComputation();
        res.setStudentId(students.getStudentId());
        res.setStudentName(students.getStudent().getFname() + " " + students.getStudent().getLname());

        Map<String, BigDecimal> termGrades = new HashMap<>();
        termGrades.put("Midterm", term1Grade.getFinalGrade());
        termGrades.put("Final", term2Grade.getFinalGrade());
        res.setTermGrades(termGrades);
        BigDecimal semGrade = term1Grade.getFinalGrade().add(term2Grade.getFinalGrade())
                .divide(BigDecimal.valueOf(2),2, RoundingMode.HALF_UP);
        res.setSemesterGrade(semGrade);

        String message = semGrade.compareTo(BigDecimal.valueOf(74)) <= 0 ? "Failed" : "Passed";
        res.setMessage(message);
        return res;
    }

    public List<GradesPerCategory> getScorePerCat (String token,
                                           Integer teachingLoadDetailId,
                                           Integer termId,
                                           Integer catId) {
        String username = jwtUtil.getUsernameFromToken(token);
        Students student = gradingFunc.findStudentByUsername(username);
        Enrollments enrollment =enrollmentRepository.findByStudentIdAndTeachingLoadDetailId(student.getId(), teachingLoadDetailId);
        Term term = gradingFunc.findTermById(termId);
        GradeCategory cat = gradingFunc.findGradeCategory(catId);

        return gradingDetailRepository.findGradesPerCategoryAndStudent(teachingLoadDetailId, term.getId(), cat.getId(), enrollment.getId());
    }


}
