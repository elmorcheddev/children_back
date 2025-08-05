package com.child.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.child.model.Educateur;

public interface EducateurRepo extends JpaRepository<Educateur, Long> {

    // Exemple : Trouver un éducateur par email
    Educateur findByEmail(String email);
}
