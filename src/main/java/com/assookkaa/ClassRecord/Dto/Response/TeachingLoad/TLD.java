package com.assookkaa.ClassRecord.Dto.Response.TeachingLoad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TLD {
 private Integer id;
 private Integer enrollmentId;
 private Integer teachingLoadId;
 private String subjectName;
 private String schedule;
 private String key;
 private String section;

 }
