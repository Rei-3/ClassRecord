package com.assookkaa.ClassRecord.Utils.Objects.Grading;

import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingDetailRequest;
import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingRequest;
import com.assookkaa.ClassRecord.Dto.Request.Grading.GradingUpdateRequest;
import com.assookkaa.ClassRecord.Dto.Response.Grading.GradeComputation;
import com.assookkaa.ClassRecord.Dto.Response.Grading.GradingDetailsResponse;
import com.assookkaa.ClassRecord.Dto.Response.Grading.GradingResponse;
import com.assookkaa.ClassRecord.Entity.*;
import com.assookkaa.ClassRecord.Repository.*;
import com.assookkaa.ClassRecord.Utils.Interface.Grading.GradingFuncInterface;
import com.assookkaa.ClassRecord.Utils.Objects.Super;
import com.assookkaa.ClassRecord.Utils.Token.TokenDecryption;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class GradingFunc extends Super implements GradingFuncInterface {


    private final GradingDetailRepository gradingDetailRepository;
    private final GradingCompositionRepository gradingCompositionRepository;
    private final GradeCategoryRepository gradeCategoryRepository;
    private final EnrollmentRepository enrollmentRepository;

    public GradingFunc(TeacherRepository teacherRepository, StudentRepository studentRepository, SubjectsRepository subjectsRepository, SemRepository semRepository,  TeachingLoadDetailsRespository teachingLoadDetailsRespository, TermRepository termRepository, TokenDecryption tokenDecryption, GradingDetailRepository gradingDetailRepository, GradingCompositionRepository gradingCompositionRepository, GradeCategoryRepository gradeCategoryRepository, EnrollmentRepository enrollmentRepository) {
        super(teacherRepository, studentRepository, subjectsRepository, semRepository, gradeCategoryRepository, teachingLoadDetailsRespository, termRepository, enrollmentRepository, tokenDecryption);
        this.gradingDetailRepository = gradingDetailRepository;
        this.gradingCompositionRepository = gradingCompositionRepository;
        this.gradeCategoryRepository = gradeCategoryRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public Grading buildActivity(GradingRequest dto,
                                TeachingLoadDetails teachingLoadDetails,
                                GradeCategory category,
                                Term term)
    {
        return Grading.builder()
                .teachingLoadDetails(teachingLoadDetails)
                .description(dto.getDescription())
                .numberOfItems(dto.getNumberOfItems())
                .category(category)
                .term(term)
                .dateConducted(new Date())
                .build();
    }

    @Override
    public GradingDetail inputStudentInTheScoreSheetRecord(GradingDetailRequest dto,
                                                           Grading grading,
                                                           Enrollments enrollments)
    {
        return GradingDetail.builder()
                .score(dto.getScore())
                .grading(grading)
                .enrollments(enrollments)
                .build();
    }

    @Override
    public GradingDetail updateScoreOfStudent(GradingUpdateRequest dto) {

        GradingDetail gradingDetail = gradingDetailRepository.findById(dto.getGradingDetailId())
                .orElseThrow(() -> new RuntimeException("GradingDetail not found"));

        gradingDetail.setScore(dto.getScore());
        return gradingDetailRepository.save(gradingDetail);
    }

    @Override
    public GradingResponse batchGradingResponse(Grading grading) {
        return GradingResponse.builder()
                .id(grading.getId())
                .numberOfItems(grading.getNumberOfItems())
                .desc(grading.getDescription())
                .catId(grading.getCategory().getId())
                .teachingLoadDetailId(grading.getTeachingLoadDetails().getId())
                .termId(grading.getTerm().getId())
                .date(grading.getDateConducted())
                .build();
    }



//    public void batchGradingResponse(Grading grading) {
//        GradingResponse.builder()
//                .id(grading.getId())
//                .numberOfItems(grading.getNumberOfItems())
//                .desc(grading.getDescription())
//                .catId(grading.getCategory().getId())
//                .teachingLoadDetailId(grading.getTeachingLoadDetails().getId())
//                .termId(grading.getTerm().getId())
//                .date(grading.getDateConducted())
//
//                .build();
//    }

    @Override
    public GradingDetailsResponse gradingDetailResponse(GradingDetail gradingDetail) {
        return GradingDetailsResponse.builder()
                .id(gradingDetail.getId())
                .enrollmentId(gradingDetail.getEnrollments().getId())
                .gradingId(gradingDetail.getGrading().getId())
                .score(gradingDetail.getScore())
                .build();
    }

    //Compute

    @Override //Weight of each category
    public Map<Integer, Double> getCategoryWeights(Integer teachingLoadId) {

        List<GradingComposition> compositions = gradingCompositionRepository.findByTeachingLoadDetailId(teachingLoadId);
        if (compositions.isEmpty()) {
            return new HashMap<>(); // Return empty map instead of throwing error
        }

        return compositions.stream()
                .collect(Collectors.toMap(gc -> gc.getCategory().getId(),
                        gc -> gc.getPercentage() / 100));
    }

    //total of items each cat
    private Map<Integer, Integer> calculateTotalItemsByCat(List<Grading>gradings){
        return gradings.stream().collect(
                Collectors.groupingBy(
                        g->g.getCategory().getId(),
                        Collectors.summingInt(Grading::getNumberOfItems)
                ));
    }

    //total score each cat
    private Map<Integer, BigDecimal> calculateStudentsScoresByCat(List<GradingDetail> gradingDetails){
        return gradingDetails.stream()
                .collect(Collectors.groupingBy(
                        gd-> gd.getGrading().getCategory().getId(),
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                gd-> gd.getScore() != null ? BigDecimal.valueOf(gd.getScore()) : BigDecimal.ZERO,
                                BigDecimal::add
                        )
                ));
    }

    @Override
    //filtered student List
    public List<GradingDetail> getFilteredListOfStudents(List<Enrollments> enrollments, Term term) {
        List <Integer> enrollmentId = enrollments.stream()
                .map(Enrollments::getId)
                .toList();

        return gradingDetailRepository.findByEnrollments_IdIn(enrollmentId).stream()
                .filter(gd-> gd.getGrading().getTerm().getId().equals(term.getId()))
                .toList();
    }

    private String getCatName (Integer categoryId){
        return gradeCategoryRepository.findById(categoryId)
                .map(GradeCategory::getCategoryName)
                .orElse("Unknown Cat");
    }

//    public List<GradingDetail> individualStudent (Integer enrollments, Term term){
//            Enrollments enrolled = enrollmentRepository.findById(enrollments).orElseThrow(
//                    () -> new RuntimeException("Enrollment not found")
//            );
//
//            return gradingDetailRepository.findByEnrollmentsId(enrollments).stream()
//                    .filter(gd-> gd.getGrading().getTerm().getId().equals(term.getId()))
//                    .toList();
//    }

    @Override
    public GradeComputation computeTermGrades(Enrollments enrollments, List<Grading> gradings,
                                              Term term, Map<Integer, Double> categoryWeightsLoadId) {

        Integer studentId = enrollments.getStudent().getStudentId();
        String name = enrollments.getStudent().getStudent().getLname() + " "
                + enrollments.getStudent().getStudent().getFname() + " "
                + Optional.ofNullable(enrollments.getStudent().getStudent().getMname())
                .filter(m -> !m.isEmpty())
                .map(m-> m.charAt(0) + ".")
                .orElse("");

        TeachingLoadDetails teachingLoadDetails = enrollments.getTeachingLoadDetail();

        //BASE Grade
        GradeBase gradeBase = teachingLoadDetails.getGradeBase();
        if (gradeBase == null){
            throw new RuntimeException("Grade Base not found");
        }

        //filter by Term
        List<Grading> itemsFilterByTerm = gradings.stream()
                .filter(g->g.getTerm().getId().equals(term.getId()))
                .toList();

        //GD by term
        List <GradingDetail> scoresFilteredByTerm = enrollments.getGradingDetails().stream()
                .filter(gd-> gd.getGrading().getTerm().getId().equals(term.getId()))
                .toList();

        //map total score for each cat
        Map <Integer, Integer> totalItemsPerCat = calculateTotalItemsByCat(itemsFilterByTerm);

        //Base Grade

        //Grade Percentage

        //map out total score of all cat
        Map <Integer, BigDecimal> totalScoresPerCat = calculateStudentsScoresByCat(scoresFilteredByTerm);

        Map <String, BigDecimal>categoryGradeRaw = categoryWeightsLoadId.entrySet().stream()
                .collect(Collectors.toMap(
                        entry-> getCatName(entry.getKey()),
                        entry-> {
                            Integer categoryId = entry.getKey();
                            Double weight = entry.getValue();
                            BigDecimal baseGrade = BigDecimal.valueOf(gradeBase.getBaseGrade());
                            BigDecimal percentage = BigDecimal.valueOf(gradeBase.getBaseGradePercent());
                            Integer totalItems = totalItemsPerCat.getOrDefault(categoryId, 0);
                            BigDecimal totalScores = totalScoresPerCat.getOrDefault(categoryId, BigDecimal.ZERO);

                            //(total items / total score) 100 this is raw
                            return totalItems > 0
                                    ? (totalScores.divide(BigDecimal.valueOf(totalItems), 2, RoundingMode.HALF_UP)
                                    .multiply(percentage)
                                    .add(baseGrade))
                                    .multiply(BigDecimal.valueOf(weight))
                                    .setScale(0, RoundingMode.HALF_UP)
                                    : BigDecimal.ZERO;
                        }
                ));
        //final grade these are with weights
        BigDecimal finalGrade = categoryWeightsLoadId.entrySet().stream()
                .map(entry -> {
                    Integer categoryId = entry.getKey();
                    Double weight = entry.getValue();
                    Integer totalItems = totalItemsPerCat.getOrDefault(categoryId, 0);
                    BigDecimal totalScores = totalScoresPerCat.getOrDefault(categoryId, BigDecimal.ZERO);

                    return totalItems > 0
                            ? totalScores
                            .divide(BigDecimal.valueOf(totalItems), 2, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100))
                            .multiply(BigDecimal.valueOf(weight))
                            .setScale(1, RoundingMode.HALF_UP)
                            : BigDecimal.ZERO;
                }).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal NORSUgrade;

//        if (finalGrade.compareTo(BigDecimal.valueOf(95)) >= 0) {
//            NORSUgrade = BigDecimal.valueOf(1.0);
//        } else if (finalGrade.compareTo(BigDecimal.valueOf(94)) == 0) {
//            NORSUgrade = BigDecimal.valueOf(1.1);
//        } else if (finalGrade.compareTo(BigDecimal.valueOf(93)) == 0) {
//            NORSUgrade = BigDecimal.valueOf(1.2);
//        } else if (finalGrade.compareTo(BigDecimal.valueOf(92)) == 0) {
//            NORSUgrade = BigDecimal.valueOf(1.3);
//        } else if (finalGrade.compareTo(BigDecimal.valueOf(91)) == 0) {
//            NORSUgrade = BigDecimal.valueOf(1.4);
//        } else if (finalGrade.compareTo(BigDecimal.valueOf(90)) == 0) {
//            NORSUgrade = BigDecimal.valueOf(1.5);
//        } else if (finalGrade.compareTo(BigDecimal.valueOf(89)) == 0) {
//            NORSUgrade = BigDecimal.valueOf(1.6);
//        } else if (finalGrade.compareTo(BigDecimal.valueOf(88)) == 0) {
//            NORSUgrade = BigDecimal.valueOf(1.7);
//        } else if (finalGrade.compareTo(BigDecimal.valueOf(87)) == 0) {
//            NORSUgrade = BigDecimal.valueOf(1.8);
//        } else if (finalGrade.compareTo(BigDecimal.valueOf(86)) == 0) {
//            NORSUgrade = BigDecimal.valueOf(1.9);
//        } else if (finalGrade.compareTo(BigDecimal.valueOf(85)) == 0) {
//            NORSUgrade = BigDecimal.valueOf(2.0);
//        } else if (finalGrade.compareTo(BigDecimal.valueOf(84)) == 0) {
//            NORSUgrade = BigDecimal.valueOf(2.1);
//        } else if (finalGrade.compareTo(BigDecimal.valueOf(83)) == 0) {
//            NORSUgrade = BigDecimal.valueOf(2.2);
//        } else if (finalGrade.compareTo(BigDecimal.valueOf(82)) == 0) {
//            NORSUgrade = BigDecimal.valueOf(2.3);
//        } else if (finalGrade.compareTo(BigDecimal.valueOf(81)) == 0) {
//            NORSUgrade = BigDecimal.valueOf(2.4);
//        } else if (finalGrade.compareTo(BigDecimal.valueOf(80)) == 0) {
//            NORSUgrade = BigDecimal.valueOf(2.5);
//        } else if (finalGrade.compareTo(BigDecimal.valueOf(79)) == 0) {
//            NORSUgrade = BigDecimal.valueOf(2.6);
//        } else if (finalGrade.compareTo(BigDecimal.valueOf(78)) == 0) {
//            NORSUgrade = BigDecimal.valueOf(2.7);
//        } else if (finalGrade.compareTo(BigDecimal.valueOf(77)) == 0) {
//            NORSUgrade = BigDecimal.valueOf(2.8);
//        } else if (finalGrade.compareTo(BigDecimal.valueOf(76)) == 0) {
//            NORSUgrade = BigDecimal.valueOf(2.9);
//        } else if (finalGrade.compareTo(BigDecimal.valueOf(75)) == 0) {
//            NORSUgrade = BigDecimal.valueOf(3.0);
//        } else if (finalGrade.compareTo(BigDecimal.valueOf(74)) == 0) {
//            NORSUgrade = BigDecimal.valueOf(3.1);
//        } else if (finalGrade.compareTo(BigDecimal.valueOf(73)) == 0) {
//            NORSUgrade = BigDecimal.valueOf(3.2);
//        } else if (finalGrade.compareTo(BigDecimal.valueOf(72)) == 0) {
//            NORSUgrade = BigDecimal.valueOf(3.3);
//        } else if (finalGrade.compareTo(BigDecimal.valueOf(71)) == 0) {
//            NORSUgrade = BigDecimal.valueOf(3.4);
//        } else if (finalGrade.compareTo(BigDecimal.valueOf(70)) == 0) {
//            NORSUgrade = BigDecimal.valueOf(3.5);
//        } else if (finalGrade.compareTo(BigDecimal.valueOf(69)) == 0) {
//            NORSUgrade = BigDecimal.valueOf(3.6);
//        } else if (finalGrade.compareTo(BigDecimal.valueOf(68)) == 0) {
//            NORSUgrade = BigDecimal.valueOf(3.7);
//        } else if (finalGrade.compareTo(BigDecimal.valueOf(67)) == 0) {
//            NORSUgrade = BigDecimal.valueOf(3.8);
//        } else if (finalGrade.compareTo(BigDecimal.valueOf(66)) == 0) {
//            NORSUgrade = BigDecimal.valueOf(3.9);
//        } else if (finalGrade.compareTo(BigDecimal.valueOf(65)) == 0) {
//            NORSUgrade = BigDecimal.valueOf(4.0);
//        } else {
//            NORSUgrade = BigDecimal.valueOf(5.0);
//        }


//        double NORSUgrade = switch (roundedGrade){
//            case 95, 96, 97, 98, 99, 100 -> 1;
//            case 94 -> 1.1;
//            case 93 -> 1.2;
//            case 92 -> 1.3;
//            case 91 -> 1.4;
//            case 90 -> 1.5;
//            case 89 -> 1.6;
//            case 88 -> 1.7;
//            case 87 -> 1.8;
//            case 86 -> 1.9;
//            case 85 -> 2.0;
//            case 84 -> 2.1;
//            case 83 -> 2.2;
//            case 82 -> 2.3;
//            case 81 -> 2.4;
//            case 80 -> 2.5;
//            case 79 -> 2.6;
//            case 78 -> 2.7;
//            case 77 -> 2.8;
//            case 76 -> 2.9;
//            case 75 -> 3.0;
//
//            default -> 5.0;
//        };

        String remarks = finalGrade.compareTo(BigDecimal.valueOf(74)) <= 0 ? "Failed" : "Passed";
        return new GradeComputation(studentId, name, categoryGradeRaw, finalGrade, remarks);
//
    }


 ////////////////////////////////////// BORDER BELOW STUDENTS //////////////////////////////////////////////////////

    public GradeComputation computeEachStudentGrade (Enrollments enrollments, List<Grading> gradings,
                                                     Term term, Map<Integer, Double> categoryWeightsLoadId)
    {if (enrollments == null) {
        throw new IllegalArgumentException("Enrollments cannot be null");
    }

        // 2. Safely get student information
        Students student = enrollments.getStudent();
        if (student == null) {
            throw new IllegalStateException("No student associated with this enrollment");
        }

        // 3. Get basic student info
        Integer studentId = student.getStudentId();

        // 4. Safely build student name
        User studentUser = student.getStudent(); // Assuming this is the correct relationship
        if (studentUser == null) {
            throw new IllegalStateException("No user details found for student");
        }

        TeachingLoadDetails teachingLoadDetails = enrollments.getTeachingLoadDetail();

        //BASE Grade
        GradeBase gradeBase = teachingLoadDetails.getGradeBase();
        if (gradeBase == null){
            throw new RuntimeException("Grade Base not found");
        }

        String name = Stream.of(
                        studentUser.getLname(),
                        studentUser.getFname(),
                        Optional.ofNullable(studentUser.getMname())
                                .filter(m -> !m.isEmpty())
                                .map(m -> m.charAt(0) + ".")
                                .orElse("")
                )
                .filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.joining(" "));

        //filter by Term
        List<Grading> itemsFilterByTerm = gradings.stream()
                .filter(g->g.getTerm().getId().equals(term.getId()))
                .toList();

        //GD by term
        List <GradingDetail> scoresFilteredByTerm = enrollments.getGradingDetails().stream()
                .filter(gd-> gd.getGrading().getTerm().getId().equals(term.getId()))
                .toList();

        Map <Integer, Integer> totalItemsPerCat = calculateTotalItemsByCat(itemsFilterByTerm);

        Map <Integer, BigDecimal> totalScoresPerCat = calculateStudentsScoresByCat(scoresFilteredByTerm);

        Map <String, BigDecimal>getRawGrade = categoryWeightsLoadId.entrySet().stream()
                .collect(Collectors.toMap(
                        entry-> getCatName(entry.getKey()),
                        entry-> {
                            Integer categoryId = entry.getKey();
                            Double weight = entry.getValue();
                            BigDecimal baseGrade = BigDecimal.valueOf(gradeBase.getBaseGrade());
                            BigDecimal percentage = BigDecimal.valueOf(gradeBase.getBaseGradePercent());
                            Integer totalItems = totalItemsPerCat.getOrDefault(categoryId, 0);
                            BigDecimal totalScores = totalScoresPerCat.getOrDefault(categoryId, BigDecimal.ZERO);

                            //(total items / total score) 100 this is raw
                            return totalItems > 0
                                    ? (totalScores.divide(BigDecimal.valueOf(totalItems), 2, RoundingMode.HALF_UP)
                                    .multiply(percentage)
                                    .add(baseGrade))
                                    .multiply(BigDecimal.valueOf(weight))
                                    .setScale(0, RoundingMode.HALF_UP)
                                    : BigDecimal.ZERO;
                        }
                ));
        BigDecimal finalGrade = categoryWeightsLoadId.entrySet().stream()
                .map(entry -> {
                    Integer categoryId = entry.getKey();
                    Double weight = entry.getValue();

                    Integer totalItems = totalItemsPerCat.getOrDefault(categoryId, 0);
                    BigDecimal totalScores = totalScoresPerCat.getOrDefault(categoryId, BigDecimal.ZERO);

                    return totalItems > 0
                            ? totalScores
                            .divide(BigDecimal.valueOf(totalItems), 2, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100))
                            .multiply(BigDecimal.valueOf(weight))
                            .setScale(0, RoundingMode.HALF_UP)
                            : BigDecimal.ZERO;
                }).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal NORSUgrade;

        if (finalGrade.compareTo(BigDecimal.valueOf(95)) >= 0) {
            NORSUgrade = BigDecimal.valueOf(1.0);
        } else if (finalGrade.compareTo(BigDecimal.valueOf(94)) == 0) {
            NORSUgrade = BigDecimal.valueOf(1.1);
        } else if (finalGrade.compareTo(BigDecimal.valueOf(93)) == 0) {
            NORSUgrade = BigDecimal.valueOf(1.2);
        } else if (finalGrade.compareTo(BigDecimal.valueOf(92)) == 0) {
            NORSUgrade = BigDecimal.valueOf(1.3);
        } else if (finalGrade.compareTo(BigDecimal.valueOf(91)) == 0) {
            NORSUgrade = BigDecimal.valueOf(1.4);
        } else if (finalGrade.compareTo(BigDecimal.valueOf(90)) == 0) {
            NORSUgrade = BigDecimal.valueOf(1.5);
        } else if (finalGrade.compareTo(BigDecimal.valueOf(89)) == 0) {
            NORSUgrade = BigDecimal.valueOf(1.6);
        } else if (finalGrade.compareTo(BigDecimal.valueOf(88)) == 0) {
            NORSUgrade = BigDecimal.valueOf(1.7);
        } else if (finalGrade.compareTo(BigDecimal.valueOf(87)) == 0) {
            NORSUgrade = BigDecimal.valueOf(1.8);
        } else if (finalGrade.compareTo(BigDecimal.valueOf(86)) == 0) {
            NORSUgrade = BigDecimal.valueOf(1.9);
        } else if (finalGrade.compareTo(BigDecimal.valueOf(85)) == 0) {
            NORSUgrade = BigDecimal.valueOf(2.0);
        } else if (finalGrade.compareTo(BigDecimal.valueOf(84)) == 0) {
            NORSUgrade = BigDecimal.valueOf(2.1);
        } else if (finalGrade.compareTo(BigDecimal.valueOf(83)) == 0) {
            NORSUgrade = BigDecimal.valueOf(2.2);
        } else if (finalGrade.compareTo(BigDecimal.valueOf(82)) == 0) {
            NORSUgrade = BigDecimal.valueOf(2.3);
        } else if (finalGrade.compareTo(BigDecimal.valueOf(81)) == 0) {
            NORSUgrade = BigDecimal.valueOf(2.4);
        } else if (finalGrade.compareTo(BigDecimal.valueOf(80)) == 0) {
            NORSUgrade = BigDecimal.valueOf(2.5);
        } else if (finalGrade.compareTo(BigDecimal.valueOf(79)) == 0) {
            NORSUgrade = BigDecimal.valueOf(2.6);
        } else if (finalGrade.compareTo(BigDecimal.valueOf(78)) == 0) {
            NORSUgrade = BigDecimal.valueOf(2.7);
        } else if (finalGrade.compareTo(BigDecimal.valueOf(77)) == 0) {
            NORSUgrade = BigDecimal.valueOf(2.8);
        } else if (finalGrade.compareTo(BigDecimal.valueOf(76)) == 0) {
            NORSUgrade = BigDecimal.valueOf(2.9);
        } else if (finalGrade.compareTo(BigDecimal.valueOf(75)) == 0) {
            NORSUgrade = BigDecimal.valueOf(3.0);
        } else if (finalGrade.compareTo(BigDecimal.valueOf(74)) == 0) {
            NORSUgrade = BigDecimal.valueOf(3.1);
        } else if (finalGrade.compareTo(BigDecimal.valueOf(73)) == 0) {
            NORSUgrade = BigDecimal.valueOf(3.2);
        } else if (finalGrade.compareTo(BigDecimal.valueOf(72)) == 0) {
            NORSUgrade = BigDecimal.valueOf(3.3);
        } else if (finalGrade.compareTo(BigDecimal.valueOf(71)) == 0) {
            NORSUgrade = BigDecimal.valueOf(3.4);
        } else if (finalGrade.compareTo(BigDecimal.valueOf(70)) == 0) {
            NORSUgrade = BigDecimal.valueOf(3.5);
        } else if (finalGrade.compareTo(BigDecimal.valueOf(69)) == 0) {
            NORSUgrade = BigDecimal.valueOf(3.6);
        } else if (finalGrade.compareTo(BigDecimal.valueOf(68)) == 0) {
            NORSUgrade = BigDecimal.valueOf(3.7);
        } else if (finalGrade.compareTo(BigDecimal.valueOf(67)) == 0) {
            NORSUgrade = BigDecimal.valueOf(3.8);
        } else if (finalGrade.compareTo(BigDecimal.valueOf(66)) == 0) {
            NORSUgrade = BigDecimal.valueOf(3.9);
        } else if (finalGrade.compareTo(BigDecimal.valueOf(65)) == 0) {
            NORSUgrade = BigDecimal.valueOf(4.0);
        } else {
            NORSUgrade = BigDecimal.valueOf(5.0);
        }

        String remarks = finalGrade.compareTo(BigDecimal.valueOf(74)) <= 0 ? "Failed" : "Passed";

        return new GradeComputation(studentId, name, getRawGrade, NORSUgrade, remarks);
    }

}
