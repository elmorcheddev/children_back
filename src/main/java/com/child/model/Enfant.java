package com.child.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enfant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nom;

    @NotBlank
    private String prenom;
    private String etatSante;

    private String dateNaissance;
    @Column(nullable = false)
    private boolean payee = false;
    @ManyToOne
    @JoinColumn(name = "classe_id")  
    private Classe classe; 
    

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    private Parent parent;
}
