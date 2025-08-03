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

import com.child.model.RoleUtilisateur;
import com.child.model.Utilisateur;
import com.child.repo.RoleRepo;
import com.child.repo.UtilisateurRepo;
import com.child.service.UtilisateurService;
@Service
public class UtilisateurServiceImpl implements UtilisateurService {
@Autowired
private UtilisateurRepo utilisateurRepo;
@Autowired private RoleRepo roleRepo;
@Autowired private BCryptPasswordEncoder passwordEncoder;
@Autowired private DocteurRepo docteurRepo;
@Autowired
private JavaMailSender mailSender;

	

	@Override
	public Utilisateur ajouterSuperAdmin(Utilisateur utilisateur) {
 	        RoleUtilisateur roles = roleRepo.findByNomRoles("SUPERADMIN");
 		    boolean adminbyRolesExists = utilisateurRepo.existsByRoleUtilisateursContains(Collections.singleton(roleRepo.findByNomRoles("SUPERADMIN")));

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
	 

    public void processForgotPassword(String email) {
        Utilisateur user = utilisateurRepo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with email: " + email);
        }

        // Generate a reset token
        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        utilisateurRepo.save(user);

        // Send email with reset link
        String resetLink = "http://localhost:4200/reset-password?token=" + token;
        sendEmail(user.getEmail(), resetLink);
    }

    private void sendEmail(String to, String resetLink) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Password Reset Request");
            message.setText("Click the link to reset your password: " + resetLink);
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            // Log the exception and rethrow it or handle it as needed
        }
    }

    public Utilisateur getUserByResetToken(String token) {
        return utilisateurRepo.findByResetToken(token);
    }

    public void updatePassword(Utilisateur user, String newPassword) {
        user.setPassword(newPassword);
        utilisateurRepo.save(user);
    }
   
	@Override
	public List<Object[]> countPatientsByDateCreation() {
		// TODO Auto-generated method stub
        return utilisateurRepo.countPatientsByDay();
	}
} 