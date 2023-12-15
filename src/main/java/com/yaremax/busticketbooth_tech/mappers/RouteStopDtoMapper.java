package com.yaremax.busticketbooth_tech.mappers;

import com.yaremax.busticketbooth_tech.data.BusStop;
import com.yaremax.busticketbooth_tech.data.Route;
import com.yaremax.busticketbooth_tech.data.RouteStop;
import com.yaremax.busticketbooth_tech.data.RouteStopId;
import com.yaremax.busticketbooth_tech.dto.RouteStopDto;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RouteStopDtoMapper {

    public RouteStopDto toDto(RouteStop routeStop) {
        RouteStopDto dto = new RouteStopDto();
        dto.setRouteId(routeStop.getId().getRouteId());
        dto.setStopId(routeStop.getId().getStopId());
        dto.setSequenceNumber(routeStop.getSequenceNumber());
        dto.setDepartureOffset(routeStop.getDepartureOffset());
        dto.setRoute(routeStop.getRoute());
        dto.setBusStop(routeStop.getBusStop());
        return dto;
    }

    public RouteStop toEntity(RouteStopDto routeStopDto, Route route, BusStop busStop) {
        RouteStop routeStop = new RouteStop();
        routeStop.setId(new RouteStopId(route.getId(), busStop.getId()));
        routeStop.setRoute(route);
        routeStop.setBusStop(busStop);
        routeStop.setSequenceNumber(routeStopDto.getSequenceNumber());
        routeStop.setDepartureOffset(routeStopDto.getDepartureOffset());
        return routeStop;
    }

    public List<RouteStopDto> toDtoList(List<RouteStop> routeStops) {
        List<RouteStopDto> dtos = new ArrayList<>();
        for (RouteStop routeStop : routeStops) {
            dtos.add(toDto(routeStop));
        }
        return dtos;
    }

    public List<RouteStopDto> toSortedDtoList(List<RouteStop> routeStops) {
        return toDtoList(routeStops).stream()
                .sorted(Comparator.comparing(RouteStopDto::getSequenceNumber))
                .toList();
    }
}
