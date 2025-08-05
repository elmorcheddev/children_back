package com.child.service.impl;

import com.child.model.Classe;
import com.child.repo.ClasseRepo;
import com.child.service.ClasseService;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ClasseServiceImpl implements ClasseService {

    private final ClasseRepo classeRepo;

    @Autowired
    public ClasseServiceImpl(ClasseRepo classeRepo) {
        this.classeRepo = classeRepo;
    }

    @Override
    public Classe createClasse(Classe classe) {
        if (classe == null) {
            throw new IllegalArgumentException("La classe ne peut pas être nulle.");
        }

        return classeRepo.save(classe);
    }

    @Override
    public Classe updateClasse(Long id, Classe updatedClasse) {
        Classe existingClasse = classeRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Classe introuvable avec l'ID : " + id));

        // Mise à jour des champs
        if (updatedClasse.getNom() != null) existingClasse.setNom(updatedClasse.getNom());
        if (updatedClasse.getNiveau() != null) existingClasse.setNiveau(updatedClasse.getNiveau());
        if (updatedClasse.getEducateur() != null) existingClasse.setEducateur(updatedClasse.getEducateur());
        if (updatedClasse.getEnfants() != null) existingClasse.setEnfants(updatedClasse.getEnfants());

        return classeRepo.save(existingClasse);
    }

    @Override
    public void deleteClasse(Long id) {
        if (!classeRepo.existsById(id)) {
            throw new IllegalArgumentException("Impossible de supprimer : classe introuvable avec l'ID : " + id);
        }

        classeRepo.deleteById(id);
    }

    @Override
    public Classe getClasseById(Long id) {
        return classeRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Classe introuvable avec l'ID : " + id));
    }

    @Override
    public List<Classe> getAllClasses() {
        return classeRepo.findAll();
    }
}
