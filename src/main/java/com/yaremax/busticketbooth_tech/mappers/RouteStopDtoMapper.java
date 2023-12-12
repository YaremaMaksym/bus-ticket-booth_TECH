package com.yaremax.busticketbooth_tech.mappers;

import com.yaremax.busticketbooth_tech.data.Route;
import com.yaremax.busticketbooth_tech.data.RouteStop;
import com.yaremax.busticketbooth_tech.data.RouteStopId;
import com.yaremax.busticketbooth_tech.dto.RouteStopDto;
import com.yaremax.busticketbooth_tech.exception.ResourceNotFoundException;
import com.yaremax.busticketbooth_tech.repositories.RouteStopRepository;
import com.yaremax.busticketbooth_tech.services.BusStopService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;

@Service
@AllArgsConstructor
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
