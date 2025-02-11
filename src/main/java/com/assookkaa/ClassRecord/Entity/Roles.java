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
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(name = "role_name", nullable = false, unique = true) // Explicitly map to snake_case column
    private String roleName;

    // Mapping
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true) // User owns this relationship
    private List <User> user;

    public Roles(String roleName) {
        this.roleName = roleName;
    }
}
