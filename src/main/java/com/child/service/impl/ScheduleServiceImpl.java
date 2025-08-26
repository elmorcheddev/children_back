package com.child.service.impl;

import com.child.model.Classe;
import com.child.model.Schedule;
import com.child.repo.ClasseRepository;
import com.child.repo.ScheduleRepository;
import com.child.service.ScheduleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ClasseRepository classeRepository;

    @Override
    public Schedule addSchedule(Long classeId, Schedule schedule) {
        Classe classe = classeRepository.findById(classeId)
                .orElseThrow(() -> new EntityNotFoundException("Classe not found with id: " + classeId));
        schedule.setClasse(classe);
        return scheduleRepository.save(schedule);
    }

    @Override
    public Schedule updateSchedule(Long id, Schedule schedule) {
        Schedule existing = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found with id: " + id));
        existing.setDay(schedule.getDay());
        existing.setActivity(schedule.getActivity());
        existing.setStartTime(schedule.getStartTime());
        existing.setEndTime(schedule.getEndTime());
        return scheduleRepository.save(existing);
    }

    @Override
    public void deleteSchedule(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new EntityNotFoundException("Schedule not found with id: " + id);
        }
        scheduleRepository.deleteById(id);
    }

    @Override
    public List<Schedule> getSchedulesByClasse(Long classeId) {
        Classe classe = classeRepository.findById(classeId)
                .orElseThrow(() -> new EntityNotFoundException("Classe not found with id: " + classeId));
        return scheduleRepository.findByClasse(classe);
    }

    @Override
    public List<Schedule> getSchedulesByClasseAndDay(Long classeId, String day) {
        return scheduleRepository.findByClasseIdAndDay(classeId, day);
    }
}
