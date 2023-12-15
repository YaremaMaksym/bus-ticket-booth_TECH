package com.yaremax.busticketbooth_tech.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.yaremax.busticketbooth_tech.data.BusStop;
import com.yaremax.busticketbooth_tech.data.Route;
import com.yaremax.busticketbooth_tech.data.RouteStop;
import com.yaremax.busticketbooth_tech.data.Schedule;
import com.yaremax.busticketbooth_tech.data.Ticket;
import com.yaremax.busticketbooth_tech.dto.RouteDto;
import com.yaremax.busticketbooth_tech.dto.RouteStopDto;
import com.yaremax.busticketbooth_tech.dto.TicketDto;
import com.yaremax.busticketbooth_tech.projections.BusInfo;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransportServiceMediator {
    private final RouteStopService routeStopService;
    private final ScheduleService scheduleService;
    private final BusStopService busStopService;
    private final TicketService ticketService;
    private final RouteService routeService;
    private final BusService busService;

    public Ticket addTicket(TicketDto ticketDto) {
        Schedule schedule = scheduleService.findById(ticketDto.getScheduleId());
        BusStop busStop = busStopService.findById(ticketDto.getBusStopId());
        return ticketService.addTicket(ticketDto, schedule, busStop);
    }

    public void addRoute(RouteDto routeDto) {
        Route route = routeService.addRoute(routeDto);
        addRouteStops(routeDto.getRouteStops(), route.getId());
    }

    public List<RouteStop> addRouteStops(List<RouteStopDto> routeStopDtos, Integer routeId) {
        Route route = routeService.findById(routeId);
        Map<Integer, BusStop> busStopsById = busStopService.findByIds(routeStopDtos.stream()
            .map(RouteStopDto::getStopId)
            .collect(Collectors.toList()));
        return routeStopService.addRouteStops(routeStopDtos, route, busStopsById);
    }

    public void patchBus(Integer busId, String newSerialNumber, Integer newSeatCapacity) {
        int ticketsSold = ticketService.countTicketsForScheduleByBus(busId);
        busService.patchBus(busId, newSerialNumber, newSeatCapacity, ticketsSold);
    }

    public List<BusInfo> findAvailableBusInfosByTimeAndRoute(LocalDateTime departureDateTime, Integer routeId) {
        Route route = routeService.findById(routeId);

        return busService.findAvailableBusInfosByTimeAndRoute(departureDateTime, route);
    }

    public List<Integer> getAvailableSeats(Integer scheduleId) {
        List<Ticket> bookedTickets = ticketService.findAllByScheduleAndTicketStatus(scheduleId, "booked");
        return scheduleService.getAvailableSeats(scheduleId, bookedTickets);
    }
}
