package com.yaremax.busticketbooth_tech.repositories;

import com.yaremax.busticketbooth_tech.data.Ticket;
import com.yaremax.busticketbooth_tech.projections.TicketInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    @Query("select " +
            "t.id as id, " +
            "t.seatNumber as seatNumber, " +
            "t.ticketStatus as ticketStatus, " +
            "t.schedule as schedule, " +
            "t.busStop as busStop " +
            "from Ticket t " +
            "join t.schedule s " +
            "join t.busStop bs ")
    List<TicketInfo> findAllTicketInfos();

    @Query("select " +
            "t.id as id, " +
            "t.seatNumber as seatNumber, " +
            "t.ticketStatus as ticketStatus, " +
            "t.schedule as schedule, " +
            "t.busStop as busStop " +
            "from Ticket t " +
            "join t.schedule s " +
            "join t.busStop bs " +
            "where t.id = :ticketId")
    TicketInfo findByIdTicketInfo(Integer ticketId);

    List<Ticket> findByScheduleIdAndTicketStatus(Integer id, String status);

    @Query("SELECT COUNT(t) " +
            "FROM Ticket t " +
            "WHERE t.schedule.bus.id = :busId AND " +
            "t.schedule.departureDateTime > CURRENT_TIMESTAMP")
    Integer countTicketsForScheduleByBus(Integer busId);
}