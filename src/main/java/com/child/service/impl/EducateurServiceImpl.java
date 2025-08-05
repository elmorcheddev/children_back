package com.child.service.impl;

import com.child.model.Educateur;
import com.child.repo.EducateurRepo;
import com.child.service.EducateurService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducateurServiceImpl implements EducateurService {

    @Autowired
    private EducateurRepo educateurRepo;

    @Override
    public Educateur createEducateur(Educateur educateur) {
        return educateurRepo.save(educateur);
    }

    @Override
    public Educateur updateEducateur(Long id, Educateur updatedEducateur) {
        Educateur existingEducateur = educateurRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Éducateur non trouvé avec l'ID : " + id));

        existingEducateur.setNom(updatedEducateur.getNom());
        existingEducateur.setPrenom(updatedEducateur.getPrenom());
        existingEducateur.setAdresse(updatedEducateur.getAdresse());
        existingEducateur.setTel(updatedEducateur.getTel());
        existingEducateur.setCin(updatedEducateur.getCin());
        existingEducateur.setEmail(updatedEducateur.getEmail());
        existingEducateur.setPassword(updatedEducateur.getPassword());
        existingEducateur.setDateNaissance(updatedEducateur.getDateNaissance());
        existingEducateur.setEtat(updatedEducateur.isEtat());
        existingEducateur.setRoleUtilisateurs(updatedEducateur.getRoleUtilisateurs());
        // Classe: à ne pas changer ici sauf logique métier spécifique

        return educateurRepo.save(existingEducateur);
    }

    @Override
    public void deleteEducateur(Long id) {
        if (!educateurRepo.existsById(id)) {
            throw new RuntimeException("Éducateur non trouvé avec l'ID : " + id);
        }
        educateurRepo.deleteById(id);
    }

    @Override
    public Educateur getEducateurById(Long id) {
        return educateurRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Éducateur non trouvé avec l'ID : " + id));
    }

    @Override
    public List<Educateur> getAllEducateurs() {
        return educateurRepo.findAll();
    }

    @Override
    public Educateur getEducateurByEmail(String email) {
        return educateurRepo.findByEmail(email);
    }
}
