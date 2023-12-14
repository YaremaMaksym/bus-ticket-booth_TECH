package com.yaremax.busticketbooth_tech.services;

import com.yaremax.busticketbooth_tech.projections.BusStopInfo;
import com.yaremax.busticketbooth_tech.projections.RouteStopInfo;
import com.yaremax.busticketbooth_tech.repositories.RouteStopRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RouteStopService {
    private final RouteStopRepository routeStopRepository;

    public List<RouteStopInfo> getRouteStopsByRouteId(Integer routeId) {
        return routeStopRepository.findRouteStopsByRouteId(routeId);
    }

    public List<BusStopInfo> getStopsForRoute(Integer id) {
        return routeStopRepository.findBusStopInfosByRouteId(id);
    }
}
