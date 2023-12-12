package com.yaremax.busticketbooth_tech.repositories;

import com.yaremax.busticketbooth_tech.data.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByScheduleIdAndTicketStatus(Integer id, String status);

    @Query("SELECT COUNT(t) " +
            "FROM Ticket t " +
            "WHERE t.schedule.bus.id = :busId AND " +
            "t.schedule.departureDateTime > CURRENT_TIMESTAMP")
    Integer countTicketsForScheduleByBusSerialNumber(Integer busId);
}