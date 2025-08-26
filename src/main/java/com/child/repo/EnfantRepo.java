package com.child.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.child.model.Classe;
import com.child.model.Enfant;
 import com.child.model.Parent;
import com.child.model.Teacher;


public interface EnfantRepo extends JpaRepository<Enfant, Long>{

	List<Enfant> findByParent(Parent parent);

	List<Enfant> findByClasse(Classe classe);

	List<Enfant> findByClasse_Teacher(Teacher teacher);  

 
}
