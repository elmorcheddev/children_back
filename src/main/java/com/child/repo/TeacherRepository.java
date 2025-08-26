package com.child.repo;

import com.child.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.child.model.Classe;
 import java.util.List;


@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
	
 }