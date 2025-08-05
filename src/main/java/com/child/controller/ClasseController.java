package com.child.controller;

import com.child.model.Classe;
import com.child.service.ClasseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
@CrossOrigin(origins = "*") // à adapter selon ton front Angular
public class ClasseController {

    @Autowired
    private ClasseService classeService;

    // ✅ Ajouter une nouvelle classe
    @PostMapping
    public ResponseEntity<Classe> createClasse(@RequestBody Classe classe) {
        Classe newClasse = classeService.createClasse(classe);
        return ResponseEntity.ok(newClasse);
    }

    // ✅ Obtenir la liste de toutes les classes
    @GetMapping
    public ResponseEntity<List<Classe>> getAllClasses() {
        return ResponseEntity.ok(classeService.getAllClasses());
    }

    // ✅ Obtenir une classe par ID
    @GetMapping("/{id}")
    public ResponseEntity<Classe> getClasseById(@PathVariable Long id) {
        return ResponseEntity.ok(classeService.getClasseById(id));
    }

    // ✅ Mettre à jour une classe
    @PutMapping("/{id}")
    public ResponseEntity<Classe> updateClasse(@PathVariable Long id, @RequestBody Classe classe) {
        Classe updatedClasse = classeService.updateClasse(id, classe);
        return ResponseEntity.ok(updatedClasse);
    }

    // ✅ Supprimer une classe
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClasse(@PathVariable Long id) {
        classeService.deleteClasse(id);
        return ResponseEntity.noContent().build();
    }
}
