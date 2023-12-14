package com.yaremax.busticketbooth_tech.mappers;

import com.yaremax.busticketbooth_tech.data.Ticket;
import com.yaremax.busticketbooth_tech.dto.TicketDto;
import com.yaremax.busticketbooth_tech.services.BusStopService;
import com.yaremax.busticketbooth_tech.services.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

@Service
@ApplicationScope
@AllArgsConstructor
public class TicketMapper {
    private final ScheduleService scheduleService;
    private final BusStopService busStopService;

    public Ticket toEntity(TicketDto ticketDto) {
        return new Ticket(scheduleService.findById(ticketDto.getScheduleId()),
                busStopService.findById(ticketDto.getBusStopId()),
                ticketDto.getSeatNumber(),
                ticketDto.getTicketStatus());
    }
}
