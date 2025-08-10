package com.child.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.child.model.Utilisateur;
import com.child.service.UtilisateurService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Utilisateur>> all() {
        return ResponseEntity.ok(utilisateurService.listAll());
    }

    @PostMapping
    public ResponseEntity<Utilisateur> createUser(@Valid @RequestBody Utilisateur utilisateur) {
        Utilisateur createdUser = utilisateurService.createUser(utilisateur);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getById(@PathVariable Long id) {
        Utilisateur u = utilisateurService.findByIdUtilisateur(id);
        if (u == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(u);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> update(@PathVariable Long id, @RequestBody Utilisateur utilisateur) {
        // Implémenter update dans service si besoin
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // Implémenter delete dans service si besoin
        return ResponseEntity.noContent().build();
    }
}
