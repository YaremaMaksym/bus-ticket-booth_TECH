package com.yaremax.busticketbooth_tech.services;

import com.yaremax.busticketbooth_tech.data.*;
import com.yaremax.busticketbooth_tech.exception.ResourceNotFoundException;
import com.yaremax.busticketbooth_tech.repositories.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

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
//        LocalTime currentTime = LocalTime.MIN;
        return allScheduleInfo.stream()
                .filter(s -> s.getDepartureTime().isAfter(currentTime))
                .sorted(Comparator.comparing(ScheduleInfo::getDepartureTime))
                .collect(Collectors.toList());
    }


    public Optional<List<ScheduleInfo>> findBestSchedulesToStop(Integer busStopId) {
        List<ScheduleInfo> allScheduleInfo = findAllScheduleInfo();

        return Optional.of(allScheduleInfo.stream()
                .filter(schedule -> schedule.getRoute().getRouteStops() != null ||
                                    schedule.getAvailableSeats() > 0)
                .map(schedule -> {
                    List<RouteStop> orderedRouteStops = schedule.getRoute().getRouteStops().stream()
                            .sorted(Comparator.comparing(RouteStop::getSequenceNumber))
                            .toList();

                    int totalTime = 0;
                    for (RouteStop rs : orderedRouteStops) {
                        totalTime += rs.getDepartureOffset();
                        if (rs.getBusStop().getId().equals(busStopId)) {
                            return new AbstractMap.SimpleEntry<>(schedule, totalTime);
                        }
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingInt(AbstractMap.SimpleEntry::getValue))
                .map(AbstractMap.SimpleEntry::getKey)
                .toList());
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