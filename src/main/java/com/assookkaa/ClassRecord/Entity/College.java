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
@Table(name = "colleges")
public class College {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(nullable = false)
    private String college_name;

    private String college_code;

    //Mapping
    @OneToMany(mappedBy = "college", cascade = CascadeType.ALL)
    private List<Courses> courses;

    public College(String college_name, String college_code) {
        this.college_name = college_name;
        this.college_code = college_code;
    }
}
