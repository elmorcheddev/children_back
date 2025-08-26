package com.child.service;

import java.util.List;

import com.child.model.Schedule;

public interface ScheduleService {

	List<Schedule> getSchedulesByClasseAndDay(Long classeId, String day);

	List<Schedule> getSchedulesByClasse(Long classeId);

	void deleteSchedule(Long id);

	Schedule updateSchedule(Long id, Schedule schedule);

	Schedule addSchedule(Long classeId, Schedule schedule);

}
