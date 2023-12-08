package com.yaremax.busticketbooth_tech.dto;

import com.yaremax.busticketbooth_tech.data.Route;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link Route}
 */
@Data
public class RouteDto implements Serializable {
    Integer id;
    String name;
    List<RouteStopDto> routeStops;
}