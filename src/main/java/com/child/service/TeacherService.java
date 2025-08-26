package com.child.service;

import com.child.model.Teacher;
import java.util.List;

public interface TeacherService {

    Teacher createTeacher(Teacher teacher);

    Teacher updateTeacher(Long id, Teacher teacher);

    void deleteTeacher(Long id);

    Teacher getTeacherById(Long id);

    List<Teacher> getAllTeachers();
}
