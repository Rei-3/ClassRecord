package com.assookkaa.ClassRecord.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "grading")
public class Grading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    private String description;

    private Integer numberOfItems;

    private Date dateConducted;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private GradeCategory category;

    @ManyToOne
    @JoinColumn(name = "teaching_load_detail_id", referencedColumnName = "id" , nullable = false)
    private TeachingLoadDetails teachingLoadDetails;

    @ManyToOne
    @JoinColumn(name = "term_id", referencedColumnName = "id", nullable = false)
    private Term term;
}
