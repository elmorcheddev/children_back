
package com.child.model;


import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;


@Entity
@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class Paiement {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


@ManyToOne(optional = false)
private Parent parent;


@ManyToOne(optional = false)
private Enfant enfant;


@Column(nullable = false)
private Double montant; // ex: 250.0


@Column(nullable = false)
private String mois; // ex: "Septembre 2025" ou "2025-09"


@Column(nullable = false)
private String modePaiement; // Especes, Virement, Carte


@Column(nullable = false)
private LocalDate datePaiement; // date effective du paiement


@Lob
@Column(name = "recu_pdf", columnDefinition = "bytea")
private byte[] recuPdf;

}