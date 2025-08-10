package com.child.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.child.model.Parent;

import java.util.Optional;

public interface ParentRepository extends JpaRepository<Parent, Long> {
    
    Optional<Parent> findByUtilisateurId(Long utilisateurId);

    boolean existsByUtilisateurEmail(String email);

	boolean existsByUtilisateurId(Long id);
}
