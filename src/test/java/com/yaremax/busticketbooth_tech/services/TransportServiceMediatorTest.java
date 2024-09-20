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

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
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

    @Nested
    class PatchBusTests {

        @Test
        void patchBus_shouldUpdateBus_whenBusExistsAndSerialNumberIsValid() {
            // Arrange
            when(ticketService.countTicketsForScheduleByBus(Mockito.<Integer>any())).thenReturn(3);
            doNothing().when(busService)
                    .patchBus(Mockito.<Integer>any(), Mockito.<String>any(), Mockito.<Integer>any(), Mockito.<Integer>any());
            int busId = 1;
            String newSerialNumber = "42";
            int seatCapacity = 10;

            // Act
            transportServiceMediator.patchBus(busId, newSerialNumber, seatCapacity);

            // Assert
            verify(busService).patchBus(busId, newSerialNumber, seatCapacity, 3);
        }
    }

    @Nested
    class FindAvailableBusInfosByTimeAndRouteTests {

        @Test
        void findAvailableBusInfosByTimeAndRoute_shouldReturnEmptyList_whenNoBusesAvailable() {
            // Arrange
            Route route = new Route();
            route.setId(1);
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
    }

    @Nested
    class GetAvailableSeatsTests {

        @Test
        void getAvailableSeats_shouldReturnEmptyList_whenNoAvailableSeats() {
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

}
