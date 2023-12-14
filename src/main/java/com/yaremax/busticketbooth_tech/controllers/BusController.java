package com.yaremax.busticketbooth_tech.controllers;

import com.yaremax.busticketbooth_tech.data.Bus;
import com.yaremax.busticketbooth_tech.projections.BusInfo;
import com.yaremax.busticketbooth_tech.services.BusService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/buses")
@AllArgsConstructor
public class BusController {
    private final BusService busService;

    @GetMapping
    public String getBuses(Model model) {
        model.addAttribute("buses", busService.findAll());
        return "buses";
    }

    @PostMapping
    public String addBus(@RequestParam String serialNumber,
                         @RequestParam Integer seatCapacity) {
        busService.addBus(new Bus(serialNumber, seatCapacity));
        return "redirect:/buses";
    }

    @DeleteMapping("/{id}")
    public String deleteBus(@PathVariable Integer id) {
        busService.deleteBus(id);
        return "redirect:/buses";
    }

    @PatchMapping("/{id}")
    public String patchBus(@PathVariable Integer id,
                           @RequestParam String newSerialNumber,
                           @RequestParam Integer newSeatCapacity) {
        busService.patchBus(id, newSerialNumber, newSeatCapacity);
        return "redirect:/buses";
    }

    @ResponseBody
    @GetMapping("/find-available")
    public ResponseEntity<List<BusInfo>> getAvailableBusInfos(@RequestParam LocalDateTime inputDateTime,
                                                              @RequestParam Integer routeId) {
        return ResponseEntity.ok(busService.findAvailableBusInfosByTimeAndRoute(inputDateTime, routeId));
    }
}
