package com.child.service.impl;

import com.child.exception.ResourceNotFoundException;
import com.child.model.Parent;
import com.child.model.Utilisateur;
import com.child.repo.ParentRepository;
import com.child.repo.UtilisateurRepo;
import com.child.service.ParentService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class ParentServiceImpl implements ParentService {

    private final ParentRepository parentRepository;
    private final UtilisateurRepo utilisateurRepo;

    public ParentServiceImpl(ParentRepository parentRepository, UtilisateurRepo utilisateurRepo) {
        this.parentRepository = parentRepository;
        this.utilisateurRepo = utilisateurRepo;
    }

    @Override
       public Parent createParent(Parent parent, Long utilisateurId) {
        log.info("Creating new parent linked to utilisateurId {}", utilisateurId);

        Utilisateur utilisateur = utilisateurRepo.findById(utilisateurId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur not found with id " + utilisateurId));

        // Défensive check pour éviter doublon parent/utilisateur
        if (parentRepository.existsByUtilisateurId(utilisateurId)) {
            throw new IllegalStateException("Parent already exists for utilisateur with id " + utilisateurId);
        }

        parent.setUtilisateur(utilisateur);
        Parent saved = parentRepository.save(parent);
        log.info("Parent created with id {}", saved.getId());
        return saved;
    }

    @Override
    public Parent updateParent(Long id, Parent parent) {
        log.info("Updating parent with id {}", id);
        return parentRepository.findById(id).map(existing -> {
            existing.setRelation(parent.getRelation());

            // Only update children if passed is non-null, otherwise keep existing
            if (parent.getChildren() != null) {
                existing.getChildren().clear();
                existing.getChildren().addAll(parent.getChildren());
            }

            Parent updated = parentRepository.save(existing);
            log.info("Parent with id {} updated successfully", id);
            return updated;
        }).orElseThrow(() -> {
            log.error("Parent not found with id {}", id);
            return new ResourceNotFoundException("Parent not found with id " + id);
        });
    }

    @Override
    public void deleteParent(Long id) {
        log.info("Deleting parent with id {}", id);
        boolean exists = parentRepository.existsById(id);
        if (!exists) {
            log.warn("Attempt to delete non-existing parent with id {}", id);
            throw new ResourceNotFoundException("Parent not found with id " + id);
        }
        parentRepository.deleteById(id);
        log.info("Parent with id {} deleted successfully", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Parent> getParentById(Long id) {
        log.info("Fetching parent with id {}", id);
        return parentRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Parent> getAllParents() {
        log.info("Fetching all parents");
        return parentRepository.findAll();
    }
}
