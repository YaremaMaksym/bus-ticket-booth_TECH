package com.yaremax.busticketbooth_tech.repositories;

import com.yaremax.busticketbooth_tech.data.Bus;
import com.yaremax.busticketbooth_tech.projections.BusInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BusRepository extends JpaRepository<Bus, Integer> {
    boolean existsBySerialNumber(String serialNumber);

    @Query(value = "" +
            "SELECT b.bus_id as id, " +
            "b.bus_serial_number as serialNumber, " +
            "b.seat_capacity as seatCapacity " +
            "FROM buses b " +
            "WHERE NOT EXISTS (" +
            "    SELECT 1 " +
            "    FROM schedule s " +
            "    JOIN routes_stops rs ON s.route_id = rs.route_id " +
            "    WHERE s.bus_id = b.bus_id " +
            "    AND (" +
            "        :departureDatetime BETWEEN s.departure_datetime AND " +
            "        (s.departure_datetime + INTERVAL '1 minute' * (SELECT SUM(rs.departure_minutes_offset)" +
            "                                                     FROM routes_stops rs" +
            "                                                     WHERE rs.route_id = s.route_id)) " +
            "        OR :endRouteDatetime BETWEEN s.departure_datetime AND " +
            "        (s.departure_datetime + INTERVAL '1 minute' * (SELECT SUM(rs.departure_minutes_offset)" +
            "                                                     FROM routes_stops rs" +
            "                                                     WHERE rs.route_id = s.route_id)) " +
            "        OR s.departure_datetime BETWEEN :departureDatetime AND :endRouteDatetime " +
            "        OR (s.departure_datetime + INTERVAL '1 minute' * (SELECT SUM(rs.departure_minutes_offset)" +
            "                                                        FROM routes_stops rs" +
            "                                                        WHERE rs.route_id = s.route_id)) BETWEEN :departureDatetime AND :endRouteDatetime " +
            "    )" +
            ") ORDER BY b.bus_id", nativeQuery = true)
    List<BusInfo> findAvailableBusInfosByTime(@Param("departureDatetime") LocalDateTime departureDatetime,
                                              @Param("endRouteDatetime") LocalDateTime endRouteDatetime);


}