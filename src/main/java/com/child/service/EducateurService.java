package com.child.service;

import com.child.model.Educateur;

import java.util.List;

public interface EducateurService {

    Educateur createEducateur(Educateur educateur);

    Educateur updateEducateur(Long id, Educateur educateur);

    void deleteEducateur(Long id);

    Educateur getEducateurById(Long id);

    List<Educateur> getAllEducateurs();

    Educateur getEducateurByEmail(String email);
}
