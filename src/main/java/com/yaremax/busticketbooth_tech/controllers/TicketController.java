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

    @GetMapping("/{ticketId}")
    public String showTicket(Model model,
                             @PathVariable Integer ticketId) {
        model.addAttribute("ticket", ticketService.findById(ticketId));
        return "ticket_info";
    }

    @PostMapping
    public String addTicket(@ModelAttribute TicketDto ticketDto) {
        Ticket ticket = ticketService.addTicket(ticketDto);
        return "redirect:tickets/" + ticket.getId();
    }
}
