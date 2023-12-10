package com.yaremax.busticketbooth_tech.controllers;

import com.yaremax.busticketbooth_tech.services.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/refunds")
@AllArgsConstructor
public class RefundController {
    private final TicketService ticketService;

    @GetMapping("/schedule/{scheduleId}/tickets")
    public String showTicketRefundPage(Model model,
                                       @PathVariable Integer scheduleId) {
        model.addAttribute("tickets", ticketService.findAllByScheduleAndTicketStatus(scheduleId, "booked"));
        return "ticket_refund";
    }

    @PostMapping("/schedule/{scheduleId}/tickets/{ticketId}")
    public String refundTicket(@PathVariable Integer scheduleId,
                               @PathVariable Integer ticketId) {
        ticketService.refundTicket(ticketId);

        return "redirect:/refunds/schedule/" + scheduleId + "/tickets";
    }
}
