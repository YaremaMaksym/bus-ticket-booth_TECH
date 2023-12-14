package com.yaremax.busticketbooth_tech.services;

import com.yaremax.busticketbooth_tech.data.*;
import com.yaremax.busticketbooth_tech.dto.RouteDto;
import com.yaremax.busticketbooth_tech.dto.RouteStopDto;
import com.yaremax.busticketbooth_tech.mappers.RouteDtoMapper;
import com.yaremax.busticketbooth_tech.mappers.RouteStopDtoMapper;
import com.yaremax.busticketbooth_tech.projections.BusStopInfo;
import com.yaremax.busticketbooth_tech.projections.RouteStopInfo;
import com.yaremax.busticketbooth_tech.repositories.BusStopRepository;
import com.yaremax.busticketbooth_tech.repositories.RouteRepository;
import com.yaremax.busticketbooth_tech.exception.ResourceNotFoundException;
import com.yaremax.busticketbooth_tech.repositories.RouteStopRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class RouteService {
    private final RouteRepository routeRepository;
    private final RouteDtoMapper routeDtoMapper;
    private final RouteStopDtoMapper routeStopDtoMapper;

    public List<Route> findAll() {
        return routeRepository.findAll();
    }

    public Route findById(Integer id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Route with id " + id + " wasn't found"));
    }

    @Transactional
    public void addRoute(RouteDto routeDto) {
        Route route = routeDtoMapper.toEntity(routeDto);
        route = routeRepository.save(route);

        route.setRouteStops(routeStopDtoMapper.toEntitySet(routeDto.getRouteStops(), route.getId()));

        routeRepository.save(route);
    }

    @Transactional
    public void deleteRoute(Integer id) {
        Route route = findById(id);
        routeRepository.delete(route);
    }
}
