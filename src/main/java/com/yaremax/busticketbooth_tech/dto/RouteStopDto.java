package com.yaremax.busticketbooth_tech.dto;

import com.yaremax.busticketbooth_tech.data.RouteStop;
import lombok.Data;
import lombok.Value;

import java.io.Serial;
import java.io.Serializable;

/**
 * DTO for {@link RouteStop}
 */
@Data
public class RouteStopDto implements Serializable {
    Integer routeId;
    Integer stopId;
    Integer sequenceNumber;
    Integer departureOffset;
}