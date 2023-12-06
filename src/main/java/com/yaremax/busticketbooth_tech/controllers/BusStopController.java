package com.yaremax.busticketbooth_tech.controllers;

import com.yaremax.busticketbooth_tech.data.BusStop;
import com.yaremax.busticketbooth_tech.services.BusStopService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bus_stops")
@AllArgsConstructor
public class BusStopController {
    private final BusStopService busStopService;

    @GetMapping
    public String getBusStops(Model model) {
        model.addAttribute("bus_stops", busStopService.findAll());
        return "bus_stops";
    }

    @PostMapping
    public String addBusStop(@RequestParam String name) {
        busStopService.addBusStop(new BusStop(name));
        return "redirect:/bus_stops";
    }

    @DeleteMapping("/{id}")
    public String deleteBusStop(@PathVariable Integer id) {
        busStopService.deleteBusStop(id);
        return "redirect:/bus_stops";
    }

    @PatchMapping("/{id}")
    public String patchBusStop(@PathVariable Integer id,
                                @RequestParam String name) {
        busStopService.patchBusStop(id, name);
        return "redirect:/bus_stops";
    }
}
