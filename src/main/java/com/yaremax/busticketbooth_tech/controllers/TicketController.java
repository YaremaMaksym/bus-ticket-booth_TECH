package com.yaremax.busticketbooth_tech.controllers;

import com.yaremax.busticketbooth_tech.dto.TicketDto;
import com.yaremax.busticketbooth_tech.services.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tickets")
@AllArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    // TODO: чи не треба перемістити це в інший контроллер?
    @PostMapping
    public String addTicket(@ModelAttribute TicketDto ticketDto,
                            @RequestParam("returnUrl") String returnUrl) {
        ticketService.addTicket(ticketDto);
        return "redirect:" + returnUrl;
    }
}
