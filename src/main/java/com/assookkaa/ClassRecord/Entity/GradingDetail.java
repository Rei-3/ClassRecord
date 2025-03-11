package com.assookkaa.ClassRecord.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "grading_detail")
public class GradingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(nullable = false)
    private Integer score;

    @ManyToOne
    @JoinColumn(name = "enrollments", referencedColumnName = "id")
    private Enrollments enrollments;

    @ManyToOne
    @JoinColumn(name = "grading", referencedColumnName = "id")
    private Grading grading;

}
