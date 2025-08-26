package com.child.service;


import com.child.model.Paiement;


import java.util.List;


public interface PaiementService {
Paiement createPaiement(Long parentId, Long enfantId, Double montant, String mois, String modePaiement);
Paiement getById(Long id);
List<Paiement> getByParent(Long parentId);
Paiement getByEnfant(Long enfantId);
List<Paiement> getAll();
byte[] getRecuPdf(Long paiementId);
}