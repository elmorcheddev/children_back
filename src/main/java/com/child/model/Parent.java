package com.child.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nom;

    @NotBlank
    private String prenom;

    @NotBlank
    private String relation; // père, mère, tuteur

    private String cin;
    private String adresse;

    @NotBlank
    private String telPrincipal;
    private String telSecondaire;

    @Email
    @Column(unique = true, nullable = false)   
    private String email;

    private String profession;
    private String lieuTravail;
    private String telTravail;

    private String contactUrgenceNom;
    private String contactUrgenceTel;

    private boolean etat; // actif/inactif


}
