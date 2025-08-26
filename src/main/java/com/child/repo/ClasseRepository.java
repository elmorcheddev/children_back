package com.child.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.child.model.Classe;
import java.util.List;
import java.util.Optional;


public interface ClasseRepository extends JpaRepository<Classe, Long>{

 
	Optional<Classe> findByNomClass(String nomGroup);
	
}
