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
		    boolean adminbyRolesExists = utilisateurRepo.existsByRoleUtilisateursContains(Collections.singleton(roleUtilisateurRepo.findByNomRoles("ADMIN")));
		    Set<RoleUtilisateur> listRole=new HashSet<>();
	         listRole.add(ADMIN);
         
        // Check if an admin user already exists
        if (!adminbyRolesExists) {
        	Utilisateur admin = new Utilisateur();
            admin.setNom("Admin");
            admin.setPrenom("System");
             admin.setCin("00000000");
             admin.setAdresse("Rue de l'Admin, Tunis");
             admin.setRoleUtilisateurs(listRole);
             admin.setEmail("admin@admin.com");
            admin.setPassword(new BCryptPasswordEncoder().encode("adminadmin"));
         
            
            utilisateurRepo.save(admin);
            System.out.println("Compte administrateur créé avec succès.");
        } else {
            System.out.println("Le compte administrateur existe déjà.");
        }
    }
}