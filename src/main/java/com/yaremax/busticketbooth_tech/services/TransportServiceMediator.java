package com.yaremax.busticketbooth_tech.services;

import com.yaremax.busticketbooth_tech.data.RouteStop;
import com.yaremax.busticketbooth_tech.data.Ticket;
import com.yaremax.busticketbooth_tech.projections.BusInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class TransportServiceMediator {
    private final ScheduleService scheduleService;
    private final TicketService ticketService;
    private final RouteService routeService;
    private final BusService busService;

    public void patchBus(Integer busId, String newSerialNumber, Integer newSeatCapacity) {
        int ticketsSold = ticketService.countTicketsForScheduleByBus(busId);
        busService.patchBus(busId, newSerialNumber, newSeatCapacity, ticketsSold);
    }

    public List<BusInfo> findAvailableBusInfosByTimeAndRoute(LocalDateTime departureDateTime, Integer routeId) {
        Set<RouteStop> routeStops = routeService.findById(routeId).getRouteStops();
        return busService.findAvailableBusInfosByTimeAndRoute(departureDateTime, routeStops);
    }

    public List<Integer> getAvailableSeats(Integer scheduleId) {
        List<Ticket> bookedTickets = ticketService.findAllByScheduleAndTicketStatus(scheduleId, "booked");
        return scheduleService.getAvailableSeats(scheduleId, bookedTickets);
    }
}
