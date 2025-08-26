package com.child.repo;

import com.child.model.Schedule;
import com.child.model.Classe;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByClasse(Classe classe);
    List<Schedule> findByClasseIdAndDay(Long classeId, String day);
}
