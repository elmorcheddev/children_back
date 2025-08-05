package com.child.controller;

import com.child.model.Educateur;
import com.child.service.EducateurService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/educateurs")
@CrossOrigin(origins = "*") // adapte l'origine selon ton front
public class EducateurController {

    private final EducateurService educateurService;

    @Autowired
    public EducateurController(EducateurService educateurService) {
        this.educateurService = educateurService;
    }

    // Créer un éducateur
    @PostMapping
    public ResponseEntity<Educateur> createEducateur(@RequestBody Educateur educateur) {
        Educateur created = educateurService.createEducateur(educateur);
        return ResponseEntity.ok(created);
    }

    // Récupérer la liste des éducateurs
    @GetMapping
    public ResponseEntity<List<Educateur>> getAllEducateurs() {
        List<Educateur> list = educateurService.getAllEducateurs();
        return ResponseEntity.ok(list);
    }

    // Récupérer un éducateur par ID
    @GetMapping("/{id}")
    public ResponseEntity<Educateur> getEducateurById(@PathVariable Long id) {
        Educateur educateur = educateurService.getEducateurById(id);
        return ResponseEntity.ok(educateur);
    }

    // Mettre à jour un éducateur
    @PutMapping("/{id}")
    public ResponseEntity<Educateur> updateEducateur(@PathVariable Long id, @RequestBody Educateur educateur) {
        Educateur updated = educateurService.updateEducateur(id, educateur);
        return ResponseEntity.ok(updated);
    }

    // Supprimer un éducateur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEducateur(@PathVariable Long id) {
        educateurService.deleteEducateur(id);
        return ResponseEntity.noContent().build();
    }

    // Rechercher un éducateur par email
    @GetMapping("/search")
    public ResponseEntity<Educateur> getEducateurByEmail(@RequestParam String email) {
        Educateur educateur = educateurService.getEducateurByEmail(email);
        return ResponseEntity.ok(educateur);
    }
}
