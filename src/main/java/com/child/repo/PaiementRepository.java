package com.child.repo;


import com.child.model.Paiement;
import com.child.model.Parent;
import com.child.model.Enfant;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface PaiementRepository extends JpaRepository<Paiement, Long> {
List<Paiement> findByParent(Parent parent);
  Paiement  findByEnfant(Enfant enfant);
}