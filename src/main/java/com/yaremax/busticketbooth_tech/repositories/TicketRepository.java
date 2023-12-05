package com.yaremax.busticketbooth_tech.repositories;

import com.yaremax.busticketbooth_tech.data.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}