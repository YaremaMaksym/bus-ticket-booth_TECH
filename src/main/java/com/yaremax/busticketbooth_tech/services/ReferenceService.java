package com.yaremax.busticketbooth_tech.services;

import com.yaremax.busticketbooth_tech.data.BusStop;
import com.yaremax.busticketbooth_tech.data.Schedule;
import com.yaremax.busticketbooth_tech.repositories.BusStopRepository;
import com.yaremax.busticketbooth_tech.repositories.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReferenceService {
    private final ScheduleRepository scheduleRepository;
    private final BusStopRepository busStopRepository;

    public Schedule getScheduleReferenceById(Integer id) {
        return scheduleRepository.getReferenceById(id);
    }

    public BusStop getBusStopReferenceById(Integer id) {
        return busStopRepository.getReferenceById(id);
    }
}
