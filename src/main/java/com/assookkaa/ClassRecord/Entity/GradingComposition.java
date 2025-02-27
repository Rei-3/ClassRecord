package com.assookkaa.ClassRecord.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "grading_composition", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"teaching_load_detail_id", "category_id"})
})
public class GradingComposition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    private Float percentage;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private GradeCategory category;

    @ManyToOne
    @JoinColumn(name = "teaching_load_detail_id", referencedColumnName = "id", nullable = false)
    private TeachingLoadDetails teachingLoadDetail;
}
