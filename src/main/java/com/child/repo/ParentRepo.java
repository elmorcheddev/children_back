package com.child.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.child.model.Parent;

public interface ParentRepo  extends JpaRepository<Parent, Long>{
    boolean existsByEmail(String email);
    Optional<Parent> findByEmail(String email);

}
