package com.child.controller;

import com.child.model.Enfant;
import com.child.model.Teacher;
import com.child.service.TeacherService;
import com.child.repo.EnfantRepo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;
    private final EnfantRepo enfantRepo;

    // Créer un teacher
    @PostMapping
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher) {
        Teacher savedTeacher = teacherService.createTeacher(teacher);
        return ResponseEntity.ok(savedTeacher);
    }

    // Mettre à jour un teacher
    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable Long id, @RequestBody Teacher teacher) {
        Teacher updatedTeacher = teacherService.updateTeacher(id, teacher);
        return ResponseEntity.ok(updatedTeacher);
    }

    // Supprimer un teacher
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }

    // Récupérer un teacher par ID
    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Long id) {
        Teacher teacher = teacherService.getTeacherById(id);
        return ResponseEntity.ok(teacher);
    }

    // Lister tous les teachers
    @GetMapping("/list")
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }

    // Récupérer tous les enfants assignés à un teacher par son ID
    @GetMapping("/{id}/enfants")
    public ResponseEntity<List<Enfant>> getEnfantsByTeacher(@PathVariable Long id) {
        Teacher teacher = teacherService.getTeacherById(id);
        List<Enfant> enfants = enfantRepo.findByClasse_Teacher(teacher);
        return ResponseEntity.ok(enfants);
    }
}
