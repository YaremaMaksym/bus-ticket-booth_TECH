package com.yaremax.busticketbooth_tech.repositories;

import com.yaremax.busticketbooth_tech.data.Schedule;
import com.yaremax.busticketbooth_tech.data.ScheduleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

//    (b.seatCapacity - COALESCE(COUNT(t.ticket_id), 0)) as availableSeats

    @Query("SELECT " +
            "  s.id as id, " +
            "  r.name as routeName, " +
            "  (SELECT st.name" +
            "   FROM BusStop st" +
            "   JOIN RouteStop rs on rs.busStop = st" +
            "   WHERE rs.route = r " +
            "   ORDER BY rs.sequenceNumber DESC " +
            "   LIMIT 1) as finalDestinationName, " +
            "  (b.seatCapacity - " +
            "    COALESCE(" +
            "      SUM(CASE " +
            "        WHEN t.ticketStatus = 'booked' " +
            "        THEN 1 " +
            "        ELSE 0 " +
            "      END), " +
            "      0" +
            "    )" +
            "  ) as availableSeats, " +
            "  b.seatCapacity as totalSeats, " +
            "  s.departureTime as departureTime, " +
            "  s.bus as bus, " +
            "  s.route as route " +
            "FROM Schedule s " +
            "JOIN s.bus b " +
            "JOIN s.route r " +
            "LEFT JOIN Ticket t ON t.schedule.id = s.id AND t.ticketStatus = 'booked' " +
            "GROUP BY s.id, b.seatCapacity, r.name, s.departureTime, b.id, r.id")
    List<ScheduleInfo> findAllScheduleInfo();


    @Query("SELECT " +
            "  s.id as id, " +
            "  r.name as routeName, " +
            "  (SELECT st.name FROM BusStop st " +
            "   JOIN RouteStop rs ON rs.busStop = st " +
            "   WHERE rs.route = r " +
            "   ORDER BY rs.sequenceNumber DESC " +
            "   LIMIT 1) as finalDestinationName, " +
            "  (b.seatCapacity - " +
            "    COALESCE(" +
            "      SUM(CASE " +
            "        WHEN t.ticketStatus = 'booked' " +
            "        THEN 1 " +
            "        ELSE 0 " +
            "      END), " +
            "      0" +
            "    )" +
            "  ) as availableSeats, " +
            "  b.seatCapacity as totalSeats, " +
            "  s.departureTime as departureTime, " +
            "  s.bus as bus, " +
            "  s.route as route " +
            "FROM Schedule s " +
            "JOIN s.bus b " +
            "JOIN s.route r " +
            "LEFT JOIN Ticket t ON t.schedule = s AND t.ticketStatus = 'booked' " +
            "WHERE s.id = ?1 " +
            "GROUP BY s.id, b.seatCapacity, r.name, s.departureTime, b.id, r.id")
    ScheduleInfo findByIdScheduleInfo(Integer id);

}