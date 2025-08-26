package com.child.model;

import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nom;

    @NotBlank
    private String prenom;

    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    private String telephone;

    private String adresse;

    @NotBlank
    private String specialite; // ex: éducatrice maternelle, psychologue...

    private Integer experience; // nb d’années d’expérience

    private String diplome; // diplômes obtenus

    private String sexe; // Homme / Femme

    private String dateNaissance;

 
}
