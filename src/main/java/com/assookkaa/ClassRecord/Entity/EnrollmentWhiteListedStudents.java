package com.assookkaa.ClassRecord.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "enrollment_white_listed_students")
public class EnrollmentWhiteListedStudents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer studentId;

    private boolean enrolled;

    @ManyToOne
    @JoinColumn(name = "teaching_load_deatails_id", referencedColumnName = "id")
    private TeachingLoadDetails teachingLoadDetails;
}
