package com.child.service;

import com.child.model.Classe;

import java.util.List;

public interface ClasseService {
    Classe createClasse(Classe Classe);
    Classe updateClasse(Long id, Classe Classe);
    void deleteClasse(Long id);
    Classe getClasseById(Long id);
    List<Classe> getAllClasses();
}
