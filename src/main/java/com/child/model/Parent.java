package com.child.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parent extends Utilisateur {

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Enfant> enfants;
}
