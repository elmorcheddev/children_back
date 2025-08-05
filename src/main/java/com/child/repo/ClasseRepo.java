package com.child.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.child.model.Classe;

public interface ClasseRepo extends JpaRepository<Classe, Long> {

    // Exemple : Trouver une classe par nom
    Classe findByNom(String nom);
}
