package com.child.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.child.model.RoleUtilisateur;
import com.child.model.Utilisateur;
import com.child.repo.RoleRepo;
import com.child.repo.UtilisateurRepo;
import com.child.service.UtilisateurService;

import jakarta.transaction.Transactional;
@Service
public class UtilisateurServiceImpl implements UtilisateurService {
@Autowired
    private final BCryptPasswordEncoder passwordEncoder;
@Autowired
private UtilisateurRepo utilisateurRepo;
@Autowired private RoleRepo roleRepo;
@Autowired
private JavaMailSender mailSender;


    UtilisateurServiceImpl(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

	

	@Override
	public Utilisateur ajouterSuperAdmin(Utilisateur utilisateur) {
 	        RoleUtilisateur roles = roleRepo.findByNomRoles("ADMIN");
 		    boolean adminbyRolesExists = utilisateurRepo.existsByRoleUtilisateursContains(Collections.singleton(roleRepo.findByNomRoles("ADMIN")));

	        Set<RoleUtilisateur> listRole=new HashSet<>();
	         listRole.add(roles);
	         boolean Exist = utilisateurRepo.existsByEmail(utilisateur.getEmail());
	         if (!Exist && !adminbyRolesExists) {
	        	 utilisateur.setNom(utilisateur.getNom());
	        	 utilisateur.setPrenom(utilisateur.getPrenom());
	        	 utilisateur.setEmail(utilisateur.getEmail());
	        	 utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
	        	 utilisateur.setAdresse(utilisateur.getAdresse());
 	        	 utilisateur.setEtat(true);
 	        	 utilisateur.setRoleUtilisateurs(listRole);
	        	 utilisateur.setEtat(true);

	          
	        return  utilisateurRepo.save(utilisateur); 
	         }else {
	        	 return null;
	         }
	}
 
	@Override
	public List<Utilisateur> findByRoleName(String roleName) {
		// TODO Auto-generated method stub
		return utilisateurRepo.findByRoleName(roleName);
	}
	 
	@Override
	public Utilisateur findByIdUtilisateur(Long id) {
		// TODO Auto-generated method stub
		return utilisateurRepo.findById(id).orElse(null);
	}
	 

	 @Override
	    public Utilisateur createUser(Utilisateur utilisateur) {
	        if (!StringUtils.hasText(utilisateur.getEmail())) {
	            throw new IllegalArgumentException("Email must not be empty");
	        }

	        if (utilisateurRepo.existsByEmail(utilisateur.getEmail())) {
	            throw new IllegalStateException("Email already in use: " + utilisateur.getEmail());
	        }

	        if (!StringUtils.hasText(utilisateur.getPassword()) || utilisateur.getPassword().length() < 6) {
	            throw new IllegalArgumentException("Password must be at least 6 characters long");
	        }

	        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));

	        Set<RoleUtilisateur> roles = new HashSet<>();
	        RoleUtilisateur parentRole = roleRepo.findByNomRoles("PARENT");
	        if (parentRole == null) {
	            throw new IllegalStateException("Role PARENT not found in database");
	        }
	        roles.add(parentRole);
	        utilisateur.setRoleUtilisateurs(roles);

	        utilisateur.setEtat(true);

	        return utilisateurRepo.save(utilisateur);
	    }

    public Utilisateur getUserByResetToken(String token) {
        return utilisateurRepo.findByResetToken(token);
    }

    public void updatePassword(Utilisateur user, String newPassword) {
        user.setPassword(newPassword);
        utilisateurRepo.save(user);
    }
   




	@Override
	public List<Utilisateur> listAll() {
		// TODO Auto-generated method stub
		return utilisateurRepo.findAll();
	}
} 