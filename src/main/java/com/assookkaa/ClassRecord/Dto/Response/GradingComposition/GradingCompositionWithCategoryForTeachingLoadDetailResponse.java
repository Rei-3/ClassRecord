package com.assookkaa.ClassRecord.Dto.Response.GradingComposition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradingCompositionWithCategoryForTeachingLoadDetailResponse {
   private Integer teachingLoadDetailId;
   private List<CompositionItem> composition;

   @Data
   @AllArgsConstructor
    public static class CompositionItem {
        private Integer id;
        private Double percentage;
        private CatDto category;
    }

    public record CatDto (Integer id, String categoryName){};
}
