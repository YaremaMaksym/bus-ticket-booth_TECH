package com.yaremax.busticketbooth_tech.controllers;

import com.yaremax.busticketbooth_tech.data.Ticket;
import com.yaremax.busticketbooth_tech.dto.TicketDto;
import com.yaremax.busticketbooth_tech.services.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tickets")
@AllArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping
    public String showTicketsPage(Model model) {
        model.addAttribute("tickets", ticketService.findAllTicketInfos());
        return "tickets";
    }

    @GetMapping("/{ticketId}")
    public String showTicketPage(Model model,
                             @PathVariable Integer ticketId) {
        model.addAttribute("ticket", ticketService.findByIdTicketInfo(ticketId));
        return "ticket_info";
    }

    @PostMapping
    public String addTicket(@ModelAttribute TicketDto ticketDto) {
        Ticket ticket = ticketService.addTicket(ticketDto);
        return "redirect:tickets/" + ticket.getId();
    }

    @GetMapping("/schedule/{scheduleId}")
    public String showTicketRefundPage(Model model,
                                       @PathVariable Integer scheduleId) {
        model.addAttribute("tickets", ticketService.findAllByScheduleAndTicketStatus(scheduleId, "booked"));
        return "ticket_refund";
    }

    @PostMapping("/{ticketId}/refund")
    public String refundTicket(@PathVariable Integer ticketId) {
        ticketService.refundTicket(ticketId);
        return "redirect:/tickets";
    }
}
