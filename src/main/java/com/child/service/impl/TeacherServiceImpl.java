package com.child.service.impl;

import com.child.model.Enfant;
import com.child.model.Teacher;
import com.child.repo.EnfantRepo;
import com.child.repo.TeacherRepository;
import com.child.service.TeacherService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final EnfantRepo enfantRepository;

    @Override
    public Teacher createTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher updateTeacher(Long id, Teacher teacher) {
        Teacher existing = teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found with id: " + id));

        // mise à jour des attributs
        existing.setNom(teacher.getNom());
        existing.setPrenom(teacher.getPrenom());
        existing.setEmail(teacher.getEmail());
        existing.setTelephone(teacher.getTelephone());
        existing.setAdresse(teacher.getAdresse());
        existing.setSpecialite(teacher.getSpecialite());
        existing.setExperience(teacher.getExperience());
        existing.setDiplome(teacher.getDiplome());
        existing.setSexe(teacher.getSexe());
        existing.setDateNaissance(teacher.getDateNaissance());

        return teacherRepository.save(existing);
    }

    @Override
    public void deleteTeacher(Long id) {
        if (!teacherRepository.existsById(id)) {
            throw new EntityNotFoundException("Teacher not found with id: " + id);
        }
        teacherRepository.deleteById(id);
    }

    @Override
    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found with id: " + id));
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    // Enfants d’un teacher
    public List<Enfant> getEnfantsByTeacher(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found with id: " + teacherId));
        return enfantRepository.findByClasse_Teacher(teacher);
    }
}
