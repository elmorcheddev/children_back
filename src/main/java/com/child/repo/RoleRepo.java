package com.child.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.child.model.RoleUtilisateur;

public interface RoleRepo extends JpaRepository<RoleUtilisateur, Long> {
	RoleUtilisateur findByNomRoles(String nomRoles);
}
