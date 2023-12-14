package com.yaremax.busticketbooth_tech.controllers;

import com.yaremax.busticketbooth_tech.projections.BusStopInfo;
import com.yaremax.busticketbooth_tech.projections.RouteStopInfo;
import com.yaremax.busticketbooth_tech.dto.RouteDto;
import com.yaremax.busticketbooth_tech.services.BusStopService;
import com.yaremax.busticketbooth_tech.services.RouteService;
import com.yaremax.busticketbooth_tech.services.RouteStopService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/routes")
@AllArgsConstructor
public class RouteController {
    private final RouteStopService routeStopService;
    private final RouteService routeService;
    private final BusStopService busStopService;

    @GetMapping
    public String getRoutes(Model model) {
        model.addAttribute("routes", routeService.findAll());
        model.addAttribute("bus_stops", busStopService.findAll());
        return "routes";
    }

    @PostMapping
    public String addRoute(@ModelAttribute RouteDto routeDto) {
        routeService.addRoute(routeDto);
        return "redirect:/routes";
    }

    @DeleteMapping("/{id}")
    public String deleteRoute(@PathVariable Integer id) {
        routeService.deleteRoute(id);
        return "redirect:/routes";
    }

    @ResponseBody
    @GetMapping("/{id}/stops")
    public ResponseEntity<List<BusStopInfo>> getStopsForRoute(@PathVariable Integer id) {
        return ResponseEntity.ok(routeStopService.getStopsForRoute(id));
    }

    @ResponseBody
    @GetMapping("/{routeId}/route-stops")
    public ResponseEntity<List<RouteStopInfo>> getRouteStopsByRouteId(@PathVariable Integer routeId) {
        return ResponseEntity.ok(routeStopService.getRouteStopsByRouteId(routeId));
    }
}
