package com.child.controller;


import com.child.model.Paiement;
import com.child.service.PaiementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/paiements")
@RequiredArgsConstructor
public class PaiementController {


private final PaiementService paiementService;


public record CreatePaiementRequest(Long parentId, Long enfantId, Double montant, String mois, String modePaiement) {}


@PostMapping
public ResponseEntity<Paiement> createPaiement(@RequestBody CreatePaiementRequest req) {
    Paiement paiement = paiementService.createPaiement(
            req.parentId(), req.enfantId(), req.montant(), req.mois(), req.modePaiement()
    );
    return new ResponseEntity<>(paiement, HttpStatus.CREATED);
}




@GetMapping("/{id}")
public ResponseEntity<Paiement> getOne(@PathVariable Long id) { return ResponseEntity.ok(paiementService.getById(id)); }


@GetMapping
public ResponseEntity<List<Paiement>> getAll() { return ResponseEntity.ok(paiementService.getAll()); }


@GetMapping("/parent/{parentId}")
public ResponseEntity<List<Paiement>> byParent(@PathVariable Long parentId) { return ResponseEntity.ok(paiementService.getByParent(parentId)); }


@GetMapping("/enfant/{enfantId}")
public ResponseEntity<Paiement> byEnfant(@PathVariable Long enfantId) { return ResponseEntity.ok(paiementService.getByEnfant(enfantId)); }


@GetMapping("/{id}/recu")
public ResponseEntity<byte[]> downloadRecu(@PathVariable Long id) {
byte[] pdf = paiementService.getRecuPdf(id);
HttpHeaders headers = new HttpHeaders();
headers.setContentType(MediaType.APPLICATION_PDF);
headers.setContentDisposition(ContentDisposition.attachment().filename("recu-" + id + ".pdf").build());
return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
}
}