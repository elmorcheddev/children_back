package com.child.service;

import com.child.model.Classe;
import java.util.List;

public interface ClasseService {
    
    Classe createClasse(Classe classe);

    Classe updateClasse(Long id, Classe classe);

    void deleteClasse(Long id);

    Classe getClasseById(Long id);

    List<Classe> getAllClasses();
}
