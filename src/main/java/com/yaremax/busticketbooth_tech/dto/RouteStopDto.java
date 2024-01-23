package com.yaremax.busticketbooth_tech.dto;

import com.yaremax.busticketbooth_tech.data.BusStop;
import com.yaremax.busticketbooth_tech.data.Route;
import com.yaremax.busticketbooth_tech.data.RouteStop;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link RouteStop}
 */
@Data
public class RouteStopDto implements Serializable {
    Integer routeId;
    Integer stopId;
    Integer sequenceNumber;
    Integer departureMinutesOffset;

    Route route;
    BusStop busStop;
    LocalDateTime arrivalDateTime;
}