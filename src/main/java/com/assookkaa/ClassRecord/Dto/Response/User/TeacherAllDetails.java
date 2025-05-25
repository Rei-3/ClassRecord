package com.assookkaa.ClassRecord.Dto.Response.User;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class TeacherAllDetails extends Users{
    private String teacherId;
}
