package com.child.service.impl;

import com.child.model.Classe;
import com.child.model.Enfant;
import com.child.model.Parent;
import com.child.repo.ClasseRepository;
import com.child.repo.EnfantRepo;
import com.child.repo.ParentRepo;
import com.child.repo.TeacherRepository;
import com.child.service.EnfantService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class EnfantServiceImpl implements EnfantService {

    private final EnfantRepo enfantRepository;
    private final ParentRepo parentRepository;
    private final TeacherRepository teacherRepo;
    private final ClasseRepository groupRepository;

    public EnfantServiceImpl(ClasseRepository groupRepository,
                             EnfantRepo enfantRepository,
                             TeacherRepository teacherRepo,
                             ParentRepo parentRepository) {
        this.enfantRepository = enfantRepository;
        this.parentRepository = parentRepository;
        this.teacherRepo = teacherRepo;
        this.groupRepository = groupRepository;
    }

    @Override
    public Enfant saveEnfant(Long parentId, Enfant enfant) {
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("❌ Parent introuvable avec ID: " + parentId));
        enfant.setParent(parent);

        // 🔹 Déterminer la classe selon l’âge (seulement 3 classes valides)
        if (enfant.getDateNaissance() != null && !enfant.getDateNaissance().isEmpty()) {
            LocalDate birthDate = LocalDate.parse(enfant.getDateNaissance());
            int age = Period.between(birthDate, LocalDate.now()).getYears();

            Classe group;

            if (age >= 3 && age < 4) {
                group = groupRepository.findByNomClass("Petite section")
                        .orElseThrow(() -> new RuntimeException("❌ Classe 'Petite section' introuvable"));
            } else if (age >= 4 && age < 5) {
                group = groupRepository.findByNomClass("Moyenne section")
                        .orElseThrow(() -> new RuntimeException("❌ Classe 'Moyenne section' introuvable"));
            } else if (age >= 5 ) {
                group = groupRepository.findByNomClass("Grande section")
                        .orElseThrow(() -> new RuntimeException("❌ Classe 'Grande section' introuvable"));
            } else {
                throw new RuntimeException("⚠️ L’âge de l’enfant (" + age + " ans) ne correspond à aucune classe disponible !");
            }

            // 🔹 Vérifier la capacité avant d’ajouter
            int capacite = Integer.parseInt(group.getCapacite());
            int enfantsActuels = enfantRepository.findByClasse(group).size();

            if (enfantsActuels >= capacite) {
                throw new RuntimeException("⚠️ Classe " + group.getNomClass() + " est déjà pleine !");
            }

            enfant.setClasse(group);
        }
        enfant.setPayee(false);
        return enfantRepository.save(enfant);
    }

    @Override
    public List<Enfant> getEnfantsByParent(Long parentId) {
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("❌ Parent introuvable avec ID: " + parentId));
        return enfantRepository.findByParent(parent);
    }

    @Override
    public Enfant getEnfantById(Long id) {
        return enfantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Enfant introuvable avec ID: " + id));
    }

    @Override
    public Enfant updateEnfant(Long id, Enfant enfant) {
        Enfant existing = getEnfantById(id);
        existing.setNom(enfant.getNom());
        existing.setPrenom(enfant.getPrenom());
        existing.setDateNaissance(enfant.getDateNaissance());
        existing.setClasse(enfant.getClasse());
        existing.setEtatSante(enfant.getEtatSante());
        return enfantRepository.save(existing);
    }

    @Override
    public void deleteEnfant(Long id) {
        enfantRepository.deleteById(id);
    }

    @Override
    public List<Enfant> getListEnfants() {
        return enfantRepository.findAll();
    }
}
