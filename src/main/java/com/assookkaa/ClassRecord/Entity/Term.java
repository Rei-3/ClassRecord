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
@Table(name = "term")
public class Term {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String term_type;

    @OneToOne(mappedBy = "term", cascade = CascadeType.ALL, orphanRemoval = true)
    private Grading grading;

    public Term(String term_type) {
        this.term_type = term_type;
    }
}
