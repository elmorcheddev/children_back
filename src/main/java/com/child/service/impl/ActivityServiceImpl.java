package com.child.service.impl;

import com.child.model.Activity;
import com.child.model.Schedule;
import com.child.repo.ActivityRepository;
import com.child.repo.ScheduleRepository;
import com.child.service.ActivityService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    public Activity addActivity(Long scheduleId, Activity activity) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found with id: " + scheduleId));
        activity.setSchedule(schedule);
        return activityRepository.save(activity);
    }

    @Override
    public Activity updateActivity(Long id, Activity activity) {
        Activity existing = activityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found with id: " + id));
        existing.setName(activity.getName());
        existing.setStartTime(activity.getStartTime());
        existing.setEndTime(activity.getEndTime());
        return activityRepository.save(existing);
    }

    @Override
    public void deleteActivity(Long id) {
        if (!activityRepository.existsById(id)) {
            throw new EntityNotFoundException("Activity not found with id: " + id);
        }
        activityRepository.deleteById(id);
    }

    @Override
    public List<Activity> getActivitiesBySchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found with id: " + scheduleId));
        return activityRepository.findBySchedule(schedule);
    }
}
