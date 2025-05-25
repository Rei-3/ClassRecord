package com.assookkaa.ClassRecord.Service.Grading;

import com.assookkaa.ClassRecord.Dto.Request.Grading.BaseGradeRequest;
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

    private final GradeBaseRepository gradeBaseRepository;

    public GradingService(GradingFunc gradingFunc, GradingRepository gradingRepository, EnrollmentRepository enrollmentRepository, GradingDetailRepository gradingDetailRepository, GradeBaseRepository gradeBaseRepository) {
        this.gradingFunc = gradingFunc;
        this.gradingRepository = gradingRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.gradingDetailRepository = gradingDetailRepository;
        this.gradeBaseRepository = gradeBaseRepository;
    }


    @Override
    @Transactional
    public GradingResponse addActivity(GradingRequest gradingRequest) {
        TeachingLoadDetails teachingLoadDetails =gradingFunc.findTeachingLoadDetailId(gradingRequest.getTeachingLoadDetailId());
        GradeCategory category = gradingFunc.findGradeCategory(gradingRequest.getCategoryId());
        Term term = gradingFunc.findTermById(gradingRequest.getTermId());

        Grading grading = gradingFunc.buildActivity(gradingRequest,teachingLoadDetails,category,term);

        grading = gradingRepository.save(grading);


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
            gradingDetail.setRecordedOn(new Date());
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


    public BaseGradeResponse addBaseGrade ( BaseGradeRequest req){

        TeachingLoadDetails teachingLoadDetails = gradingFunc.findTeachingLoadDetailId(
                req.getTeachingLoadDetailId());

        GradeBase baseGrade = GradeBase.builder()
                .baseGrade(req.getBaseGrade())
                .baseGradePercent(req.getPercentage())
                .teachingLoadDetails(teachingLoadDetails)
                .build();
        gradeBaseRepository.save(baseGrade);


        return BaseGradeResponse.builder()
                .id(baseGrade.getId())
                .baseGrade(baseGrade.getBaseGrade())
                .percentage(baseGrade.getBaseGradePercent())
                .build();
    }

    public BaseGradeResponse editBaseGrade (Integer BaseGradeId, BaseGradeRequest req){

        GradeBase baseGrade = gradeBaseRepository.findById(BaseGradeId).orElseThrow(()->new IllegalArgumentException("Base Grade Not Found"));

        TeachingLoadDetails tld = gradingFunc.findTeachingLoadDetailId(req.getTeachingLoadDetailId());

        baseGrade.setBaseGrade(req.getBaseGrade());
        baseGrade.setBaseGradePercent(req.getPercentage());
        baseGrade.setTeachingLoadDetails(tld);

        gradeBaseRepository.save(baseGrade);

        return BaseGradeResponse.builder()
                .id(baseGrade.getId())
                .baseGrade(baseGrade.getBaseGrade())
                .percentage(baseGrade.getBaseGradePercent())
                .build();
    }

//////////////////////////
    public BaseGradeResponse getBaseGrade(Integer req){

        TeachingLoadDetails teachingLoadDetails = gradingFunc.findTeachingLoadDetailId(req);

        GradeBase baseGrade = gradeBaseRepository.findByTeachingLoadDetailsId(teachingLoadDetails.getId()



        );
        if(baseGrade == null){
            throw new IllegalArgumentException("Not Found BaseGrade");
        }

        return BaseGradeResponse.builder()
                .id(baseGrade.getId())
                .baseGrade(baseGrade.getBaseGrade())
                .percentage(baseGrade.getBaseGradePercent())
                .build();
    }

    @Override
    public List<GradeComputation> calculateEachTermGrade(Integer teachingLoad, List<Enrollments> enrollments, Integer termId) {

//        List<GradingDetail> gradingDetails = gradingFunc.getFilteredListOfStudents(enrollments, termId);

        Map<Integer, Double> categoryWeights = gradingFunc.getCategoryWeights(teachingLoad);
        Term term = gradingFunc.findTermById(termId);

        List <Grading> gradings = gradingRepository.findByTeachingLoadDetailsIdAndTermId(teachingLoad, term.getId());

        return enrollments.stream()
                .map(e -> gradingFunc.computeTermGrades(e, gradings, term, categoryWeights))
                .collect(Collectors.toList());
    }


    @Override
    public List<SemesterGradeComputation> calculateSemGrade(Integer teachingLoadDetailId, List<Enrollments> enrollments) {
        // Get the terms for semester calculation
        Term term1 = gradingFunc.findTermById(1);
        Term term2 = gradingFunc.findTermById(2);

        // Calculate the term grades synchronously
        List<GradeComputation> term1Grades = this.calculateEachTermGrade(teachingLoadDetailId, enrollments, 1);
        List<GradeComputation> term2Grades = this.calculateEachTermGrade(teachingLoadDetailId, enrollments, 2);

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
    public List<GradesPerCategory> getGradesPerCategory(Integer teachingLoadDetailId, Integer termId, Integer categoryId) {

        TeachingLoadDetails teachingLoadDetails = gradingFunc.findTeachingLoadDetailId(teachingLoadDetailId);
        Term term = gradingFunc.findTermById(termId);
        GradeCategory category = gradingFunc.findGradeCategory(categoryId);

        return gradingDetailRepository.findGradesPerCategory(
                teachingLoadDetails.getId(),
                term.getId(),
                category.getId()
        );
    }


    @Override
    public List<GradingResponse> getGrading(Integer teachingLoadDetailId, Integer termId, Integer categoryId) {

        List <Grading> getGrading = gradingRepository.findByTeachingLoadDetailsIdAndTermIdAndCategoryId(teachingLoadDetailId, termId, categoryId);

        TeachingLoadDetails teachingLoadDetails = gradingFunc.findTeachingLoadDetailId(teachingLoadDetailId);
        Term term = gradingFunc.findTermById(termId);
        GradeCategory cat = gradingFunc.findGradeCategory(categoryId);

        List <GradingResponse> gradingResponses = new ArrayList<>();

        for (Grading grading : getGrading) {
            GradingResponse items = new GradingResponse();
            items.setId(grading.getId());
            items.setTeachingLoadDetailId(teachingLoadDetails.getId());
            items.setTermId(term.getId());
            items.setCatId(cat.getId());
            items.setDesc(grading.getDescription());
            items.setNumberOfItems(grading.getNumberOfItems());
            items.setDate(grading.getDateConducted());

            gradingResponses.add(items);
        }

        return gradingResponses;
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
