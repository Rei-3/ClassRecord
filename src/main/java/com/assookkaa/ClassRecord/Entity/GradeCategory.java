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
@Table(name = "grade_category")
public class GradeCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    private String categoryName;

    @OneToMany
    (mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GradingComposition> gradingComposition;

    @OneToMany
    (mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Grading> grading;

    public GradeCategory(String categoryName) {
        this.categoryName = categoryName;
    }
}
