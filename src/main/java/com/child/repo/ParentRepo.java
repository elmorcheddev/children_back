package com.child.repo;

import com.child.model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentRepo extends JpaRepository<Parent, Long> {
}
