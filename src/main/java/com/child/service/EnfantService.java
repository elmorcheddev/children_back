package com.child.service;

import com.child.model.Enfant;
import java.util.List;

public interface EnfantService {
    Enfant saveEnfant(Long parentId, Enfant enfant);
    List<Enfant> getEnfantsByParent(Long parentId);
    Enfant getEnfantById(Long id);
    Enfant updateEnfant(Long id, Enfant enfant);
    void deleteEnfant(Long id);
	List<Enfant> getListEnfants();
 }
