package com.assookkaa.ClassRecord.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "enrollments")
public class Enrollments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    private Date added_on;

    @ManyToOne
    @JoinColumn(name = "teaching_load_detail_id", referencedColumnName = "id", nullable = false)
    private TeachingLoadDetails teachingLoadDetail;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    private Students student;

    @OneToMany(mappedBy = "enrollments",cascade = CascadeType.ALL, orphanRemoval = true)
    private List <GradingDetail> gradingDetails;

    @OneToMany(mappedBy = "enrollments", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <Attendance> attendances;

}
