package com.assookkaa.ClassRecord.Utils.Interface;

import com.assookkaa.ClassRecord.Entity.*;

public interface SuperInterface {
    Teachers findTeacherByUsername(String username);
    Students findStudentByUsername(String username);
    Subjects findSubjectbyId(Integer subjectId);
    Sem findSembyId(Integer semId);
    GradeCategory findGradeCategory(Integer id);
    TeachingLoadDetails findTeachingLoadDetailId(Integer id);
    TeachingLoadDetails findTeachingLoadDetailByHashKey(String hashKey);
    Term findTermById(Integer id);
}