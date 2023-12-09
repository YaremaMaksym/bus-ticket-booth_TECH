package com.yaremax.busticketbooth_tech.controllers;

import com.yaremax.busticketbooth_tech.data.Schedule;
import com.yaremax.busticketbooth_tech.services.BusService;
import com.yaremax.busticketbooth_tech.services.RouteService;
import com.yaremax.busticketbooth_tech.services.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/schedules")
@AllArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final BusService busService;
    private final RouteService routeService;

    @GetMapping
    public String getSchedules(Model model) {
        model.addAttribute("schedulesInfo", scheduleService.findAllScheduleInfo());
        model.addAttribute("busses", busService.findAll());
        model.addAttribute("routes", routeService.findAll());
        return "schedules";
    }

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

//    @PatchMapping("/{id}")
//    public String updateSchedule(@PathVariable Integer id,
//                                 @ModelAttribute Schedule scheduleDetails) {
//        scheduleService.updateSchedule(id, scheduleDetails);
//        return "redirect:/schedules";
//    }
}