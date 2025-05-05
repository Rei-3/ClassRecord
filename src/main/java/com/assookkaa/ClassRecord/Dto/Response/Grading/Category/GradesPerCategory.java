package com.assookkaa.ClassRecord.Dto.Response.Grading.Category;

import com.assookkaa.ClassRecord.Entity.Grading;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradesPerCategory {
    private Integer teachingLoadDetailId;
    private Integer termId;
    private Integer categoryId;
    private List<Items> grades;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Items {
        private String desc;
        private Integer totalItem;
        private Date dateConducted;
        private List<Score> score;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Score {
        private Integer gradingDetailId;
        private Integer enrollmentId;
        private BigDecimal score;
        private Date recordedOn;
    }

}
