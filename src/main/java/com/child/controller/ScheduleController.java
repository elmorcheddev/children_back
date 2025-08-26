package com.child.controller;

import com.child.model.Schedule;
import com.child.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/{classeId}")
    public Schedule addSchedule(@PathVariable Long classeId, @RequestBody Schedule schedule) {
        return scheduleService.addSchedule(classeId, schedule);
    }

    @PutMapping("/{id}")
    public Schedule updateSchedule(@PathVariable Long id, @RequestBody Schedule schedule) {
        return scheduleService.updateSchedule(id, schedule);
    }

    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
    }

    @GetMapping("/classe/{classeId}")
    public List<Schedule> getSchedulesByClasse(@PathVariable Long classeId) {
        return scheduleService.getSchedulesByClasse(classeId);
    }

    @GetMapping("/classe/{classeId}/day/{day}")
    public List<Schedule> getSchedulesByClasseAndDay(@PathVariable Long classeId, @PathVariable String day) {
        return scheduleService.getSchedulesByClasseAndDay(classeId, day);
    }
}
