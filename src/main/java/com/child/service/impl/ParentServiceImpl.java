package com.child.service.impl;

import com.child.model.Parent;
import com.child.repo.ParentRepo;
import com.child.service.ParentService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParentServiceImpl implements ParentService {

    private final ParentRepo parentRepository;

    @Override
    public Parent createParent(Parent parent) {
        if (parentRepository.existsByEmail(parent.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + parent.getEmail());
        }
        return parentRepository.save(parent);
    }

    @Override
    public Parent updateParent(Long id, Parent parent) {
        Parent existing = parentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parent not found with id: " + id));

        // prevent updating email to one that already exists
        if (!existing.getEmail().equals(parent.getEmail())
                && parentRepository.existsByEmail(parent.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + parent.getEmail());
        }

        existing.setNom(parent.getNom());
        existing.setPrenom(parent.getPrenom());
        existing.setRelation(parent.getRelation());
        existing.setCin(parent.getCin());
        existing.setAdresse(parent.getAdresse());
        existing.setTelPrincipal(parent.getTelPrincipal());
        existing.setTelSecondaire(parent.getTelSecondaire());
        existing.setEmail(parent.getEmail());
        existing.setProfession(parent.getProfession());
        existing.setLieuTravail(parent.getLieuTravail());
        existing.setTelTravail(parent.getTelTravail());
        existing.setContactUrgenceNom(parent.getContactUrgenceNom());
        existing.setContactUrgenceTel(parent.getContactUrgenceTel());
        existing.setEtat(parent.isEtat());

        return parentRepository.save(existing);
    }


    @Override
    public void deleteParent(Long id) {
        if (!parentRepository.existsById(id)) {
            throw new EntityNotFoundException("Parent not found with id: " + id);
        }
        parentRepository.deleteById(id);
    }

    @Override
    public Parent getParentById(Long id) {
        return parentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parent not found with id: " + id));
    }

    @Override
    public List<Parent> getAllParents() {
        return parentRepository.findAll();
    }
}
