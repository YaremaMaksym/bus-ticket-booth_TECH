package com.yaremax.busticketbooth_tech.mappers;

import com.yaremax.busticketbooth_tech.data.RouteStop;
import com.yaremax.busticketbooth_tech.data.RouteStopId;
import com.yaremax.busticketbooth_tech.dto.RouteStopDto;
import com.yaremax.busticketbooth_tech.exception.ResourceNotFoundException;
import com.yaremax.busticketbooth_tech.repositories.BusStopRepository;
import com.yaremax.busticketbooth_tech.repositories.RouteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class RouteStopDtoMapper {
    private final RouteRepository routeRepository;
    private final BusStopRepository busStopRepository;

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

    public RouteStop toEntity(RouteStopDto routeStopDto, Integer routeId) {
        Integer busStopId = routeStopDto.getStopId();

        RouteStop routeStop = new RouteStop();
        routeStop.setId(new RouteStopId(routeId, busStopId));
        routeStop.setRoute(routeRepository.findById(routeId)
                .orElseThrow(() -> new ResourceNotFoundException("Route with id " + routeId + " wasn't found")));
        routeStop.setBusStop(busStopRepository.findById(busStopId)
                .orElseThrow(() -> new ResourceNotFoundException("Bus stop with id " + busStopId + " wasn't found")));
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

    public Set<RouteStop> toEntitySet(List<RouteStopDto> routeStopDtos, Integer routeId) {
        Set<RouteStop> set = new LinkedHashSet<>();
        for (RouteStopDto routeStopDto : routeStopDtos) {
            set.add(toEntity(routeStopDto, routeId));
        }
        return set;
    }

    public List<RouteStopDto> toSortedDtoList(List<RouteStop> routeStops) {
        return toDtoList(routeStops).stream()
                .sorted(Comparator.comparing(RouteStopDto::getSequenceNumber))
                .toList();
    }
}
