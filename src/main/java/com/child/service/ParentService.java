package com.child.service;

import com.child.model.Parent;
import java.util.List;

public interface ParentService {
    Parent createParent(Parent parent);
    Parent updateParent(Long id, Parent parent);
    void deleteParent(Long id);
    Parent getParentById(Long id);
    List<Parent> getAllParents();
}
