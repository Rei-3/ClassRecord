package com.assookkaa.ClassRecord.Dto.Response.TeachingLoad;

import lombok.Data;

import java.util.Date;

@Data
public class TeachingLoadResponseDto {
   private Integer id;
   private Integer semId;
   private Boolean status;
   private Integer teacherId;
   private Date addedOn;
   private String academicYear;
}
