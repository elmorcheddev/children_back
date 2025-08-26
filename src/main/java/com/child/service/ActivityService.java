package com.child.service;

import com.child.model.Activity;
import java.util.List;

public interface ActivityService {
    Activity addActivity(Long scheduleId, Activity activity);
    Activity updateActivity(Long id, Activity activity);
    void deleteActivity(Long id);
    List<Activity> getActivitiesBySchedule(Long scheduleId);
}
