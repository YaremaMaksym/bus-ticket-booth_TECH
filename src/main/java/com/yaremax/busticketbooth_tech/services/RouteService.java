package com.yaremax.busticketbooth_tech.services;

import com.yaremax.busticketbooth_tech.data.BusStop;
import com.yaremax.busticketbooth_tech.data.Route;
import com.yaremax.busticketbooth_tech.data.RouteStop;
import com.yaremax.busticketbooth_tech.dto.RouteDto;
import com.yaremax.busticketbooth_tech.exception.ResourceNotFoundException;
import com.yaremax.busticketbooth_tech.mappers.RouteStopDtoMapper;
import com.yaremax.busticketbooth_tech.repositories.RouteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RouteService {
    private final RouteStopDtoMapper routeStopDtoMapper;
    private final ReferenceService referenceService;
    private final RouteRepository routeRepository;

    public List<Route> findAll() {
        return routeRepository.findAll();
    }

    public Route findById(Integer id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Route with id " + id + " wasn't found"));
    }

    @Transactional
    public void addRoute(RouteDto routeDto) {
        Route route = new Route(routeDto.getName());
        routeRepository.save(route);

        Set<RouteStop> routeStops = routeDto.getRouteStops().stream()
                .map(routeStopDto -> {
                    BusStop busStop = referenceService.getBusStopReferenceById(routeStopDto.getStopId());
                    return routeStopDtoMapper.toEntity(routeStopDto, route, busStop);
                })
                .collect(Collectors.toSet());

        route.setRouteStops(routeStops);

        routeRepository.save(route);
    }

    @Transactional
    public void deleteRoute(Integer id) {
        Route route = findById(id);
        routeRepository.delete(route);
    }
}
