package com.yaremax.busticketbooth_tech.services;

import com.yaremax.busticketbooth_tech.data.*;
import com.yaremax.busticketbooth_tech.dto.RouteDto;
import com.yaremax.busticketbooth_tech.dto.RouteStopDto;
import com.yaremax.busticketbooth_tech.mappers.RouteDtoMapper;
import com.yaremax.busticketbooth_tech.repositories.RouteRepository;
import com.yaremax.busticketbooth_tech.exception.ResourceNotFoundException;
import com.yaremax.busticketbooth_tech.repositories.RouteStopRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RouteService {
    private final BusStopService busStopService;
    private final RouteRepository routeRepository;
    private final RouteStopRepository routeStopRepository;
    private final RouteDtoMapper routeDtoMapper;

    public List<Route> findAll() {
        return routeRepository.findAll();
    }

    public Optional<Route> findById(Integer id) {
        return routeRepository.findById(id);
    }

    @Transactional
    public void addRoute(RouteDto routeDto) {
        Route route = routeDtoMapper.toEntity(routeDto);
        route = routeRepository.save(route);

        for (RouteStopDto routeStopDto : routeDto.getRouteStops()) {
            // TODO: винести в окремий метод (маппер)
            RouteStop routeStop = new RouteStop();
            routeStop.setId(new RouteStopId(route.getId(), routeStopDto.getStopId()));
            routeStop.setRoute(route);
            routeStop.setBusStop(busStopService.findById(routeStopDto.getStopId())
                    .orElseThrow(() -> new ResourceNotFoundException("Bus stop with id " + routeStopDto.getStopId() + " wasn't found")
            ));
            routeStop.setSequenceNumber(routeStopDto.getSequenceNumber());
            routeStop.setDepartureOffset(routeStopDto.getDepartureOffset());

            route.getRouteStops().add(routeStop);
        }

        routeRepository.save(route);
    }

    @Transactional
    public void deleteRoute(Integer id) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Route with id " + id + " wasn't found"));
        routeRepository.delete(route);
    }

    public List<BusStopInfo> getStopsForRoute(Integer id) {
        return routeStopRepository.findBusStopInfosByRouteId(id);
    }

    // TODO: додати update для route
//    @Transactional
//    public void updateRoute(Integer routeId, RouteDto routeDto) {
//        Route existingRoute = routeRepository.findById(routeId)
//                .orElseThrow(() -> new ResourceNotFoundException("Route with id " + routeId + " wasn't found"));
//
//        existingRoute.setName(routeDto.getName());
//        // TODO: Видалення існуючих зупинок може бути необхідно
//
//        existingRoute.setRouteStops(routeDtoMapper.toEntity(routeDto).getRouteStops());
//
//        routeRepository.save(existingRoute);
//    }

}
