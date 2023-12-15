package com.yaremax.busticketbooth_tech.services;

import com.yaremax.busticketbooth_tech.data.BusStop;
import com.yaremax.busticketbooth_tech.data.Route;
import com.yaremax.busticketbooth_tech.data.RouteStop;
import com.yaremax.busticketbooth_tech.dto.RouteStopDto;
import com.yaremax.busticketbooth_tech.mappers.RouteStopDtoMapper;
import com.yaremax.busticketbooth_tech.projections.BusStopInfo;
import com.yaremax.busticketbooth_tech.projections.RouteStopInfo;
import com.yaremax.busticketbooth_tech.repositories.RouteStopRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor
public class RouteStopService {
    private final RouteStopRepository routeStopRepository;
    private final RouteStopDtoMapper routeStopDtoMapper;

    @Transactional
    public List<RouteStop> addRouteStops(List<RouteStopDto> routeStopDtos, Route route, Map<Integer, BusStop> busStopsById) {
        Set<RouteStop> routeStops = routeStopDtoMapper.toEntitySet(routeStopDtos, route, busStopsById);
        return routeStopRepository.saveAll(routeStops);
    }

    public List<RouteStopInfo> getRouteStopsByRouteId(Integer routeId) {
        return routeStopRepository.findRouteStopsByRouteId(routeId);
    }

    public List<BusStopInfo> getStopsForRoute(Integer id) {
        return routeStopRepository.findBusStopInfosByRouteId(id);
    }
}
