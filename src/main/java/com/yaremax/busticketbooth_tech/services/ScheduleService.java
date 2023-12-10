package com.yaremax.busticketbooth_tech.services;

import com.yaremax.busticketbooth_tech.data.Schedule;
import com.yaremax.busticketbooth_tech.data.ScheduleInfo;
import com.yaremax.busticketbooth_tech.data.Ticket;
import com.yaremax.busticketbooth_tech.exception.ResourceNotFoundException;
import com.yaremax.busticketbooth_tech.repositories.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final TicketService ticketService;

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

    public List<Integer> getAvailableSeats(Integer scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule with id " + scheduleId + " wasn't found"));

        int totalSeats = schedule.getBus().getSeatCapacity();

        List<Integer> availableSeats = new ArrayList<>();
        for (int i = 1; i <= totalSeats; i++) {
            availableSeats.add(i);
        }

        List<Ticket> bookedTickets = ticketService.findAllByScheduleAndTicketStatus(schedule, "booked");
        for (Ticket ticket : bookedTickets) {
            availableSeats.remove(ticket.getSeatNumber());
        }

        return availableSeats;
    }

    // TODO: додати update
}