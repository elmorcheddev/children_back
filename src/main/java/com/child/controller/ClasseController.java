package com.child.controller;

import com.child.model.Classe;
import com.child.service.ClasseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Classes")
@RequiredArgsConstructor
public class ClasseController {

    private final ClasseService ClasseService;

    @PostMapping
    public ResponseEntity<Classe> createClasse(@RequestBody Classe Classe) {
        return ResponseEntity.ok(ClasseService.createClasse(Classe));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Classe> updateClasse(@PathVariable Long id, @RequestBody Classe Classe) {
        return ResponseEntity.ok(ClasseService.updateClasse(id, Classe));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClasse(@PathVariable Long id) {
        ClasseService.deleteClasse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Classe> getClasseById(@PathVariable Long id) {
        return ResponseEntity.ok(ClasseService.getClasseById(id));
    }

    @GetMapping
    public ResponseEntity<List<Classe>> getAllClasses() {
        return ResponseEntity.ok(ClasseService.getAllClasses());
    }
}
