package com.yaremax.busticketbooth_tech.repositories;

import com.yaremax.busticketbooth_tech.data.BusStopInfo;
import com.yaremax.busticketbooth_tech.data.RouteStop;
import com.yaremax.busticketbooth_tech.data.RouteStopId;
import com.yaremax.busticketbooth_tech.data.RouteStopInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RouteStopRepository extends JpaRepository<RouteStop, RouteStopId> {

    @Query("SELECT rs.busStop.id as id, " +
            " rs.busStop.name as name " +
            "FROM RouteStop rs " +
            "WHERE rs.route.id = :routeId " +
            "ORDER BY rs.sequenceNumber")
    List<BusStopInfo> findBusStopInfosByRouteId(@Param("routeId") Integer routeId);

    @Query("SELECT rs " +
            "FROM RouteStop rs " +
            "WHERE rs.route.id = :routeId " +
            "ORDER BY rs.sequenceNumber")
    List<RouteStopInfo> findRouteStopsByRouteId(@Param("routeId") Integer routeId);
}