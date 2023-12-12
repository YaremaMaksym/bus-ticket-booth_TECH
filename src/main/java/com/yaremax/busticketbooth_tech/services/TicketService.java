package com.yaremax.busticketbooth_tech.services;

import com.yaremax.busticketbooth_tech.data.Ticket;
import com.yaremax.busticketbooth_tech.dto.TicketDto;
import com.yaremax.busticketbooth_tech.exception.ResourceNotFoundException;
import com.yaremax.busticketbooth_tech.mappers.TicketMapper;
import com.yaremax.busticketbooth_tech.repositories.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    public Ticket findById(Integer ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket with id " + ticketId + " wasn't found"));
    }

    public List<Ticket> findAllByScheduleAndTicketStatus(Integer scheduleId, String status) {
        return ticketRepository.findByScheduleIdAndTicketStatus(scheduleId, status);
    }

    public Ticket addTicket(TicketDto ticketDto) {
        Ticket ticket = ticketMapper.toEntity(ticketDto);
        return ticketRepository.save(ticket);
    }

    public void refundTicket(Integer ticketId) {
        Ticket ticket = findById(ticketId);
        ticket.setTicketStatus("refunded");
        ticketRepository.saveAndFlush(ticket);
    }
}
