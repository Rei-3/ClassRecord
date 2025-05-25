package com.assookkaa.ClassRecord.Dto.Request.Admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeachingLoadStatusEdit {
    private Integer teachingLoadId;
    private Boolean status;
}
