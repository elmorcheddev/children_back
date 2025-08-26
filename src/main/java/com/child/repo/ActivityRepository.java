package com.child.repo;

import com.child.model.Activity;
import com.child.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findBySchedule(Schedule schedule);
}
