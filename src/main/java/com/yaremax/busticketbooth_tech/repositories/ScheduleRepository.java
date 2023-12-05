package com.yaremax.busticketbooth_tech.repositories;

import com.yaremax.busticketbooth_tech.data.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
}