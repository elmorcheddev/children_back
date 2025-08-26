package com.child.service.impl;

import com.child.model.Classe;
import com.child.model.Teacher;
import com.child.repo.ClasseRepository;
import com.child.repo.TeacherRepository;
import com.child.service.ClasseService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClasseServiceImpl implements ClasseService {

    private final ClasseRepository ClasseRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public Classe createClasse(Classe Classe) {
        // Si teacher est associé
        if (Classe.getTeacher() != null && Classe.getTeacher().getId() != null) {
            Teacher teacher = teacherRepository.findById(Classe.getTeacher().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Teacher not found with id: " + Classe.getTeacher().getId()));
            Classe.setTeacher(teacher);
        }
        return ClasseRepository.save(Classe);
    }

    @Override
    public Classe updateClasse(Long id, Classe Classe) {
        Classe existing = ClasseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Classe not found with id: " + id));

        existing.setNomClass(Classe.getNomClass());
        existing.setCapacite(Classe.getCapacite());
        existing.setMinAge(Classe.getMinAge());
        existing.setMaxAge(Classe.getMaxAge());

        // mise à jour du teacher
        if (Classe.getTeacher() != null && Classe.getTeacher().getId() != null) {
            Teacher teacher = teacherRepository.findById(Classe.getTeacher().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Teacher not found with id: " + Classe.getTeacher().getId()));
            existing.setTeacher(teacher);
        } else {
            existing.setTeacher(null);
        }

        return ClasseRepository.save(existing);
    }

    @Override
    public void deleteClasse(Long id) {
        if (!ClasseRepository.existsById(id)) {
            throw new EntityNotFoundException("Classe not found with id: " + id);
        }
        ClasseRepository.deleteById(id);
    }

    @Override
    public Classe getClasseById(Long id) {
        return ClasseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Classe not found with id: " + id));
    }

    @Override
    public List<Classe> getAllClasses() {
        return ClasseRepository.findAll();
    }
}
