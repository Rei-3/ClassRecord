package com.assookkaa.ClassRecord.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "grade_base")
public class GradeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    private Double baseGrade;

    private Double baseGradePercent;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teaching_load_details_id", referencedColumnName = "id")
    private TeachingLoadDetails teachingLoadDetails;

}
