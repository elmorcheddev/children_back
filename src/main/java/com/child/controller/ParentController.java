package com.child.controller;

import com.child.model.Parent;
import com.child.service.ParentService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parents")
public class ParentController {

    private final ParentService parentService;

    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    // POST /api/parents?utilisateurId=15
    @PostMapping(value="/create/{utilisateurId}")
    public ResponseEntity<Parent> createParent(
            @PathVariable Long utilisateurId,
            @Valid @RequestBody Parent parent) {
        
        Parent createdParent = parentService.createParent(parent, utilisateurId);
        return new ResponseEntity<>(createdParent, HttpStatus.CREATED);
    }

    // Other CRUD methods (optional, for completeness)

    @GetMapping
    public ResponseEntity<List<Parent>> getAllParents() {
        List<Parent> parents = parentService.getAllParents();
        return ResponseEntity.ok(parents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parent> getParentById(@PathVariable Long id) {
        return parentService.getParentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParent(@PathVariable Long id) {
        parentService.deleteParent(id);
        return ResponseEntity.noContent().build();
    }
}
