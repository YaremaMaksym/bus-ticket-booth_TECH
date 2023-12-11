package com.yaremax.busticketbooth_tech.services;

import com.yaremax.busticketbooth_tech.data.Schedule;
import com.yaremax.busticketbooth_tech.data.ScheduleInfo;
import com.yaremax.busticketbooth_tech.data.Ticket;
import com.yaremax.busticketbooth_tech.exception.ResourceNotFoundException;
import com.yaremax.busticketbooth_tech.repositories.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.*;

@Service
@AllArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final TicketService ticketService;

    public Schedule findById(Integer scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule with id " + scheduleId + " wasn't found"));
    }

    public ScheduleInfo findByIdScheduleInfo(Integer scheduleId) {
        return scheduleRepository.findByIdScheduleInfo(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule with id " + scheduleId + " wasn't found"));
    }


    public List<ScheduleInfo> findAllScheduleInfo() {
        List<ScheduleInfo> allScheduleInfo = scheduleRepository.findAllScheduleInfo();
        LocalTime currentTime = LocalTime.now();

        return allScheduleInfo.stream()
                .filter(s -> s.getDepartureTime().isAfter(currentTime))
                .sorted(Comparator.comparing(ScheduleInfo::getDepartureTime))
                .collect(Collectors.toList());
    }

    @Transactional
    public void addSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    @Transactional
    public void deleteSchedule(Integer scheduleId) {
        Schedule schedule = findById(scheduleId);
        scheduleRepository.delete(schedule);
    }

    public List<Integer> getAvailableSeats(Integer scheduleId) {
        Schedule schedule = findById(scheduleId);

        int totalSeats = schedule.getBus().getSeatCapacity();

        List<Integer> availableSeats = new ArrayList<>();
        for (int i = 1; i <= totalSeats; i++) {
            availableSeats.add(i);
        }

        List<Ticket> bookedTickets = ticketService.findAllByScheduleAndTicketStatus(scheduleId, "booked");
        for (Ticket ticket : bookedTickets) {
            availableSeats.remove(ticket.getSeatNumber());
        }

        return availableSeats;
    }

    // TODO: додати update
}