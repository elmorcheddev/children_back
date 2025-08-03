package com.child.service;

 
 
import java.util.List;

import org.springframework.data.repository.query.Param;


import com.child.model.Utilisateur; 
 
public interface UtilisateurService {
			Utilisateur ajouterSuperAdmin(Utilisateur utilisateur);
	    List<Utilisateur> findByRoleName(String roleName);
		Utilisateur findByIdUtilisateur(Long id);
		List<Utilisateur> listAll();
 

}
