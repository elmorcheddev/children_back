package com.child.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;       // Exemple : "Petite Section A"
    private String niveau;    // Exemple : "Petite", "Moyenne", "Grande"

    // Liste des enfants dans la classe
    @OneToMany(mappedBy = "classe", cascade = CascadeType.ALL)
    private List<Enfant> enfants;

    // Un éducateur pour cette classe
    @OneToOne
    @JoinColumn(name = "educateur_id", referencedColumnName = "id")
    private Educateur educateur;
}
