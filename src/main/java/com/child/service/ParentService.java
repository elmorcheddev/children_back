package com.child.service;

import com.child.model.Parent;

import java.util.List;
import java.util.Optional;

public interface ParentService {
    Parent updateParent(Long id, Parent parent);
    void deleteParent(Long id);
    Optional<Parent> getParentById(Long id);
    List<Parent> getAllParents();
	Parent createParent(Parent parent, Long utilisateurId);
}
