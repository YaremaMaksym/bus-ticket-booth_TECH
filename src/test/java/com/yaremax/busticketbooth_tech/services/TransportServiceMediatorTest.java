package com.yaremax.busticketbooth_tech.services;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yaremax.busticketbooth_tech.data.Route;
import com.yaremax.busticketbooth_tech.data.RouteStop;
import com.yaremax.busticketbooth_tech.data.Ticket;
import com.yaremax.busticketbooth_tech.projections.BusInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TransportServiceMediator.class})
@ExtendWith(SpringExtension.class)
class TransportServiceMediatorTest {
    @MockBean
    private BusService busService;

    @MockBean
    private RouteService routeService;

    @MockBean
    private ScheduleService scheduleService;

    @MockBean
    private TicketService ticketService;

    @Autowired
    private TransportServiceMediator transportServiceMediator;

    /**
     * Method under test:
     * {@link TransportServiceMediator#patchBus(Integer, String, Integer)}
     */
    @Test
    void testPatchBus() {
        // Arrange
        when(ticketService.countTicketsForScheduleByBus(Mockito.<Integer>any())).thenReturn(3);
        doNothing().when(busService)
                .patchBus(Mockito.<Integer>any(), Mockito.<String>any(), Mockito.<Integer>any(), Mockito.<Integer>any());
        int busId = 1;
        String newSerialNumber = "42";
        int newSeatCapacity = 1;

        // Act
        transportServiceMediator.patchBus(busId, newSerialNumber, newSeatCapacity);

        // Assert that nothing has changed
        verify(busService).patchBus(Mockito.<Integer>any(), Mockito.<String>any(), Mockito.<Integer>any(),
                Mockito.<Integer>any());
        verify(ticketService).countTicketsForScheduleByBus(Mockito.<Integer>any());
    }

    /**
     * Method under test:
     * {@link TransportServiceMediator#findAvailableBusInfosByTimeAndRoute(LocalDateTime, Integer)}
     */
    @Test
    void testFindAvailableBusInfosByTimeAndRoute() {
        // Arrange
        Route route = new Route();
        route.setId(1);
        route.setName("Name");
        route.setRouteStops(new HashSet<>());
        when(routeService.findById(Mockito.<Integer>any())).thenReturn(route);
        ArrayList<BusInfo> busInfoList = new ArrayList<>();
        when(busService.findAvailableBusInfosByTimeAndRoute(Mockito.<LocalDateTime>any(), Mockito.<Set<RouteStop>>any()))
                .thenReturn(busInfoList);
        LocalDateTime departureDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        int routeId = 1;

        // Act
        List<BusInfo> actualFindAvailableBusInfosByTimeAndRouteResult = transportServiceMediator
                .findAvailableBusInfosByTimeAndRoute(departureDateTime, routeId);

        // Assert
        verify(busService).findAvailableBusInfosByTimeAndRoute(Mockito.<LocalDateTime>any(), Mockito.<Set<RouteStop>>any());
        verify(routeService).findById(Mockito.<Integer>any());
        assertTrue(actualFindAvailableBusInfosByTimeAndRouteResult.isEmpty());
        assertSame(busInfoList, actualFindAvailableBusInfosByTimeAndRouteResult);
    }

    /**
     * Method under test:
     * {@link TransportServiceMediator#getAvailableSeats(Integer)}
     */
    @Test
    void testGetAvailableSeats() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        when(scheduleService.getAvailableSeats(Mockito.<Integer>any(), Mockito.<List<Ticket>>any()))
                .thenReturn(integerList);
        when(ticketService.findAllByScheduleAndTicketStatus(Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        int scheduleId = 1;

        // Act
        List<Integer> actualAvailableSeats = transportServiceMediator.getAvailableSeats(scheduleId);

        // Assert
        verify(scheduleService).getAvailableSeats(Mockito.<Integer>any(), Mockito.<List<Ticket>>any());
        verify(ticketService).findAllByScheduleAndTicketStatus(Mockito.<Integer>any(), Mockito.<String>any());
        assertTrue(actualAvailableSeats.isEmpty());
        assertSame(integerList, actualAvailableSeats);
    }
}
