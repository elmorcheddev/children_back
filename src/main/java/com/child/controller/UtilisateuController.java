package com.child.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.child.model.Utilisateur;
import com.child.service.UtilisateurService;

@RestController
@RequestMapping(value = "/api/utilisateur")
public class UtilisateuController {
@Autowired
private UtilisateurService utilisateurService;


@PostMapping(value = "/ajouterSuperAdmin")
public Utilisateur ajouterSuperAdmin(@RequestBody Utilisateur utilisateur) {
	 
 		return utilisateurService.ajouterSuperAdmin(utilisateur);
	 
}
@PostMapping(value = "/inscriptionSuper")
public Utilisateur inscriptionSuper(@RequestBody Utilisateur utilisateur) {
	 
		return utilisateurService.ajouterSuperAdmin(utilisateur);
	 
}
@GetMapping(value = "/listdocteur")
public List<Utilisateur> listDocteurs(){
	return utilisateurService.findByRoleName("DOCTEUR");
}
@GetMapping(value = "/listdocteurPatient")
public List<Utilisateur> listdocteurPatient(){
	return utilisateurService.findByRoleName("DOCTEUR");
}
@GetMapping(value = "/all")
public List<Utilisateur> all(){
	return utilisateurService.listAll();
}
@GetMapping(value = "/listAssistant")
public List<Utilisateur> listAssistant(){
	return utilisateurService.findByRoleName("ASSISTANT");
}
@GetMapping(value = "/listResponsableStock")
public List<Utilisateur> listResponsableStock(){
	return utilisateurService.findByRoleName("RESPONSABLESTOCK");
}
@GetMapping(value = "/listScretaire")
public List<Utilisateur> listScretaire(){
	return utilisateurService.findByRoleName("SECRITAIRE");
}
@GetMapping(value = "/listPatient")
public List<Utilisateur> listPatient(){
	return utilisateurService.findByRoleName("PATIENT");
}

@GetMapping(value = "/byId/{id}")
public Utilisateur getByID(@PathVariable Long id) {
	return utilisateurService.findByIdUtilisateur(id);
}
@GetMapping("/patientcountByDate")
public List<Object[]> getCountPatientsByDateCreation() {
    return utilisateurService. countPatientsByDateCreation();
}
}
