package com.child.service;

 
 
import java.util.List;

import org.springframework.data.repository.query.Param;


import com.child.model.Utilisateur;

import jakarta.validation.Valid; 
 
public interface UtilisateurService {
			Utilisateur ajouterSuperAdmin(Utilisateur utilisateur);
	    List<Utilisateur> findByRoleName(String roleName);
		Utilisateur findByIdUtilisateur(Long id);
		List<Utilisateur> listAll();
		Utilisateur createUser(@Valid Utilisateur utilisateur);
 

}
