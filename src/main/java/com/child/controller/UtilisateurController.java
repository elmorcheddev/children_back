package com.child.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.child.model.Utilisateur;
import com.child.service.UtilisateurService;

@RestController
@RequestMapping("/api/utilisateur")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("/all")
    public List<Utilisateur> all() {
        return utilisateurService.listAll();
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<Utilisateur> getById(@PathVariable Long id) {
        Utilisateur u = utilisateurService.findByIdUtilisateur(id);
        if (u == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(u);
    }

    @PostMapping("/create")
    public ResponseEntity<Utilisateur> create(@RequestBody Utilisateur utilisateur) {
        Utilisateur saved = utilisateurService.ajouterSuperAdmin(utilisateur);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Utilisateur> update(@PathVariable Long id, @RequestBody Utilisateur utilisateur) {
      
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
         return ResponseEntity.noContent().build();
    }
}
