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
@Table(name = "subjects")
public class Subjects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String SubjectName;

    @Column
    private String SubjectDesc;

    @Column
    private Integer units;

    @Column
    private Integer type;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
    private Courses course;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeachingLoadDetails> details;

    public Subjects(String SubjectName, String SubjectDesc, Integer units, Integer type, Courses course) {
        this.SubjectName = SubjectName;
        this.SubjectDesc = SubjectDesc;
        this.units = units;
        this.type = type;
        this.course = course;
    }
}
