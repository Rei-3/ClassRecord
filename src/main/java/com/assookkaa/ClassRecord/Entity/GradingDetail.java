package com.assookkaa.ClassRecord.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "grading_detail")
public class GradingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(nullable = true)
    private Double score;

    @ManyToOne
    @JoinColumn(name = "enrollments", referencedColumnName = "id")
    private Enrollments enrollments;

    @ManyToOne
    @JoinColumn(name = "grading", referencedColumnName = "id")
    private Grading grading;

    @Column
    private Date recordedOn;

}
