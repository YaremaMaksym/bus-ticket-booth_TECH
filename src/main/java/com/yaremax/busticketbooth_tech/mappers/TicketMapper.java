package com.yaremax.busticketbooth_tech.mappers;

import com.yaremax.busticketbooth_tech.data.BusStop;
import com.yaremax.busticketbooth_tech.data.Schedule;
import com.yaremax.busticketbooth_tech.data.Ticket;
import com.yaremax.busticketbooth_tech.dto.TicketDto;
import com.yaremax.busticketbooth_tech.exception.ResourceNotFoundException;
import com.yaremax.busticketbooth_tech.repositories.BusStopRepository;
import com.yaremax.busticketbooth_tech.repositories.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

@Service
@ApplicationScope
@AllArgsConstructor
public class TicketMapper {
    private final ScheduleRepository scheduleRepository;
    private final BusStopRepository busStopRepository;

    public Ticket toEntity(TicketDto ticketDto) {
        Schedule schedule = scheduleRepository.findById(ticketDto.getScheduleId())
                .orElseThrow(() -> new ResourceNotFoundException("Schedule with id " + ticketDto.getScheduleId() + " wasn't found"));

        BusStop busStop = busStopRepository.findById(ticketDto.getBusStopId())
                .orElseThrow(() -> new ResourceNotFoundException("Bus stop with id " + ticketDto.getBusStopId() + " wasn't found"));


        return new Ticket(schedule,
                busStop,
                ticketDto.getSeatNumber(),
                ticketDto.getTicketStatus());
    }
}
