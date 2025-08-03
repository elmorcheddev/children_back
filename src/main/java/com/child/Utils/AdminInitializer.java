package com.child.Utils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.child.model.RoleUtilisateur;
import com.child.model.Utilisateur;
import com.child.repo.RoleRepo;
import com.child.repo.UtilisateurRepo;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final UtilisateurRepo utilisateurRepo;
    private final RoleRepo roleUtilisateurRepo;
     public AdminInitializer(UtilisateurRepo utilisateurRepo, RoleRepo roleUtilisateurRepo) {
        this.utilisateurRepo = utilisateurRepo;
        this.roleUtilisateurRepo = roleUtilisateurRepo;
    }

    @Override
    public void run(String... args) throws Exception {

        // Ensure required roles exist
        RoleUtilisateur ADMIN = roleUtilisateurRepo.findByNomRoles("ADMIN");

        if (ADMIN == null) {
            ADMIN = new RoleUtilisateur();
            ADMIN.setNomRoles("ADMIN");
            roleUtilisateurRepo.save(ADMIN);
        }
        RoleUtilisateur roles = roleUtilisateurRepo.findByNomRoles("ADMIN");
		    boolean adminbyRolesExists = utilisateurRepo.existsByRoleUtilisateursContains(Collections.singleton(roleUtilisateurRepo.findByNomRoles("ADMIN")));

        Set<RoleUtilisateur> listRole=new HashSet<>();
         listRole.add(roles);
         Utilisateur utilisateur = new Utilisateur();
         if (!adminbyRolesExists) {
        	 utilisateur.setNom(utilisateur.getNom());
        	 utilisateur.setPrenom(utilisateur.getPrenom());
        	 utilisateur.setEmail(utilisateur.getEmail());
        	 utilisateur.setPassword(new BCryptPasswordEncoder().encode(utilisateur.getPassword()));
        	 utilisateur.setAdresse(utilisateur.getAdresse());
	        	 utilisateur.setEtat(true);
	        	 utilisateur.setRoleUtilisateurs(listRole);
        	 utilisateur.setEtat(true);

          
          utilisateurRepo.save(utilisateur); 
         }else {
        	  return ;
         }
    }
}