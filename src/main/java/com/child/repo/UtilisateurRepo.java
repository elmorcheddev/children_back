package com.child.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

 import com.child.model.RoleUtilisateur;
import com.child.model.Utilisateur;
import java.util.List;
import java.util.Set;


public interface UtilisateurRepo extends JpaRepository<Utilisateur, Long>{
	
	boolean existsByEmail(String email);
	Utilisateur  findByEmail(String email); 
boolean existsByRole(RoleUtilisateur role);
}
