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
@Table(name = "sem")
public class Sem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(nullable = false)
    private String sem_name;

    //Mapping
    @OneToMany(mappedBy = "sem", cascade = CascadeType.ALL)
    private List<TeachingLoad> teachingLoads;

    public Sem(String sem_name) {
        this.sem_name = sem_name;
    }
}
