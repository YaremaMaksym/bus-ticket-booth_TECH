package com.yaremax.busticketbooth_tech.services;

import com.yaremax.busticketbooth_tech.data.Ticket;
import com.yaremax.busticketbooth_tech.dto.TicketDto;
import com.yaremax.busticketbooth_tech.exception.ResourceNotFoundException;
import com.yaremax.busticketbooth_tech.mappers.TicketMapper;
import com.yaremax.busticketbooth_tech.projections.TicketInfo;
import com.yaremax.busticketbooth_tech.repositories.TicketRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public Ticket addTicket(TicketDto ticketDto) {
        Ticket ticket = ticketMapper.toEntity(ticketDto);
        return ticketRepository.save(ticket);
    }

    @Transactional
    public void refundTicket(Integer ticketId) {
        Ticket ticket = findById(ticketId);
        ticket.setTicketStatus("refunded");
        ticketRepository.saveAndFlush(ticket);
    }

    public Integer countTicketsForScheduleByBusSerialNumber(Integer busId) {
        return ticketRepository.countTicketsForScheduleByBusSerialNumber(busId);
    }

    public List<TicketInfo> findAllTicketInfos() {
        return ticketRepository.findAllTicketInfos();
    }

    public TicketInfo findByIdTicketInfo(Integer ticketId) {
        return ticketRepository.findByIdTicketInfo(ticketId);
    }
}
