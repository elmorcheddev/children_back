package com.child.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Educateur extends Utilisateur {

    @OneToOne(mappedBy = "educateur")
    private Classe classe;
}
