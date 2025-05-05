package com.assookkaa.ClassRecord.Service.Grading;

import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingDetailRequest;
import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingRequest;
import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingUpdateRequest;
import com.assookkaa.ClassRecord.Dto.Response.Grading.*;
import com.assookkaa.ClassRecord.Dto.Response.Grading.Category.GradesPerCategory;
import com.assookkaa.ClassRecord.Entity.*;
import com.assookkaa.ClassRecord.Repository.*;
import com.assookkaa.ClassRecord.Service.Grading.Interface.GradingInterface;
import com.assookkaa.ClassRecord.Utils.Objects.Grading.GradingFunc;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GradingService implements GradingInterface {


    private final GradingFunc gradingFunc;
    private final GradingRepository gradingRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final GradingDetailRepository gradingDetailRepository;

    public GradingService(GradingFunc gradingFunc, GradingRepository gradingRepository, EnrollmentRepository enrollmentRepository, GradingDetailRepository gradingDetailRepository) {
        this.gradingFunc = gradingFunc;
        this.gradingRepository = gradingRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.gradingDetailRepository = gradingDetailRepository;
    }

    @Override
    @Transactional
    public GradingResponse addActivity(GradingRequest gradingRequest) {
        TeachingLoadDetails teachingLoadDetails =gradingFunc.findTeachingLoadDetailId(gradingRequest.getTeachingLoadDetailId());
        GradeCategory category = gradingFunc.findGradeCategory(gradingRequest.getCategoryId());
        Term term = gradingFunc.findTermById(gradingRequest.getTermId());

        Grading grading = gradingFunc.buildActivity(gradingRequest,teachingLoadDetails,category,term);

        grading = gradingRepository.save(grading);

//        gradingFunc.batchGradingResponse(grading);

//        List<Enrollments> enrollees = enrollmentRepository.findByTeachingLoadDetailId(teachingLoadDetails.getId());
//        List<GradingDetail> gradingDetails = new ArrayList<>();
//
//        for (Enrollments enrollment : enrollees) {
//            GradingDetail gradingDetail = gradingFunc.inputStudentInTheScoreSheetRecord(
//                    new GradingDetailRequest(),grading, enrollment);
//            gradingDetails.add(gradingDetail);
//        }
//
//        gradingDetailRepository.saveAll(gradingDetails);

//        List<GradingDetailsResponse> detailsResponses = gradingDetails.stream()
//                .map(gradingFunc::gradingDetailResponse)
//                .toList();

        return gradingFunc.batchGradingResponse(grading);
    }

    @Override
    public UpdatedScoreResponse editRecordedScore(GradingUpdateRequest gradingUpdateRequest) {

        GradingDetail update = gradingFunc.updateScoreOfStudent(gradingUpdateRequest);

        return new UpdatedScoreResponse("Score Updated", update.getScore());
    }

    @Override
    public BatchGradingDetailsResponse recordScore(List<GradingDetailRequest> gradingDetailRequests) {
        List <GradingDetail> gradingDetails = new ArrayList<>();

        for(GradingDetailRequest dto : gradingDetailRequests) {

            Optional<GradingDetail>  gradingDetailExisting = gradingDetailRepository.findByGradingIdAndEnrollmentsId(
                    dto.getGradingId(),dto.getEnrollmentId());
            if (gradingDetailExisting.isPresent()){
                throw new IllegalArgumentException("Grading detail already exists");
            }
            
            Grading grading = gradingRepository.findById(dto.getGradingId()).orElseThrow(()->new IllegalArgumentException("Not Found Record"));

            Enrollments enrolled = enrollmentRepository.findById(dto.getEnrollmentId()).orElseThrow(()-> new IllegalArgumentException("Not Found Fool"));

            GradingDetail gradingDetail = new GradingDetail();
            gradingDetail.setEnrollments(enrolled);
            gradingDetail.setGrading(grading);
            gradingDetail.setScore(dto.getScore());
            gradingDetails.add(gradingDetail);
        }
        gradingDetailRepository.saveAll(gradingDetails);

        return BatchGradingDetailsResponse.builder()
                .message("Grade Successfully Recorded")
                .details(gradingDetails.stream()
                        .map(gradingFunc::gradingDetailResponse)
                        .collect(Collectors.toList()))
                .build();
    }


    public List <GradingDetailsResponse> gradingDetailsResponseList (Integer gradingId){
        List<GradingDetail> gradingDetails = gradingDetailRepository.findByGradingId(gradingId);

        return gradingDetails.stream()
                .map(gradingFunc::gradingDetailResponse)
                .collect(Collectors.toList());
    }
////////////////////////////
    @Override
    public List<GradeComputation> calculateEachTermGrade(Integer teachingLoad, List<Enrollments> enrollments, Term termId) {

//        List<GradingDetail> gradingDetails = gradingFunc.getFilteredListOfStudents(enrollments, termId);

        Map<Integer, Double> categoryWeights = gradingFunc.getCategoryWeights(teachingLoad);

        List <Grading> gradings = gradingRepository.findByTeachingLoadDetailsIdAndTermId(teachingLoad, termId.getId());

        return enrollments.stream()
                .map(e -> gradingFunc.computeTermGrades(e, gradings, termId, categoryWeights))
                .collect(Collectors.toList());
    }


    @Override
    public List<SemesterGradeComputation> calculateSemGrade(Integer teachingLoadDetailId, List<Enrollments> enrollments) {
        // Get the terms for semester calculation
        Term term1 = gradingFunc.findTermById(1);
        Term term2 = gradingFunc.findTermById(2);

        // Calculate the term grades synchronously
        List<GradeComputation> term1Grades = calculateEachTermGrade(teachingLoadDetailId, enrollments, term1);
        List<GradeComputation> term2Grades = calculateEachTermGrade(teachingLoadDetailId, enrollments, term2);

        // Compute semester grade by combining term grades
        return term1Grades.stream()
                .map(term1Grade -> {
                    BigDecimal term1FinalGrade = term1Grade.getFinalGrade();

                    BigDecimal term2FinalGrade = term2Grades.stream()
                            .filter(g -> g.getStudentId().equals(term1Grade.getStudentId()))
                            .map(GradeComputation::getFinalGrade)
                            .findFirst()
                            .orElse(BigDecimal.ZERO);

                    BigDecimal finalGradeForSemester = term1FinalGrade.add(term2FinalGrade)
                            .divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP);

                    String remarks = finalGradeForSemester.doubleValue() <= 74 ? "Failed" : "Passed";

                    return new SemesterGradeComputation(
                            term1Grade.getStudentId(),
                            term1Grade.getName(),
                            Map.of(
                                    term1.getTerm_type(), term1Grade.getFinalGrade(),
                                    term2.getTerm_type(), term2FinalGrade
                            ),
                            finalGradeForSemester,
                            remarks
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public GradesPerCategory getGradesPerCategory(Integer teachingLoadDetailId, Integer termId, Integer categoryId) {

        List<GradingDetail> gradeList = gradingDetailRepository.findGradesPerCategory(teachingLoadDetailId, termId, categoryId);

        if (gradeList.isEmpty()) {
            return null;  // Or handle this case as needed
        }
        List<GradesPerCategory.Items> items = new ArrayList<>();

        for (GradingDetail gradingDetail : gradeList) {
            GradesPerCategory.Items item = new GradesPerCategory.Items();
            item.setDesc(gradingDetail.getGrading().getDescription()); // Assume GradingDetail has a 'description' field
            item.setTotalItem(gradingDetail.getGrading().getNumberOfItems()); // Assume GradingDetail has 'totalItems'
            item.setDateConducted(gradingDetail.getGrading().getDateConducted()); // Assume GradingDetail has 'dateConducted'

            // Convert the scores
            List<GradesPerCategory.Score> scores = new ArrayList<>();
            for (GradingDetail scoreDetail : gradingDetail.getGrading().getGradingDetails()) {  // Assume GradingDetail has getScores() method
                GradesPerCategory.Score score = new GradesPerCategory.Score();
                score.setGradingDetailId(scoreDetail.getId()); // Assume ScoreDetail has an 'id'
                score.setEnrollmentId(scoreDetail.getEnrollments().getId()); // Assume ScoreDetail has 'enrollmentId'
                score.setScore(BigDecimal.valueOf(scoreDetail.getScore())); // Assume ScoreDetail has 'score'
                score.setRecordedOn(scoreDetail.getRecordedOn()); // Assume ScoreDetail has 'recordedOn'
                scores.add(score);
            }
            item.setScore(scores);
            items.add(item);
        }

        // Return the populated GradesPerCategory object
        return new GradesPerCategory(teachingLoadDetailId, termId, categoryId, items);
    }


}

//public GradesPerCategory getGradesPerCategory(Integer teachingLoadDetailId, Integer termId, Integer categoryId) {
//    System.out.println("TLD ID: " + teachingLoadDetailId + ", Term ID: " + termId + ", Category ID: " + categoryId);
//    List <Grading> gradingItems = gradingRepository.findByTeachingLoadDetailsIdAndTermIdAndCategoryId(
//            teachingLoadDetailId, termId, categoryId);
//    if (gradingItems.isEmpty()) {
//        throw new IllegalArgumentException("It does not exist");
//    }
//
//    List <Enrollments> student = enrollmentRepository.findByTeachingLoadDetailId(teachingLoadDetailId);
//
//    GradesPerCategory response = new GradesPerCategory();
//    response.setTeachingLoadDetailId(teachingLoadDetailId);
//    response.setTermId(termId);
//    response.setCategoryId(categoryId);
//
//    List<GradesPerCategory.Items> itemsMap = gradingItems.stream()
//            .map(
//                    gradingItem->{
//                        GradesPerCategory.Items item = new GradesPerCategory.Items();
//                        item.setDesc(gradingItem.getDescription());
//                        item.setTotalItem(gradingItem.getNumberOfItems());
//                        item.setDateConducted(gradingItem.getDateConducted());
//
//                        List <GradesPerCategory.Score> scores = student.stream()
//                                .map(enrollment ->{
//                                    Optional<GradingDetail> gradingDetail = gradingDetailRepository.findByGradingIdAndEnrollmentsId(gradingItem.getId(),enrollment.getId());
//
//                                    GradesPerCategory.Score score = new GradesPerCategory.Score();
//                                    score.setEnrollmentId(enrollment.getId());
//
//                                    if (gradingDetail.isPresent()) {
//                                        score.setGradingDetailId(gradingDetail.get().getId());
//                                        score.setScore(BigDecimal.valueOf(gradingDetail.get().getScore()));
//                                        score.setRecordedOn(gradingDetail.get().getRecordedOn());
//                                    } else {
//                                        score.setGradingDetailId(0);
//                                        score.setScore(BigDecimal.ZERO);
//                                        score.setRecordedOn(null);
//                                    }
//                                    return score;
//                                }).collect(Collectors.toList());
//                        item.setScore(scores);
//                        return item;
//                    }).toList();
//    response.setGrades(gradingItems);
//    return response;
//}
