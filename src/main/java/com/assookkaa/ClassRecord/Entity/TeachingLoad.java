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
@Table(name = "teaching_load")
public class TeachingLoad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Date added_on;

    @Column
    private String academic_year;

    @Column
    private Boolean status;

    // Mapping
    @ManyToOne
    @JoinColumn(name = "sem_id", referencedColumnName = "id", nullable = false)
    private Sem sem;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id", nullable = false)
    private Teachers teachers;

    // One TeachingLoad can have multiple TeachingLoadDetails
    @OneToMany(mappedBy = "teachingLoad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeachingLoadDetails> details;
}
