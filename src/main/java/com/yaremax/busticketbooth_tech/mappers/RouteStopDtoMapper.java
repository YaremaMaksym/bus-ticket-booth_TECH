package com.yaremax.busticketbooth_tech.mappers;

import com.yaremax.busticketbooth_tech.data.Route;
import com.yaremax.busticketbooth_tech.data.RouteStop;
import com.yaremax.busticketbooth_tech.data.RouteStopId;
import com.yaremax.busticketbooth_tech.dto.RouteStopDto;
import com.yaremax.busticketbooth_tech.exception.ResourceNotFoundException;
import com.yaremax.busticketbooth_tech.services.BusStopService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class RouteStopDtoMapper {
    private final BusStopService busStopService;

    public LinkedHashSet<RouteStop> toRouteStopSet(List<RouteStopDto> routeStopDtos, Route route) {

        LinkedHashSet<RouteStop> routeStops = new LinkedHashSet<>();

        for (RouteStopDto routeStopDto : routeStopDtos) {
            RouteStop routeStop = new RouteStop();

            routeStop.setId(new RouteStopId(route.getId(), routeStopDto.getStopId()));
            routeStop.setRoute(route);
            routeStop.setBusStop(busStopService.findById(routeStopDto.getStopId())
                    .orElseThrow(() -> new ResourceNotFoundException("Bus stop with id " + routeStopDto.getStopId() + " wasn't found"))
            );

            routeStop.setSequenceNumber(routeStopDto.getSequenceNumber());
            routeStop.setDepartureOffset(routeStopDto.getDepartureOffset());

        }

        return routeStops;
    }
}
