package com.assookkaa.ClassRecord.Service.Admin.Interface;

import com.assookkaa.ClassRecord.Dto.Request.Admin.TeachingLoadStatusEdit;
import com.assookkaa.ClassRecord.Dto.Response.TeachingLoad.TeachingLoadDetailsListOfStudentsEnrolled;
import com.assookkaa.ClassRecord.Dto.Response.User.StudentAllDetails;
import com.assookkaa.ClassRecord.Dto.Response.User.TeacherAllDetails;
import com.assookkaa.ClassRecord.Dto.Response.User.Users;

import java.util.List;

public interface AdminInterface {

    List <Users> getAllUsers();
    List <StudentAllDetails> getAllStudentDetails();
    List <TeacherAllDetails> getAllTeacherDetails();

    List<TeachingLoadDetailsListOfStudentsEnrolled> getAllEnrolledStudentDetails(Integer tld);

    TeachingLoadStatusEdit editStatus (TeachingLoadStatusEdit dto);
}
