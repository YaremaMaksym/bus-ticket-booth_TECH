package com.yaremax.busticketbooth_tech.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yaremax.busticketbooth_tech.data.Bus;
import com.yaremax.busticketbooth_tech.data.BusStop;
import com.yaremax.busticketbooth_tech.data.Route;
import com.yaremax.busticketbooth_tech.data.RouteStop;
import com.yaremax.busticketbooth_tech.data.RouteStopId;
import com.yaremax.busticketbooth_tech.mappers.RouteStopDtoMapper;
import com.yaremax.busticketbooth_tech.mappers.TicketMapper;
import com.yaremax.busticketbooth_tech.projections.BusInfo;
import com.yaremax.busticketbooth_tech.repositories.BusRepository;
import com.yaremax.busticketbooth_tech.repositories.BusStopRepository;
import com.yaremax.busticketbooth_tech.repositories.RouteRepository;
import com.yaremax.busticketbooth_tech.repositories.ScheduleRepository;
import com.yaremax.busticketbooth_tech.repositories.TicketRepository;
import com.yaremax.busticketbooth_tech.services.BusService;
import com.yaremax.busticketbooth_tech.services.RouteService;
import com.yaremax.busticketbooth_tech.services.ScheduleService;
import com.yaremax.busticketbooth_tech.services.TicketService;
import com.yaremax.busticketbooth_tech.services.TransportServiceMediator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

class BusControllerTest {
//    /**
//     * Method under test: {@link BusController#getBuses(Model)}
//     */
//    @Test
//    void testGetBuses() {
//
//
//        // Arrange
//        BusRepository busRepository = mock(BusRepository.class);
//        when(busRepository.findAll()).thenReturn(new ArrayList<>());
//        BusService busService = new BusService(busRepository);
//        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
//        ScheduleService scheduleService = new ScheduleService(scheduleRepository, new RouteStopDtoMapper());
//
//        TicketRepository ticketRepository = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository = mock(BusStopRepository.class);
//        TicketService ticketService = new TicketService(ticketRepository, scheduleRepository2, busStopRepository,
//                new TicketMapper());
//
//        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
//                mock(RouteRepository.class));
//
//        BusController busController = new BusController(busService, new TransportServiceMediator(scheduleService,
//                ticketService, routeService, new BusService(mock(BusRepository.class))));
//        ConcurrentModel model = new ConcurrentModel();
//
//        // Act
//        String actualBuses = busController.getBuses(model);
//
//        // Assert
//        verify(busRepository).findAll();
//        assertEquals("buses", actualBuses);
//    }
//
//    /**
//     * Method under test: {@link BusController#getBuses(Model)}
//     */
//    @Test
//    void testGetBuses2() {
//
//
//        // Arrange
//        BusService busService = mock(BusService.class);
//        when(busService.findAll()).thenReturn(new ArrayList<>());
//        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
//        ScheduleService scheduleService = new ScheduleService(scheduleRepository, new RouteStopDtoMapper());
//
//        TicketRepository ticketRepository = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository = mock(BusStopRepository.class);
//        TicketService ticketService = new TicketService(ticketRepository, scheduleRepository2, busStopRepository,
//                new TicketMapper());
//
//        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
//                mock(RouteRepository.class));
//
//        BusController busController = new BusController(busService, new TransportServiceMediator(scheduleService,
//                ticketService, routeService, new BusService(mock(BusRepository.class))));
//        ConcurrentModel model = new ConcurrentModel();
//
//        // Act
//        String actualBuses = busController.getBuses(model);
//
//        // Assert
//        verify(busService).findAll();
//        assertEquals("buses", actualBuses);
//    }
//
//    /**
//     * Method under test: {@link BusController#addBus(String, Integer)}
//     */
//    @Test
//    void testAddBus() {
//
//        // Arrange
//        Bus bus = new Bus();
//        bus.setId(1);
//        bus.setSeatCapacity(1);
//        bus.setSerialNumber("42");
//        BusRepository busRepository = mock(BusRepository.class);
//        when(busRepository.existsBySerialNumber(Mockito.<String>any())).thenReturn(false);
//        when(busRepository.save(Mockito.<Bus>any())).thenReturn(bus);
//        BusService busService = new BusService(busRepository);
//        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
//        ScheduleService scheduleService = new ScheduleService(scheduleRepository, new RouteStopDtoMapper());
//
//        TicketRepository ticketRepository = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository = mock(BusStopRepository.class);
//        TicketService ticketService = new TicketService(ticketRepository, scheduleRepository2, busStopRepository,
//                new TicketMapper());
//
//        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
//                mock(RouteRepository.class));
//
//        BusController busController = new BusController(busService, new TransportServiceMediator(scheduleService,
//                ticketService, routeService, new BusService(mock(BusRepository.class))));
//        String serialNumber = "42";
//        int seatCapacity = 2;
//
//        // Act
//        String actualAddBusResult = busController.addBus(serialNumber, seatCapacity);
//
//        // Assert
//        verify(busRepository).existsBySerialNumber(Mockito.<String>any());
//        verify(busRepository).save(Mockito.<Bus>any());
//        assertEquals("redirect:/buses", actualAddBusResult);
//    }
//
//    /**
//     * Method under test: {@link BusController#addBus(String, Integer)}
//     */
//    @Test
//    void testAddBus2() {
//
//        // Arrange
//        BusService busService = mock(BusService.class);
//        doNothing().when(busService).addBus(Mockito.<Bus>any());
//        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
//        ScheduleService scheduleService = new ScheduleService(scheduleRepository, new RouteStopDtoMapper());
//
//        TicketRepository ticketRepository = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository = mock(BusStopRepository.class);
//        TicketService ticketService = new TicketService(ticketRepository, scheduleRepository2, busStopRepository,
//                new TicketMapper());
//
//        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
//                mock(RouteRepository.class));
//
//        BusController busController = new BusController(busService, new TransportServiceMediator(scheduleService,
//                ticketService, routeService, new BusService(mock(BusRepository.class))));
//        String serialNumber = "42";
//        int seatCapacity = 2;
//
//        // Act
//        String actualAddBusResult = busController.addBus(serialNumber, seatCapacity);
//
//        // Assert
//        verify(busService).addBus(Mockito.<Bus>any());
//        assertEquals("redirect:/buses", actualAddBusResult);
//    }
//
//    /**
//     * Method under test: {@link BusController#deleteBus(Integer)}
//     */
//    @Test
//    void testDeleteBus() {
//        // Arrange
//        Bus bus = new Bus();
//        bus.setId(1);
//        bus.setSeatCapacity(1);
//        bus.setSerialNumber("42");
//        Optional<Bus> ofResult = Optional.of(bus);
//        BusRepository busRepository = mock(BusRepository.class);
//        doNothing().when(busRepository).delete(Mockito.<Bus>any());
//        when(busRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
//        BusService busService = new BusService(busRepository);
//        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
//        ScheduleService scheduleService = new ScheduleService(scheduleRepository, new RouteStopDtoMapper());
//
//        TicketRepository ticketRepository = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository = mock(BusStopRepository.class);
//        TicketService ticketService = new TicketService(ticketRepository, scheduleRepository2, busStopRepository,
//                new TicketMapper());
//
//        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
//                mock(RouteRepository.class));
//
//        BusController busController = new BusController(busService, new TransportServiceMediator(scheduleService,
//                ticketService, routeService, new BusService(mock(BusRepository.class))));
//        int id = 1;
//
//        // Act
//        String actualDeleteBusResult = busController.deleteBus(id);
//
//        // Assert
//        verify(busRepository).delete(Mockito.<Bus>any());
//        verify(busRepository).findById(Mockito.<Integer>any());
//        assertEquals("redirect:/buses", actualDeleteBusResult);
//    }
//
//    /**
//     * Method under test: {@link BusController#deleteBus(Integer)}
//     */
//    @Test
//    void testDeleteBus2() {
//        // Arrange
//        BusService busService = mock(BusService.class);
//        doNothing().when(busService).deleteBus(Mockito.<Integer>any());
//        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
//        ScheduleService scheduleService = new ScheduleService(scheduleRepository, new RouteStopDtoMapper());
//
//        TicketRepository ticketRepository = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository = mock(BusStopRepository.class);
//        TicketService ticketService = new TicketService(ticketRepository, scheduleRepository2, busStopRepository,
//                new TicketMapper());
//
//        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
//                mock(RouteRepository.class));
//
//        BusController busController = new BusController(busService, new TransportServiceMediator(scheduleService,
//                ticketService, routeService, new BusService(mock(BusRepository.class))));
//        int id = 1;
//
//        // Act
//        String actualDeleteBusResult = busController.deleteBus(id);
//
//        // Assert
//        verify(busService).deleteBus(Mockito.<Integer>any());
//        assertEquals("redirect:/buses", actualDeleteBusResult);
//    }
//
//    /**
//     * Method under test: {@link BusController#patchBus(Integer, String, Integer)}
//     */
//    @Test
//    void testPatchBus() {
//
//
//        // Arrange
//        TicketRepository ticketRepository = mock(TicketRepository.class);
//        when(ticketRepository.countTicketsForScheduleByBus(Mockito.<Integer>any())).thenReturn(1);
//        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository = mock(BusStopRepository.class);
//        TicketService ticketService = new TicketService(ticketRepository, scheduleRepository, busStopRepository,
//                new TicketMapper());
//
//        Bus bus = new Bus();
//        bus.setId(1);
//        bus.setSeatCapacity(1);
//        bus.setSerialNumber("42");
//        Optional<Bus> ofResult = Optional.of(bus);
//
//        Bus bus2 = new Bus();
//        bus2.setId(1);
//        bus2.setSeatCapacity(1);
//        bus2.setSerialNumber("42");
//        BusRepository busRepository = mock(BusRepository.class);
//        when(busRepository.save(Mockito.<Bus>any())).thenReturn(bus2);
//        when(busRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
//        BusService busService = new BusService(busRepository);
//        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
//        ScheduleService scheduleService = new ScheduleService(scheduleRepository2, new RouteStopDtoMapper());
//
//        TransportServiceMediator transportServiceMediator = new TransportServiceMediator(scheduleService, ticketService,
//                new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class), mock(RouteRepository.class)),
//                busService);
//
//        BusController busController = new BusController(new BusService(mock(BusRepository.class)),
//                transportServiceMediator);
//        int id = 1;
//        String newSerialNumber = "42";
//        int newSeatCapacity = 1;
//
//        // Act
//        String actualPatchBusResult = busController.patchBus(id, newSerialNumber, newSeatCapacity);
//
//        // Assert
//        verify(ticketRepository).countTicketsForScheduleByBus(Mockito.<Integer>any());
//        verify(busRepository).findById(Mockito.<Integer>any());
//        verify(busRepository).save(Mockito.<Bus>any());
//        assertEquals("redirect:/buses", actualPatchBusResult);
//    }
//
//    /**
//     * Method under test: {@link BusController#patchBus(Integer, String, Integer)}
//     */
//    @Test
//    void testPatchBus2() {
//
//
//        // Arrange
//        TicketService ticketService = mock(TicketService.class);
//        when(ticketService.countTicketsForScheduleByBus(Mockito.<Integer>any())).thenReturn(3);
//        BusService busService = mock(BusService.class);
//        doNothing().when(busService)
//                .patchBus(Mockito.<Integer>any(), Mockito.<String>any(), Mockito.<Integer>any(), Mockito.<Integer>any());
//        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
//        ScheduleService scheduleService = new ScheduleService(scheduleRepository, new RouteStopDtoMapper());
//
//        TransportServiceMediator transportServiceMediator = new TransportServiceMediator(scheduleService, ticketService,
//                new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class), mock(RouteRepository.class)),
//                busService);
//
//        BusController busController = new BusController(new BusService(mock(BusRepository.class)),
//                transportServiceMediator);
//        int id = 1;
//        String newSerialNumber = "42";
//        int newSeatCapacity = 1;
//
//        // Act
//        String actualPatchBusResult = busController.patchBus(id, newSerialNumber, newSeatCapacity);
//
//        // Assert
//        verify(busService).patchBus(Mockito.<Integer>any(), Mockito.<String>any(), Mockito.<Integer>any(),
//                Mockito.<Integer>any());
//        verify(ticketService).countTicketsForScheduleByBus(Mockito.<Integer>any());
//        assertEquals("redirect:/buses", actualPatchBusResult);
//    }
//
//    /**
//     * Method under test: {@link BusController#patchBus(Integer, String, Integer)}
//     */
//    @Test
//    void testPatchBus3() {
//
//
//        // Arrange
//        TransportServiceMediator transportServiceMediator = mock(TransportServiceMediator.class);
//        doNothing().when(transportServiceMediator)
//                .patchBus(Mockito.<Integer>any(), Mockito.<String>any(), Mockito.<Integer>any());
//        BusController busController = new BusController(new BusService(mock(BusRepository.class)),
//                transportServiceMediator);
//        int id = 1;
//        String newSerialNumber = "42";
//        int newSeatCapacity = 1;
//
//        // Act
//        String actualPatchBusResult = busController.patchBus(id, newSerialNumber, newSeatCapacity);
//
//        // Assert
//        verify(transportServiceMediator).patchBus(Mockito.<Integer>any(), Mockito.<String>any(), Mockito.<Integer>any());
//        assertEquals("redirect:/buses", actualPatchBusResult);
//    }
//
//    /**
//     * Method under test:
//     * {@link BusController#getAvailableBusInfos(LocalDateTime, Integer)}
//     */
//    @Test
//    void testGetAvailableBusInfos() {
//
//
//        // Arrange
//        Route route = new Route();
//        route.setId(1);
//        route.setName("Name");
//        route.setRouteStops(new HashSet<>());
//        Optional<Route> ofResult = Optional.of(route);
//        RouteRepository routeRepository = mock(RouteRepository.class);
//        when(routeRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
//        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
//                routeRepository);
//
//        BusRepository busRepository = mock(BusRepository.class);
//        when(busRepository.findAvailableBusInfosByTime(Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any()))
//                .thenReturn(new ArrayList<>());
//        BusService busService = new BusService(busRepository);
//        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
//        ScheduleService scheduleService = new ScheduleService(scheduleRepository, new RouteStopDtoMapper());
//
//        TicketRepository ticketRepository = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository = mock(BusStopRepository.class);
//        TransportServiceMediator transportServiceMediator = new TransportServiceMediator(scheduleService,
//                new TicketService(ticketRepository, scheduleRepository2, busStopRepository, new TicketMapper()), routeService,
//                busService);
//
//        BusController busController = new BusController(new BusService(mock(BusRepository.class)),
//                transportServiceMediator);
//        LocalDateTime inputDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
//        int routeId = 1;
//
//        // Act
//        ResponseEntity<List<BusInfo>> actualAvailableBusInfos = busController.getAvailableBusInfos(inputDateTime, routeId);
//
//        // Assert
//        verify(busRepository).findAvailableBusInfosByTime(Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any());
//        verify(routeRepository).findById(Mockito.<Integer>any());
//        assertEquals(200, actualAvailableBusInfos.getStatusCodeValue());
//        assertTrue(actualAvailableBusInfos.hasBody());
//        assertTrue(actualAvailableBusInfos.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test:
//     * {@link BusController#getAvailableBusInfos(LocalDateTime, Integer)}
//     */
//    @Test
//    void testGetAvailableBusInfos2() {
//
//
//        // Arrange
//        BusStop busStop = new BusStop();
//        busStop.setId(1);
//        busStop.setName("Name");
//
//        RouteStopId id = new RouteStopId();
//        id.setRouteId(1);
//        id.setStopId(1);
//
//        Route route = new Route();
//        route.setId(1);
//        route.setName("Name");
//        route.setRouteStops(new HashSet<>());
//
//        RouteStop routeStop = new RouteStop();
//        routeStop.setBusStop(busStop);
//        routeStop.setDepartureMinutesOffset(1);
//        routeStop.setId(id);
//        routeStop.setRoute(route);
//        routeStop.setSequenceNumber(10);
//
//        HashSet<RouteStop> routeStops = new HashSet<>();
//        routeStops.add(routeStop);
//
//        Route route2 = new Route();
//        route2.setId(1);
//        route2.setName("Name");
//        route2.setRouteStops(routeStops);
//        Optional<Route> ofResult = Optional.of(route2);
//        RouteRepository routeRepository = mock(RouteRepository.class);
//        when(routeRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
//        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
//                routeRepository);
//
//        BusRepository busRepository = mock(BusRepository.class);
//        when(busRepository.findAvailableBusInfosByTime(Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any()))
//                .thenReturn(new ArrayList<>());
//        BusService busService = new BusService(busRepository);
//        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
//        ScheduleService scheduleService = new ScheduleService(scheduleRepository, new RouteStopDtoMapper());
//
//        TicketRepository ticketRepository = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository = mock(BusStopRepository.class);
//        TransportServiceMediator transportServiceMediator = new TransportServiceMediator(scheduleService,
//                new TicketService(ticketRepository, scheduleRepository2, busStopRepository, new TicketMapper()), routeService,
//                busService);
//
//        BusController busController = new BusController(new BusService(mock(BusRepository.class)),
//                transportServiceMediator);
//        LocalDateTime inputDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
//        int routeId = 1;
//
//        // Act
//        ResponseEntity<List<BusInfo>> actualAvailableBusInfos = busController.getAvailableBusInfos(inputDateTime, routeId);
//
//        // Assert
//        verify(busRepository).findAvailableBusInfosByTime(Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any());
//        verify(routeRepository).findById(Mockito.<Integer>any());
//        assertEquals(200, actualAvailableBusInfos.getStatusCodeValue());
//        assertTrue(actualAvailableBusInfos.hasBody());
//        assertTrue(actualAvailableBusInfos.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test:
//     * {@link BusController#getAvailableBusInfos(LocalDateTime, Integer)}
//     */
//    @Test
//    void testGetAvailableBusInfos3() {
//
//
//        // Arrange
//        Route route = new Route();
//        route.setId(1);
//        route.setName("Name");
//        route.setRouteStops(new HashSet<>());
//        RouteService routeService = mock(RouteService.class);
//        when(routeService.findById(Mockito.<Integer>any())).thenReturn(route);
//        BusRepository busRepository = mock(BusRepository.class);
//        when(busRepository.findAvailableBusInfosByTime(Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any()))
//                .thenReturn(new ArrayList<>());
//        BusService busService = new BusService(busRepository);
//        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
//        ScheduleService scheduleService = new ScheduleService(scheduleRepository, new RouteStopDtoMapper());
//
//        TicketRepository ticketRepository = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository = mock(BusStopRepository.class);
//        TransportServiceMediator transportServiceMediator = new TransportServiceMediator(scheduleService,
//                new TicketService(ticketRepository, scheduleRepository2, busStopRepository, new TicketMapper()), routeService,
//                busService);
//
//        BusController busController = new BusController(new BusService(mock(BusRepository.class)),
//                transportServiceMediator);
//        LocalDateTime inputDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
//        int routeId = 1;
//
//        // Act
//        ResponseEntity<List<BusInfo>> actualAvailableBusInfos = busController.getAvailableBusInfos(inputDateTime, routeId);
//
//        // Assert
//        verify(busRepository).findAvailableBusInfosByTime(Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any());
//        verify(routeService).findById(Mockito.<Integer>any());
//        assertEquals(200, actualAvailableBusInfos.getStatusCodeValue());
//        assertTrue(actualAvailableBusInfos.hasBody());
//        assertTrue(actualAvailableBusInfos.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test:
//     * {@link BusController#getAvailableBusInfos(LocalDateTime, Integer)}
//     */
//    @Test
//    void testGetAvailableBusInfos4() {
//
//
//        // Arrange
//        Route route = new Route();
//        route.setId(1);
//        route.setName("Name");
//        route.setRouteStops(new HashSet<>());
//        RouteService routeService = mock(RouteService.class);
//        when(routeService.findById(Mockito.<Integer>any())).thenReturn(route);
//        BusService busService = mock(BusService.class);
//        when(busService.findAvailableBusInfosByTimeAndRoute(Mockito.<LocalDateTime>any(), Mockito.<Set<RouteStop>>any()))
//                .thenReturn(new ArrayList<>());
//        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
//        ScheduleService scheduleService = new ScheduleService(scheduleRepository, new RouteStopDtoMapper());
//
//        TicketRepository ticketRepository = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository = mock(BusStopRepository.class);
//        TransportServiceMediator transportServiceMediator = new TransportServiceMediator(scheduleService,
//                new TicketService(ticketRepository, scheduleRepository2, busStopRepository, new TicketMapper()), routeService,
//                busService);
//
//        BusController busController = new BusController(new BusService(mock(BusRepository.class)),
//                transportServiceMediator);
//        LocalDateTime inputDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
//        int routeId = 1;
//
//        // Act
//        ResponseEntity<List<BusInfo>> actualAvailableBusInfos = busController.getAvailableBusInfos(inputDateTime, routeId);
//
//        // Assert
//        verify(busService).findAvailableBusInfosByTimeAndRoute(Mockito.<LocalDateTime>any(), Mockito.<Set<RouteStop>>any());
//        verify(routeService).findById(Mockito.<Integer>any());
//        assertEquals(200, actualAvailableBusInfos.getStatusCodeValue());
//        assertTrue(actualAvailableBusInfos.hasBody());
//        assertTrue(actualAvailableBusInfos.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test:
//     * {@link BusController#getAvailableBusInfos(LocalDateTime, Integer)}
//     */
//    @Test
//    void testGetAvailableBusInfos5() {
//
//
//        // Arrange
//        TransportServiceMediator transportServiceMediator = mock(TransportServiceMediator.class);
//        when(transportServiceMediator.findAvailableBusInfosByTimeAndRoute(Mockito.<LocalDateTime>any(),
//                Mockito.<Integer>any())).thenReturn(new ArrayList<>());
//        BusController busController = new BusController(new BusService(mock(BusRepository.class)),
//                transportServiceMediator);
//        LocalDateTime inputDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
//        int routeId = 1;
//
//        // Act
//        ResponseEntity<List<BusInfo>> actualAvailableBusInfos = busController.getAvailableBusInfos(inputDateTime, routeId);
//
//        // Assert
//        verify(transportServiceMediator).findAvailableBusInfosByTimeAndRoute(Mockito.<LocalDateTime>any(),
//                Mockito.<Integer>any());
//        assertEquals(200, actualAvailableBusInfos.getStatusCodeValue());
//        assertTrue(actualAvailableBusInfos.hasBody());
//        assertTrue(actualAvailableBusInfos.getHeaders().isEmpty());
//    }
}
