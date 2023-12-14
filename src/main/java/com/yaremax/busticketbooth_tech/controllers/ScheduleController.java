package com.yaremax.busticketbooth_tech.controllers;

import com.yaremax.busticketbooth_tech.data.*;
import com.yaremax.busticketbooth_tech.services.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/schedules")
@AllArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final TicketService ticketService;
    private final BusStopService busStopService;
    private final RouteService routeService;

    @GetMapping
    public String showAllSchedules(Model model) {
        model.addAttribute("schedules", scheduleService.findAllPlannedScheduleInfo());
        model.addAttribute("stops", busStopService.findAll());
        model.addAttribute("routes", routeService.findAll());
        return "schedules";
    }

    @GetMapping("/search")
    public String showBestSchedulesToStop(Model model,
                                         @RequestParam Integer busStopId) {
        model.addAttribute("schedules", scheduleService.findBestSchedulesToStop(busStopId).orElse(Collections.emptyList()));
        model.addAttribute("stops", busStopService.findAll());
        model.addAttribute("routes", routeService.findAll());
        return "schedules";
    }

    // TODO: зробити dto
    @PostMapping
    public String addSchedule(@ModelAttribute Schedule schedule) {
        scheduleService.addSchedule(schedule);
        return "redirect:/schedules";
    }

    @DeleteMapping("/{id}")
    public String deleteSchedule(@PathVariable Integer id) {
        scheduleService.deleteSchedule(id);
        return "redirect:/schedules";
    }

    @GetMapping("/{scheduleId}/boarding-manifest")
    public String getBoardingManifestForSchedule(@PathVariable Integer scheduleId, Model model) {
        model.addAttribute("tickets", ticketService.findAllByScheduleAndTicketStatus(scheduleId, "booked"));
        model.addAttribute("schedule", scheduleService.findByIdScheduleInfo(scheduleId));
        model.addAttribute("routeStops", scheduleService.getRouteStopDtosWithArrivalTime(scheduleId));

        return "boarding_manifest";
    }

    @ResponseBody
    @GetMapping("/{id}/available-seats")
    public ResponseEntity<List<Integer>> getAvailableSeats(@PathVariable Integer id) {
        return ResponseEntity.ok(scheduleService.getAvailableSeats(id));
    }

}
