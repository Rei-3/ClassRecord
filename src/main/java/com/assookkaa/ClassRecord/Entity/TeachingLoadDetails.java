package com.assookkaa.ClassRecord.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "teaching_load_details")
public class TeachingLoadDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String section;

    @Column
    private String schedule;

    @Column
    private String hashKey;

    // Many TeachingLoadDetails belong to one TeachingLoad
    @ManyToOne
    @JoinColumn(name = "teaching_load_id", referencedColumnName = "id", nullable = false)
    private TeachingLoad teachingLoad;

    @OneToMany(mappedBy = "teachingLoadDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <Grading> grading;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id", nullable = false)
    private Subjects subject;

    @OneToMany(mappedBy = "teachingLoadDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollments> enrollments;

    @OneToMany(mappedBy = "teachingLoadDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GradingComposition> gradingComposition;
}
