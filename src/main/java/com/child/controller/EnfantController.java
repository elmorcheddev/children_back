package com.child.controller;

import com.child.model.Enfant;
import com.child.service.EnfantService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/enfants")
public class EnfantController {

    private final EnfantService enfantService;

    public EnfantController(EnfantService enfantService) {
        this.enfantService = enfantService;
    }

    // Ajouter un enfant à un parent existant
  
    @GetMapping("/list")
    public  List<Enfant>  getListEnfants() {
        return enfantService.getListEnfants() ;
    }
    // Lister les enfants d’un parent
    @GetMapping("/parent/{parentId}")
    public  List<Enfant>  getEnfantsByParent(@PathVariable Long parentId) {
        return enfantService.getEnfantsByParent(parentId) ;
    }

    // Récupérer un enfant par ID
    @GetMapping("/{id}")
    public ResponseEntity<Enfant> getEnfant(@PathVariable Long id) {
        return ResponseEntity.ok(enfantService.getEnfantById(id));
    }
    @PostMapping("/parent/{parentId}")
    public ResponseEntity<Enfant> addEnfant(@PathVariable Long parentId, @RequestBody Enfant enfant) {
        Enfant savedEnfant = enfantService.saveEnfant(parentId, enfant);
        return ResponseEntity.ok(savedEnfant);
    }

    @GetMapping("/{id}/receipt")
    public ResponseEntity<byte[]> getReceipt(@PathVariable Long id) {
        Enfant enfant = enfantService.getEnfantById(id);
        String filePath = "C:/recu_paiement/recu_parent_" + enfant.getParent().getId() + "_enfant_" + id + ".pdf";

        File file = new File(filePath);
        if (!file.exists()) {
            // PDF non trouvé → renvoyer 404
            return ResponseEntity.status(404)
                    .header(HttpHeaders.CONTENT_TYPE, "text/plain")
                    .body(("PDF non trouvé pour l'enfant avec id: " + id).getBytes());
        }

        try {
            byte[] pdf = Files.readAllBytes(file.toPath());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"recu_enfant_" + id + ".pdf\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf);
        } catch (IOException e) {
            return ResponseEntity.status(500)
                    .header(HttpHeaders.CONTENT_TYPE, "text/plain")
                    .body(("Erreur lors de la lecture du PDF pour l'enfant " + id).getBytes());
        }
    }


    // Mettre à jour un enfant
    @PutMapping("/{id}")
    public ResponseEntity<Enfant> updateEnfant(@PathVariable Long id, @RequestBody Enfant enfant) {
        return ResponseEntity.ok(enfantService.updateEnfant(id, enfant));
    }

    // Supprimer un enfant
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnfant(@PathVariable Long id) {
        enfantService.deleteEnfant(id);
        return ResponseEntity.noContent().build();
    }
}
