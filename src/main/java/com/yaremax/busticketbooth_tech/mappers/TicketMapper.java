package com.yaremax.busticketbooth_tech.mappers;

import com.yaremax.busticketbooth_tech.data.Ticket;
import com.yaremax.busticketbooth_tech.data.Schedule;
import com.yaremax.busticketbooth_tech.data.BusStop;
import com.yaremax.busticketbooth_tech.dto.TicketDto;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {
    public Ticket toEntity(TicketDto ticketDto, Schedule schedule, BusStop busStop) {
        return new Ticket(schedule,
                busStop,
                ticketDto.getSeatNumber(),
                ticketDto.getTicketStatus());
    }
}