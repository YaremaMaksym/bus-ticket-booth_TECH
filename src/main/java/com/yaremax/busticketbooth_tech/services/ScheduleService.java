package com.yaremax.busticketbooth_tech.services;

import com.yaremax.busticketbooth_tech.data.Schedule;
import com.yaremax.busticketbooth_tech.data.ScheduleInfo;
import com.yaremax.busticketbooth_tech.exception.ResourceNotFoundException;
import com.yaremax.busticketbooth_tech.repositories.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    public List<ScheduleInfo> findAllScheduleInfo() {
        return scheduleRepository.findAllScheduleInfo();
    }

    public Optional<Schedule> findById(Integer id) {
        return scheduleRepository.findById(id);
    }

    @Transactional
    public Schedule addSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Transactional
    public void deleteSchedule(Integer id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule with id " + id + " wasn't found"));
        scheduleRepository.delete(schedule);
    }

    // TODO: додати update
}