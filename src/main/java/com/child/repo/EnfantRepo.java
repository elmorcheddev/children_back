package com.child.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.child.model.Enfant;
import java.util.List;

public interface EnfantRepo extends JpaRepository<Enfant, Long> {

    // Trouver tous les enfants d'un parent
    List<Enfant> findByParentId(Long parentId);

    // Trouver tous les enfants dans une classe
    List<Enfant> findByClasseId(Long classeId);
}
