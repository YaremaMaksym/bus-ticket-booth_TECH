package com.yaremax.busticketbooth_tech.controllers;

import com.yaremax.busticketbooth_tech.dto.RouteDto;
import com.yaremax.busticketbooth_tech.services.BusStopService;
import com.yaremax.busticketbooth_tech.services.RouteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/routes")
@AllArgsConstructor
public class RouteController {
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

//    @PutMapping("/{id}")
//    public String updateRoute(@PathVariable Integer id, @ModelAttribute RouteDto routeDto) {
//        routeService.updateRoute(id, routeDto);
//        return "redirect:/routes";
//    }

}
