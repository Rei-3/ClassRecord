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
@Table(name = "courses")
public class Courses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    private String course_name;

    private String course_code;

    //Mapping
    @ManyToOne
    @JoinColumn(name = "college_id", referencedColumnName = "id", nullable = false)
    private College college;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Students> students;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subjects> subjects;

    public Courses(String course_name, String course_code, College college) {
        this.course_name = course_name;
        this.course_code = course_code;
        this.college = college;
    }
}
