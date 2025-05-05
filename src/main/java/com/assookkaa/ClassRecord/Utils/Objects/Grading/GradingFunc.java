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
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class GradingFunc extends Super implements GradingFuncInterface {

    private final GradingDetailRepository gradingDetailRepository;
    private final GradingCompositionRepository gradingCompositionRepository;
    private final GradeCategoryRepository gradeCategoryRepository;

    public GradingFunc(TeacherRepository teacherRepository, StudentRepository studentRepository, SubjectsRepository subjectsRepository, SemRepository semRepository, TeachingLoadDetailsRespository teachingLoadDetailsRespository, TermRepository termRepository, EnrollmentRepository enrollmentRepository, GradingDetailRepository gradingDetailRepository, GradingCompositionRepository gradingCompositionRepository, GradeCategoryRepository gradeCategoryRepository) {
        super(teacherRepository, studentRepository, subjectsRepository, semRepository, gradeCategoryRepository, teachingLoadDetailsRespository, termRepository, enrollmentRepository);
        this.gradingDetailRepository = gradingDetailRepository;
        this.gradingCompositionRepository = gradingCompositionRepository;
        this.gradeCategoryRepository = gradeCategoryRepository;
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

        //map out total score of all cat
        Map <Integer, BigDecimal> totalScoresPerCat = calculateStudentsScoresByCat(scoresFilteredByTerm);

        Map <String, BigDecimal>categoryGradeRaw = categoryWeightsLoadId.entrySet().stream()
                .collect(Collectors.toMap(
                        entry-> getCatName(entry.getKey()),
                        entry-> {
                            Integer categoryId = entry.getKey();
//                            Double weight = entry.getValue();
                            Integer totalItems = totalItemsPerCat.getOrDefault(categoryId, 0);
                            BigDecimal totalScores = totalScoresPerCat.getOrDefault(categoryId, BigDecimal.ZERO);

                            //(total items / total score) 100 this is raw
                            return totalItems > 0
                                    ? (totalScores.divide(BigDecimal.valueOf(totalItems), 2, RoundingMode.HALF_UP)
                                    .multiply(BigDecimal.valueOf(100)))
//                                    .multiply(BigDecimal.valueOf(weight))
                                    .setScale(2, RoundingMode.HALF_UP)
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
                            .setScale(2, RoundingMode.HALF_UP)
                            : BigDecimal.ZERO;
                }).reduce(BigDecimal.ZERO, BigDecimal::add);


        String remarks = finalGrade.compareTo(BigDecimal.valueOf(74)) <= 0 ? "Failed" : "Passed";

        return new GradeComputation(studentId, name, categoryGradeRaw, finalGrade, remarks);
//
    }


}
