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
import com.yaremax.busticketbooth_tech.data.Schedule;
import com.yaremax.busticketbooth_tech.data.Ticket;
import com.yaremax.busticketbooth_tech.mappers.RouteStopDtoMapper;
import com.yaremax.busticketbooth_tech.mappers.TicketMapper;
import com.yaremax.busticketbooth_tech.projections.ScheduleInfo;
import com.yaremax.busticketbooth_tech.repositories.BusRepository;
import com.yaremax.busticketbooth_tech.repositories.BusStopRepository;
import com.yaremax.busticketbooth_tech.repositories.RouteRepository;
import com.yaremax.busticketbooth_tech.repositories.ScheduleRepository;
import com.yaremax.busticketbooth_tech.repositories.TicketRepository;
import com.yaremax.busticketbooth_tech.services.BusService;
import com.yaremax.busticketbooth_tech.services.BusStopService;
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

class ScheduleControllerTest {
//    /**
//     * Method under test: {@link ScheduleController#showAllSchedules(Model)}
//     */
//    @Test
//    void testShowAllSchedules() {
//        // Arrange
//        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
//        when(scheduleRepository.findAllScheduleInfo()).thenReturn(new ArrayList<>());
//        ScheduleService scheduleService = new ScheduleService(scheduleRepository, new RouteStopDtoMapper());
//
//        BusStopRepository busStopRepository = mock(BusStopRepository.class);
//        when(busStopRepository.findAll()).thenReturn(new ArrayList<>());
//        BusStopService busStopService = new BusStopService(busStopRepository);
//        RouteRepository routeRepository = mock(RouteRepository.class);
//        when(routeRepository.findAll()).thenReturn(new ArrayList<>());
//        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
//                routeRepository);
//
//        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
//        ScheduleService scheduleService2 = new ScheduleService(scheduleRepository2, new RouteStopDtoMapper());
//
//        TicketRepository ticketRepository = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository3 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository2 = mock(BusStopRepository.class);
//        TicketService ticketService = new TicketService(ticketRepository, scheduleRepository3, busStopRepository2,
//                new TicketMapper());
//
//        RouteService routeService2 = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
//                mock(RouteRepository.class));
//
//        TransportServiceMediator transportServiceMediator = new TransportServiceMediator(scheduleService2, ticketService,
//                routeService2, new BusService(mock(BusRepository.class)));
//
//        TicketRepository ticketRepository2 = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository4 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository3 = mock(BusStopRepository.class);
//        ScheduleController scheduleController = new ScheduleController(transportServiceMediator, scheduleService,
//                busStopService,
//                new TicketService(ticketRepository2, scheduleRepository4, busStopRepository3, new TicketMapper()),
//                routeService);
//        ConcurrentModel model = new ConcurrentModel();
//
//        // Act
//        String actualShowAllSchedulesResult = scheduleController.showAllSchedules(model);
//
//        // Assert
//        verify(scheduleRepository).findAllScheduleInfo();
//        verify(busStopRepository).findAll();
//        verify(routeRepository).findAll();
//        assertEquals("schedules", actualShowAllSchedulesResult);
//    }
//
//    /**
//     * Method under test: {@link ScheduleController#showAllSchedules(Model)}
//     */
//    @Test
//    void testShowAllSchedules2() {
//        // Arrange
//        ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
//        when(scheduleInfo.getDepartureDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
//
//        ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
//        scheduleInfoList.add(scheduleInfo);
//        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
//        when(scheduleRepository.findAllScheduleInfo()).thenReturn(scheduleInfoList);
//        ScheduleService scheduleService = new ScheduleService(scheduleRepository, new RouteStopDtoMapper());
//
//        BusStopRepository busStopRepository = mock(BusStopRepository.class);
//        when(busStopRepository.findAll()).thenReturn(new ArrayList<>());
//        BusStopService busStopService = new BusStopService(busStopRepository);
//        RouteRepository routeRepository = mock(RouteRepository.class);
//        when(routeRepository.findAll()).thenReturn(new ArrayList<>());
//        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
//                routeRepository);
//
//        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
//        ScheduleService scheduleService2 = new ScheduleService(scheduleRepository2, new RouteStopDtoMapper());
//
//        TicketRepository ticketRepository = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository3 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository2 = mock(BusStopRepository.class);
//        TicketService ticketService = new TicketService(ticketRepository, scheduleRepository3, busStopRepository2,
//                new TicketMapper());
//
//        RouteService routeService2 = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
//                mock(RouteRepository.class));
//
//        TransportServiceMediator transportServiceMediator = new TransportServiceMediator(scheduleService2, ticketService,
//                routeService2, new BusService(mock(BusRepository.class)));
//
//        TicketRepository ticketRepository2 = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository4 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository3 = mock(BusStopRepository.class);
//        ScheduleController scheduleController = new ScheduleController(transportServiceMediator, scheduleService,
//                busStopService,
//                new TicketService(ticketRepository2, scheduleRepository4, busStopRepository3, new TicketMapper()),
//                routeService);
//        ConcurrentModel model = new ConcurrentModel();
//
//        // Act
//        String actualShowAllSchedulesResult = scheduleController.showAllSchedules(model);
//
//        // Assert
//        verify(scheduleInfo).getDepartureDateTime();
//        verify(scheduleRepository).findAllScheduleInfo();
//        verify(busStopRepository).findAll();
//        verify(routeRepository).findAll();
//        assertEquals("schedules", actualShowAllSchedulesResult);
//    }
//
//    /**
//     * Method under test: {@link ScheduleController#showAllSchedules(Model)}
//     */
//    @Test
//    void testShowAllSchedules3() {
//        // Arrange
//        ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
//        when(scheduleInfo.getDepartureDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
//        ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
//        when(scheduleInfo2.getDepartureDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
//
//        ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
//        scheduleInfoList.add(scheduleInfo2);
//        scheduleInfoList.add(scheduleInfo);
//        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
//        when(scheduleRepository.findAllScheduleInfo()).thenReturn(scheduleInfoList);
//        ScheduleService scheduleService = new ScheduleService(scheduleRepository, new RouteStopDtoMapper());
//
//        BusStopRepository busStopRepository = mock(BusStopRepository.class);
//        when(busStopRepository.findAll()).thenReturn(new ArrayList<>());
//        BusStopService busStopService = new BusStopService(busStopRepository);
//        RouteRepository routeRepository = mock(RouteRepository.class);
//        when(routeRepository.findAll()).thenReturn(new ArrayList<>());
//        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
//                routeRepository);
//
//        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
//        ScheduleService scheduleService2 = new ScheduleService(scheduleRepository2, new RouteStopDtoMapper());
//
//        TicketRepository ticketRepository = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository3 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository2 = mock(BusStopRepository.class);
//        TicketService ticketService = new TicketService(ticketRepository, scheduleRepository3, busStopRepository2,
//                new TicketMapper());
//
//        RouteService routeService2 = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
//                mock(RouteRepository.class));
//
//        TransportServiceMediator transportServiceMediator = new TransportServiceMediator(scheduleService2, ticketService,
//                routeService2, new BusService(mock(BusRepository.class)));
//
//        TicketRepository ticketRepository2 = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository4 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository3 = mock(BusStopRepository.class);
//        ScheduleController scheduleController = new ScheduleController(transportServiceMediator, scheduleService,
//                busStopService,
//                new TicketService(ticketRepository2, scheduleRepository4, busStopRepository3, new TicketMapper()),
//                routeService);
//        ConcurrentModel model = new ConcurrentModel();
//
//        // Act
//        String actualShowAllSchedulesResult = scheduleController.showAllSchedules(model);
//
//        // Assert
//        verify(scheduleInfo2).getDepartureDateTime();
//        verify(scheduleInfo).getDepartureDateTime();
//        verify(scheduleRepository).findAllScheduleInfo();
//        verify(busStopRepository).findAll();
//        verify(routeRepository).findAll();
//        assertEquals("schedules", actualShowAllSchedulesResult);
//    }
//
//    /**
//     * Method under test:
//     * {@link ScheduleController#showBestSchedulesToStop(Model, Integer)}
//     */
//    @Test
//    void testShowBestSchedulesToStop() {
//        // Arrange
//        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
//        when(scheduleRepository.findAllScheduleInfo()).thenReturn(new ArrayList<>());
//        ScheduleService scheduleService = new ScheduleService(scheduleRepository, new RouteStopDtoMapper());
//
//        BusStopRepository busStopRepository = mock(BusStopRepository.class);
//        when(busStopRepository.findAll()).thenReturn(new ArrayList<>());
//        BusStopService busStopService = new BusStopService(busStopRepository);
//        RouteRepository routeRepository = mock(RouteRepository.class);
//        when(routeRepository.findAll()).thenReturn(new ArrayList<>());
//        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
//                routeRepository);
//
//        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
//        ScheduleService scheduleService2 = new ScheduleService(scheduleRepository2, new RouteStopDtoMapper());
//
//        TicketRepository ticketRepository = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository3 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository2 = mock(BusStopRepository.class);
//        TicketService ticketService = new TicketService(ticketRepository, scheduleRepository3, busStopRepository2,
//                new TicketMapper());
//
//        RouteService routeService2 = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
//                mock(RouteRepository.class));
//
//        TransportServiceMediator transportServiceMediator = new TransportServiceMediator(scheduleService2, ticketService,
//                routeService2, new BusService(mock(BusRepository.class)));
//
//        TicketRepository ticketRepository2 = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository4 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository3 = mock(BusStopRepository.class);
//        ScheduleController scheduleController = new ScheduleController(transportServiceMediator, scheduleService,
//                busStopService,
//                new TicketService(ticketRepository2, scheduleRepository4, busStopRepository3, new TicketMapper()),
//                routeService);
//        ConcurrentModel model = new ConcurrentModel();
//        int busStopId = 1;
//
//        // Act
//        String actualShowBestSchedulesToStopResult = scheduleController.showBestSchedulesToStop(model, busStopId);
//
//        // Assert
//        verify(scheduleRepository).findAllScheduleInfo();
//        verify(busStopRepository).findAll();
//        verify(routeRepository).findAll();
//        assertEquals("schedules", actualShowBestSchedulesToStopResult);
//    }
//
//    /**
//     * Method under test:
//     * {@link ScheduleController#showBestSchedulesToStop(Model, Integer)}
//     */
//    @Test
//    void testShowBestSchedulesToStop2() {
//        // Arrange
//        ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
//        when(scheduleInfo.getDepartureDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
//
//        ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
//        scheduleInfoList.add(scheduleInfo);
//        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
//        when(scheduleRepository.findAllScheduleInfo()).thenReturn(scheduleInfoList);
//        ScheduleService scheduleService = new ScheduleService(scheduleRepository, new RouteStopDtoMapper());
//
//        BusStopRepository busStopRepository = mock(BusStopRepository.class);
//        when(busStopRepository.findAll()).thenReturn(new ArrayList<>());
//        BusStopService busStopService = new BusStopService(busStopRepository);
//        RouteRepository routeRepository = mock(RouteRepository.class);
//        when(routeRepository.findAll()).thenReturn(new ArrayList<>());
//        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
//                routeRepository);
//
//        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
//        ScheduleService scheduleService2 = new ScheduleService(scheduleRepository2, new RouteStopDtoMapper());
//
//        TicketRepository ticketRepository = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository3 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository2 = mock(BusStopRepository.class);
//        TicketService ticketService = new TicketService(ticketRepository, scheduleRepository3, busStopRepository2,
//                new TicketMapper());
//
//        RouteService routeService2 = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
//                mock(RouteRepository.class));
//
//        TransportServiceMediator transportServiceMediator = new TransportServiceMediator(scheduleService2, ticketService,
//                routeService2, new BusService(mock(BusRepository.class)));
//
//        TicketRepository ticketRepository2 = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository4 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository3 = mock(BusStopRepository.class);
//        ScheduleController scheduleController = new ScheduleController(transportServiceMediator, scheduleService,
//                busStopService,
//                new TicketService(ticketRepository2, scheduleRepository4, busStopRepository3, new TicketMapper()),
//                routeService);
//        ConcurrentModel model = new ConcurrentModel();
//        int busStopId = 1;
//
//        // Act
//        String actualShowBestSchedulesToStopResult = scheduleController.showBestSchedulesToStop(model, busStopId);
//
//        // Assert
//        verify(scheduleInfo).getDepartureDateTime();
//        verify(scheduleRepository).findAllScheduleInfo();
//        verify(busStopRepository).findAll();
//        verify(routeRepository).findAll();
//        assertEquals("schedules", actualShowBestSchedulesToStopResult);
//    }
//
//    /**
//     * Method under test:
//     * {@link ScheduleController#showBestSchedulesToStop(Model, Integer)}
//     */
//    @Test
//    void testShowBestSchedulesToStop3() {
//        // Arrange
//        ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
//        when(scheduleInfo.getDepartureDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
//        ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
//        when(scheduleInfo2.getDepartureDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
//
//        ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
//        scheduleInfoList.add(scheduleInfo2);
//        scheduleInfoList.add(scheduleInfo);
//        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
//        when(scheduleRepository.findAllScheduleInfo()).thenReturn(scheduleInfoList);
//        ScheduleService scheduleService = new ScheduleService(scheduleRepository, new RouteStopDtoMapper());
//
//        BusStopRepository busStopRepository = mock(BusStopRepository.class);
//        when(busStopRepository.findAll()).thenReturn(new ArrayList<>());
//        BusStopService busStopService = new BusStopService(busStopRepository);
//        RouteRepository routeRepository = mock(RouteRepository.class);
//        when(routeRepository.findAll()).thenReturn(new ArrayList<>());
//        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
//                routeRepository);
//
//        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
//        ScheduleService scheduleService2 = new ScheduleService(scheduleRepository2, new RouteStopDtoMapper());
//
//        TicketRepository ticketRepository = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository3 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository2 = mock(BusStopRepository.class);
//        TicketService ticketService = new TicketService(ticketRepository, scheduleRepository3, busStopRepository2,
//                new TicketMapper());
//
//        RouteService routeService2 = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
//                mock(RouteRepository.class));
//
//        TransportServiceMediator transportServiceMediator = new TransportServiceMediator(scheduleService2, ticketService,
//                routeService2, new BusService(mock(BusRepository.class)));
//
//        TicketRepository ticketRepository2 = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository4 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository3 = mock(BusStopRepository.class);
//        ScheduleController scheduleController = new ScheduleController(transportServiceMediator, scheduleService,
//                busStopService,
//                new TicketService(ticketRepository2, scheduleRepository4, busStopRepository3, new TicketMapper()),
//                routeService);
//        ConcurrentModel model = new ConcurrentModel();
//        int busStopId = 1;
//
//        // Act
//        String actualShowBestSchedulesToStopResult = scheduleController.showBestSchedulesToStop(model, busStopId);
//
//        // Assert
//        verify(scheduleInfo2).getDepartureDateTime();
//        verify(scheduleInfo).getDepartureDateTime();
//        verify(scheduleRepository).findAllScheduleInfo();
//        verify(busStopRepository).findAll();
//        verify(routeRepository).findAll();
//        assertEquals("schedules", actualShowBestSchedulesToStopResult);
//    }
//
//    /**
//     * Method under test: {@link ScheduleController#addSchedule(Schedule)}
//     */
//    @Test
//    void testAddSchedule() {
//        // Arrange
//        Bus bus = new Bus();
//        bus.setId(1);
//        bus.setSeatCapacity(1);
//        bus.setSerialNumber("42");
//
//        Route route = new Route();
//        route.setId(1);
//        route.setName("Name");
//        route.setRouteStops(new HashSet<>());
//
//        Schedule schedule = new Schedule();
//        schedule.setBus(bus);
//        schedule.setDepartureDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
//        schedule.setId(1);
//        schedule.setRoute(route);
//        schedule.setTickets(new HashSet<>());
//        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
//        when(scheduleRepository.save(Mockito.<Schedule>any())).thenReturn(schedule);
//        ScheduleService scheduleService = new ScheduleService(scheduleRepository, new RouteStopDtoMapper());
//
//        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
//        ScheduleService scheduleService2 = new ScheduleService(scheduleRepository2, new RouteStopDtoMapper());
//
//        TicketRepository ticketRepository = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository3 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository = mock(BusStopRepository.class);
//        TicketService ticketService = new TicketService(ticketRepository, scheduleRepository3, busStopRepository,
//                new TicketMapper());
//
//        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
//                mock(RouteRepository.class));
//
//        TransportServiceMediator transportServiceMediator = new TransportServiceMediator(scheduleService2, ticketService,
//                routeService, new BusService(mock(BusRepository.class)));
//
//        BusStopService busStopService = new BusStopService(mock(BusStopRepository.class));
//        TicketRepository ticketRepository2 = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository4 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository2 = mock(BusStopRepository.class);
//        TicketService ticketService2 = new TicketService(ticketRepository2, scheduleRepository4, busStopRepository2,
//                new TicketMapper());
//
//        ScheduleController scheduleController = new ScheduleController(transportServiceMediator, scheduleService,
//                busStopService, ticketService2,
//                new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class), mock(RouteRepository.class)));
//
//        Bus bus2 = new Bus();
//        bus2.setId(1);
//        bus2.setSeatCapacity(1);
//        bus2.setSerialNumber("42");
//
//        Route route2 = new Route();
//        route2.setId(1);
//        route2.setName("Name");
//        route2.setRouteStops(new HashSet<>());
//
//        Schedule schedule2 = new Schedule();
//        schedule2.setBus(bus2);
//        schedule2.setDepartureDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
//        schedule2.setId(1);
//        schedule2.setRoute(route2);
//        schedule2.setTickets(new HashSet<>());
//
//        // Act
//        String actualAddScheduleResult = scheduleController.addSchedule(schedule2);
//
//        // Assert
//        verify(scheduleRepository).save(Mockito.<Schedule>any());
//        assertEquals("redirect:/schedules", actualAddScheduleResult);
//    }
//
//    /**
//     * Method under test: {@link ScheduleController#addSchedule(Schedule)}
//     */
//    @Test
//    void testAddSchedule2() {
//        // Arrange
//        ScheduleService scheduleService = mock(ScheduleService.class);
//        doNothing().when(scheduleService).addSchedule(Mockito.<Schedule>any());
//        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
//        ScheduleService scheduleService2 = new ScheduleService(scheduleRepository, new RouteStopDtoMapper());
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
//        TransportServiceMediator transportServiceMediator = new TransportServiceMediator(scheduleService2, ticketService,
//                routeService, new BusService(mock(BusRepository.class)));
//
//        BusStopService busStopService = new BusStopService(mock(BusStopRepository.class));
//        TicketRepository ticketRepository2 = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository3 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository2 = mock(BusStopRepository.class);
//        TicketService ticketService2 = new TicketService(ticketRepository2, scheduleRepository3, busStopRepository2,
//                new TicketMapper());
//
//        ScheduleController scheduleController = new ScheduleController(transportServiceMediator, scheduleService,
//                busStopService, ticketService2,
//                new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class), mock(RouteRepository.class)));
//
//        Bus bus = new Bus();
//        bus.setId(1);
//        bus.setSeatCapacity(1);
//        bus.setSerialNumber("42");
//
//        Route route = new Route();
//        route.setId(1);
//        route.setName("Name");
//        route.setRouteStops(new HashSet<>());
//
//        Schedule schedule = new Schedule();
//        schedule.setBus(bus);
//        schedule.setDepartureDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
//        schedule.setId(1);
//        schedule.setRoute(route);
//        schedule.setTickets(new HashSet<>());
//
//        // Act
//        String actualAddScheduleResult = scheduleController.addSchedule(schedule);
//
//        // Assert
//        verify(scheduleService).addSchedule(Mockito.<Schedule>any());
//        assertEquals("redirect:/schedules", actualAddScheduleResult);
//    }
//
//    /**
//     * Method under test: {@link ScheduleController#deleteSchedule(Integer)}
//     */
//    @Test
//    void testDeleteSchedule() {
//        // Arrange
//        Bus bus = new Bus();
//        bus.setId(1);
//        bus.setSeatCapacity(1);
//        bus.setSerialNumber("42");
//
//        Route route = new Route();
//        route.setId(1);
//        route.setName("Name");
//        route.setRouteStops(new HashSet<>());
//
//        Schedule schedule = new Schedule();
//        schedule.setBus(bus);
//        schedule.setDepartureDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
//        schedule.setId(1);
//        schedule.setRoute(route);
//        schedule.setTickets(new HashSet<>());
//        Optional<Schedule> ofResult = Optional.of(schedule);
//        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
//        doNothing().when(scheduleRepository).delete(Mockito.<Schedule>any());
//        when(scheduleRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
//        ScheduleService scheduleService = new ScheduleService(scheduleRepository, new RouteStopDtoMapper());
//
//        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
//        ScheduleService scheduleService2 = new ScheduleService(scheduleRepository2, new RouteStopDtoMapper());
//
//        TicketRepository ticketRepository = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository3 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository = mock(BusStopRepository.class);
//        TicketService ticketService = new TicketService(ticketRepository, scheduleRepository3, busStopRepository,
//                new TicketMapper());
//
//        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
//                mock(RouteRepository.class));
//
//        TransportServiceMediator transportServiceMediator = new TransportServiceMediator(scheduleService2, ticketService,
//                routeService, new BusService(mock(BusRepository.class)));
//
//        BusStopService busStopService = new BusStopService(mock(BusStopRepository.class));
//        TicketRepository ticketRepository2 = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository4 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository2 = mock(BusStopRepository.class);
//        TicketService ticketService2 = new TicketService(ticketRepository2, scheduleRepository4, busStopRepository2,
//                new TicketMapper());
//
//        ScheduleController scheduleController = new ScheduleController(transportServiceMediator, scheduleService,
//                busStopService, ticketService2,
//                new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class), mock(RouteRepository.class)));
//        int id = 1;
//
//        // Act
//        String actualDeleteScheduleResult = scheduleController.deleteSchedule(id);
//
//        // Assert
//        verify(scheduleRepository).delete(Mockito.<Schedule>any());
//        verify(scheduleRepository).findById(Mockito.<Integer>any());
//        assertEquals("redirect:/schedules", actualDeleteScheduleResult);
//    }
//
//    /**
//     * Method under test: {@link ScheduleController#deleteSchedule(Integer)}
//     */
//    @Test
//    void testDeleteSchedule2() {
//        // Arrange
//        ScheduleService scheduleService = mock(ScheduleService.class);
//        doNothing().when(scheduleService).deleteSchedule(Mockito.<Integer>any());
//        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
//        ScheduleService scheduleService2 = new ScheduleService(scheduleRepository, new RouteStopDtoMapper());
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
//        TransportServiceMediator transportServiceMediator = new TransportServiceMediator(scheduleService2, ticketService,
//                routeService, new BusService(mock(BusRepository.class)));
//
//        BusStopService busStopService = new BusStopService(mock(BusStopRepository.class));
//        TicketRepository ticketRepository2 = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository3 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository2 = mock(BusStopRepository.class);
//        TicketService ticketService2 = new TicketService(ticketRepository2, scheduleRepository3, busStopRepository2,
//                new TicketMapper());
//
//        ScheduleController scheduleController = new ScheduleController(transportServiceMediator, scheduleService,
//                busStopService, ticketService2,
//                new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class), mock(RouteRepository.class)));
//        int id = 1;
//
//        // Act
//        String actualDeleteScheduleResult = scheduleController.deleteSchedule(id);
//
//        // Assert
//        verify(scheduleService).deleteSchedule(Mockito.<Integer>any());
//        assertEquals("redirect:/schedules", actualDeleteScheduleResult);
//    }
//
//    /**
//     * Method under test:
//     * {@link ScheduleController#getBoardingManifestForSchedule(Integer, Model)}
//     */
//    @Test
//    void testGetBoardingManifestForSchedule() {
//        // Arrange
//        Bus bus = new Bus();
//        bus.setId(1);
//        bus.setSeatCapacity(1);
//        bus.setSerialNumber("42");
//
//        Route route = new Route();
//        route.setId(1);
//        route.setName("Name");
//        route.setRouteStops(new HashSet<>());
//
//        Schedule schedule = new Schedule();
//        schedule.setBus(bus);
//        schedule.setDepartureDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
//        schedule.setId(1);
//        schedule.setRoute(route);
//        schedule.setTickets(new HashSet<>());
//        Optional<Schedule> ofResult = Optional.of(schedule);
//        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
//        when(scheduleRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
//        Optional<ScheduleInfo> ofResult2 = Optional.of(mock(ScheduleInfo.class));
//        when(scheduleRepository.findByIdScheduleInfo(Mockito.<Integer>any())).thenReturn(ofResult2);
//        ScheduleService scheduleService = new ScheduleService(scheduleRepository, new RouteStopDtoMapper());
//
//        TicketRepository ticketRepository = mock(TicketRepository.class);
//        when(ticketRepository.findByScheduleIdAndTicketStatus(Mockito.<Integer>any(), Mockito.<String>any()))
//                .thenReturn(new ArrayList<>());
//        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository = mock(BusStopRepository.class);
//        TicketService ticketService = new TicketService(ticketRepository, scheduleRepository2, busStopRepository,
//                new TicketMapper());
//
//        ScheduleRepository scheduleRepository3 = mock(ScheduleRepository.class);
//        ScheduleService scheduleService2 = new ScheduleService(scheduleRepository3, new RouteStopDtoMapper());
//
//        TicketRepository ticketRepository2 = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository4 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository2 = mock(BusStopRepository.class);
//        TicketService ticketService2 = new TicketService(ticketRepository2, scheduleRepository4, busStopRepository2,
//                new TicketMapper());
//
//        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
//                mock(RouteRepository.class));
//
//        TransportServiceMediator transportServiceMediator = new TransportServiceMediator(scheduleService2, ticketService2,
//                routeService, new BusService(mock(BusRepository.class)));
//
//        BusStopService busStopService = new BusStopService(mock(BusStopRepository.class));
//        ScheduleController scheduleController = new ScheduleController(transportServiceMediator, scheduleService,
//                busStopService, ticketService,
//                new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class), mock(RouteRepository.class)));
//        int scheduleId = 1;
//        ConcurrentModel model = new ConcurrentModel();
//
//        // Act
//        String actualBoardingManifestForSchedule = scheduleController.getBoardingManifestForSchedule(scheduleId, model);
//
//        // Assert
//        verify(scheduleRepository).findByIdScheduleInfo(Mockito.<Integer>any());
//        verify(ticketRepository).findByScheduleIdAndTicketStatus(Mockito.<Integer>any(), Mockito.<String>any());
//        verify(scheduleRepository).findById(Mockito.<Integer>any());
//        assertEquals("boarding_manifest", actualBoardingManifestForSchedule);
//    }
//
//    /**
//     * Method under test:
//     * {@link ScheduleController#getBoardingManifestForSchedule(Integer, Model)}
//     */
//    @Test
//    void testGetBoardingManifestForSchedule2() {
//        // Arrange
//        Bus bus = new Bus();
//        bus.setId(1);
//        bus.setSeatCapacity(1);
//        bus.setSerialNumber("42");
//
//        Route route = new Route();
//        route.setId(1);
//        route.setName("Name");
//        route.setRouteStops(new HashSet<>());
//
//        BusStop busStop = new BusStop();
//        busStop.setId(1);
//        busStop.setName("Name");
//
//        RouteStopId id = new RouteStopId();
//        id.setRouteId(1);
//        id.setStopId(1);
//
//        Route route2 = new Route();
//        route2.setId(1);
//        route2.setName("Name");
//        route2.setRouteStops(new HashSet<>());
//
//        RouteStop routeStop = new RouteStop();
//        routeStop.setBusStop(busStop);
//        routeStop.setDepartureMinutesOffset(1);
//        routeStop.setId(id);
//        routeStop.setRoute(route2);
//        routeStop.setSequenceNumber(10);
//
//        HashSet<RouteStop> routeStops = new HashSet<>();
//        routeStops.add(routeStop);
//
//        Route route3 = new Route();
//        route3.setId(1);
//        route3.setName("Name");
//        route3.setRouteStops(routeStops);
//        Schedule schedule = mock(Schedule.class);
//        when(schedule.getDepartureDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
//        when(schedule.getRoute()).thenReturn(route3);
//        doNothing().when(schedule).setBus(Mockito.<Bus>any());
//        doNothing().when(schedule).setDepartureDateTime(Mockito.<LocalDateTime>any());
//        doNothing().when(schedule).setId(Mockito.<Integer>any());
//        doNothing().when(schedule).setRoute(Mockito.<Route>any());
//        doNothing().when(schedule).setTickets(Mockito.<Set<Ticket>>any());
//        schedule.setBus(bus);
//        schedule.setDepartureDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
//        schedule.setId(1);
//        schedule.setRoute(route);
//        schedule.setTickets(new HashSet<>());
//        Optional<Schedule> ofResult = Optional.of(schedule);
//        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
//        when(scheduleRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
//        Optional<ScheduleInfo> ofResult2 = Optional.of(mock(ScheduleInfo.class));
//        when(scheduleRepository.findByIdScheduleInfo(Mockito.<Integer>any())).thenReturn(ofResult2);
//        ScheduleService scheduleService = new ScheduleService(scheduleRepository, new RouteStopDtoMapper());
//
//        TicketRepository ticketRepository = mock(TicketRepository.class);
//        when(ticketRepository.findByScheduleIdAndTicketStatus(Mockito.<Integer>any(), Mockito.<String>any()))
//                .thenReturn(new ArrayList<>());
//        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository = mock(BusStopRepository.class);
//        TicketService ticketService = new TicketService(ticketRepository, scheduleRepository2, busStopRepository,
//                new TicketMapper());
//
//        ScheduleRepository scheduleRepository3 = mock(ScheduleRepository.class);
//        ScheduleService scheduleService2 = new ScheduleService(scheduleRepository3, new RouteStopDtoMapper());
//
//        TicketRepository ticketRepository2 = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository4 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository2 = mock(BusStopRepository.class);
//        TicketService ticketService2 = new TicketService(ticketRepository2, scheduleRepository4, busStopRepository2,
//                new TicketMapper());
//
//        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
//                mock(RouteRepository.class));
//
//        TransportServiceMediator transportServiceMediator = new TransportServiceMediator(scheduleService2, ticketService2,
//                routeService, new BusService(mock(BusRepository.class)));
//
//        BusStopService busStopService = new BusStopService(mock(BusStopRepository.class));
//        ScheduleController scheduleController = new ScheduleController(transportServiceMediator, scheduleService,
//                busStopService, ticketService,
//                new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class), mock(RouteRepository.class)));
//        int scheduleId = 1;
//        ConcurrentModel model = new ConcurrentModel();
//
//        // Act
//        String actualBoardingManifestForSchedule = scheduleController.getBoardingManifestForSchedule(scheduleId, model);
//
//        // Assert
//        verify(schedule).getDepartureDateTime();
//        verify(schedule).getRoute();
//        verify(schedule).setBus(Mockito.<Bus>any());
//        verify(schedule).setDepartureDateTime(Mockito.<LocalDateTime>any());
//        verify(schedule).setId(Mockito.<Integer>any());
//        verify(schedule).setRoute(Mockito.<Route>any());
//        verify(schedule).setTickets(Mockito.<Set<Ticket>>any());
//        verify(scheduleRepository).findByIdScheduleInfo(Mockito.<Integer>any());
//        verify(ticketRepository).findByScheduleIdAndTicketStatus(Mockito.<Integer>any(), Mockito.<String>any());
//        verify(scheduleRepository).findById(Mockito.<Integer>any());
//        assertEquals("boarding_manifest", actualBoardingManifestForSchedule);
//    }
//
//    /**
//     * Method under test:
//     * {@link ScheduleController#getBoardingManifestForSchedule(Integer, Model)}
//     */
//    @Test
//    void testGetBoardingManifestForSchedule3() {
//        // Arrange
//        Bus bus = new Bus();
//        bus.setId(1);
//        bus.setSeatCapacity(1);
//        bus.setSerialNumber("42");
//
//        Route route = new Route();
//        route.setId(1);
//        route.setName("Name");
//        route.setRouteStops(new HashSet<>());
//
//        BusStop busStop = new BusStop();
//        busStop.setId(1);
//        busStop.setName("Name");
//
//        RouteStopId id = new RouteStopId();
//        id.setRouteId(1);
//        id.setStopId(1);
//
//        Route route2 = new Route();
//        route2.setId(1);
//        route2.setName("Name");
//        route2.setRouteStops(new HashSet<>());
//        RouteStop routeStop = mock(RouteStop.class);
//        doNothing().when(routeStop).setBusStop(Mockito.<BusStop>any());
//        doNothing().when(routeStop).setDepartureMinutesOffset(Mockito.<Integer>any());
//        doNothing().when(routeStop).setId(Mockito.<RouteStopId>any());
//        doNothing().when(routeStop).setRoute(Mockito.<Route>any());
//        doNothing().when(routeStop).setSequenceNumber(Mockito.<Integer>any());
//        routeStop.setBusStop(busStop);
//        routeStop.setDepartureMinutesOffset(1);
//        routeStop.setId(id);
//        routeStop.setRoute(route2);
//        routeStop.setSequenceNumber(10);
//
//        HashSet<RouteStop> routeStops = new HashSet<>();
//        routeStops.add(routeStop);
//
//        Route route3 = new Route();
//        route3.setId(1);
//        route3.setName("Name");
//        route3.setRouteStops(routeStops);
//        Schedule schedule = mock(Schedule.class);
//        when(schedule.getRoute()).thenReturn(route3);
//        doNothing().when(schedule).setBus(Mockito.<Bus>any());
//        doNothing().when(schedule).setDepartureDateTime(Mockito.<LocalDateTime>any());
//        doNothing().when(schedule).setId(Mockito.<Integer>any());
//        doNothing().when(schedule).setRoute(Mockito.<Route>any());
//        doNothing().when(schedule).setTickets(Mockito.<Set<Ticket>>any());
//        schedule.setBus(bus);
//        schedule.setDepartureDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
//        schedule.setId(1);
//        schedule.setRoute(route);
//        schedule.setTickets(new HashSet<>());
//        Optional<Schedule> ofResult = Optional.of(schedule);
//        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
//        when(scheduleRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
//        Optional<ScheduleInfo> ofResult2 = Optional.of(mock(ScheduleInfo.class));
//        when(scheduleRepository.findByIdScheduleInfo(Mockito.<Integer>any())).thenReturn(ofResult2);
//        RouteStopDtoMapper routeStopDtoMapper = mock(RouteStopDtoMapper.class);
//        when(routeStopDtoMapper.toSortedDtoList(Mockito.<List<RouteStop>>any())).thenReturn(new ArrayList<>());
//        ScheduleService scheduleService = new ScheduleService(scheduleRepository, routeStopDtoMapper);
//
//        TicketRepository ticketRepository = mock(TicketRepository.class);
//        when(ticketRepository.findByScheduleIdAndTicketStatus(Mockito.<Integer>any(), Mockito.<String>any()))
//                .thenReturn(new ArrayList<>());
//        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository = mock(BusStopRepository.class);
//        TicketService ticketService = new TicketService(ticketRepository, scheduleRepository2, busStopRepository,
//                new TicketMapper());
//
//        ScheduleRepository scheduleRepository3 = mock(ScheduleRepository.class);
//        ScheduleService scheduleService2 = new ScheduleService(scheduleRepository3, new RouteStopDtoMapper());
//
//        TicketRepository ticketRepository2 = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository4 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository2 = mock(BusStopRepository.class);
//        TicketService ticketService2 = new TicketService(ticketRepository2, scheduleRepository4, busStopRepository2,
//                new TicketMapper());
//
//        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
//                mock(RouteRepository.class));
//
//        TransportServiceMediator transportServiceMediator = new TransportServiceMediator(scheduleService2, ticketService2,
//                routeService, new BusService(mock(BusRepository.class)));
//
//        BusStopService busStopService = new BusStopService(mock(BusStopRepository.class));
//        ScheduleController scheduleController = new ScheduleController(transportServiceMediator, scheduleService,
//                busStopService, ticketService,
//                new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class), mock(RouteRepository.class)));
//        int scheduleId = 1;
//        ConcurrentModel model = new ConcurrentModel();
//
//        // Act
//        String actualBoardingManifestForSchedule = scheduleController.getBoardingManifestForSchedule(scheduleId, model);
//
//        // Assert
//        verify(routeStop).setBusStop(Mockito.<BusStop>any());
//        verify(routeStop).setDepartureMinutesOffset(Mockito.<Integer>any());
//        verify(routeStop).setId(Mockito.<RouteStopId>any());
//        verify(routeStop).setRoute(Mockito.<Route>any());
//        verify(routeStop).setSequenceNumber(Mockito.<Integer>any());
//        verify(schedule).getRoute();
//        verify(schedule).setBus(Mockito.<Bus>any());
//        verify(schedule).setDepartureDateTime(Mockito.<LocalDateTime>any());
//        verify(schedule).setId(Mockito.<Integer>any());
//        verify(schedule).setRoute(Mockito.<Route>any());
//        verify(schedule).setTickets(Mockito.<Set<Ticket>>any());
//        verify(routeStopDtoMapper).toSortedDtoList(Mockito.<List<RouteStop>>any());
//        verify(scheduleRepository).findByIdScheduleInfo(Mockito.<Integer>any());
//        verify(ticketRepository).findByScheduleIdAndTicketStatus(Mockito.<Integer>any(), Mockito.<String>any());
//        verify(scheduleRepository).findById(Mockito.<Integer>any());
//        assertEquals("boarding_manifest", actualBoardingManifestForSchedule);
//    }
//
//    /**
//     * Method under test:
//     * {@link ScheduleController#getBoardingManifestForSchedule(Integer, Model)}
//     */
//    @Test
//    void testGetBoardingManifestForSchedule4() {
//        // Arrange
//        ScheduleService scheduleService = mock(ScheduleService.class);
//        when(scheduleService.findByIdScheduleInfo(Mockito.<Integer>any())).thenReturn(mock(ScheduleInfo.class));
//        when(scheduleService.getRouteStopDtosWithArrivalTime(Mockito.<Integer>any())).thenReturn(new ArrayList<>());
//        TicketRepository ticketRepository = mock(TicketRepository.class);
//        when(ticketRepository.findByScheduleIdAndTicketStatus(Mockito.<Integer>any(), Mockito.<String>any()))
//                .thenReturn(new ArrayList<>());
//        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository = mock(BusStopRepository.class);
//        TicketService ticketService = new TicketService(ticketRepository, scheduleRepository, busStopRepository,
//                new TicketMapper());
//
//        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
//        ScheduleService scheduleService2 = new ScheduleService(scheduleRepository2, new RouteStopDtoMapper());
//
//        TicketRepository ticketRepository2 = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository3 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository2 = mock(BusStopRepository.class);
//        TicketService ticketService2 = new TicketService(ticketRepository2, scheduleRepository3, busStopRepository2,
//                new TicketMapper());
//
//        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
//                mock(RouteRepository.class));
//
//        TransportServiceMediator transportServiceMediator = new TransportServiceMediator(scheduleService2, ticketService2,
//                routeService, new BusService(mock(BusRepository.class)));
//
//        BusStopService busStopService = new BusStopService(mock(BusStopRepository.class));
//        ScheduleController scheduleController = new ScheduleController(transportServiceMediator, scheduleService,
//                busStopService, ticketService,
//                new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class), mock(RouteRepository.class)));
//        int scheduleId = 1;
//        ConcurrentModel model = new ConcurrentModel();
//
//        // Act
//        String actualBoardingManifestForSchedule = scheduleController.getBoardingManifestForSchedule(scheduleId, model);
//
//        // Assert
//        verify(ticketRepository).findByScheduleIdAndTicketStatus(Mockito.<Integer>any(), Mockito.<String>any());
//        verify(scheduleService).findByIdScheduleInfo(Mockito.<Integer>any());
//        verify(scheduleService).getRouteStopDtosWithArrivalTime(Mockito.<Integer>any());
//        assertEquals("boarding_manifest", actualBoardingManifestForSchedule);
//    }
//
//    /**
//     * Method under test: {@link ScheduleController#getAvailableSeats(Integer)}
//     */
//    @Test
//    void testGetAvailableSeats() {
//        // Arrange
//        Bus bus = new Bus();
//        bus.setId(1);
//        bus.setSeatCapacity(1);
//        bus.setSerialNumber("42");
//
//        Route route = new Route();
//        route.setId(1);
//        route.setName("Name");
//        route.setRouteStops(new HashSet<>());
//
//        Schedule schedule = new Schedule();
//        schedule.setBus(bus);
//        schedule.setDepartureDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
//        schedule.setId(1);
//        schedule.setRoute(route);
//        schedule.setTickets(new HashSet<>());
//        Optional<Schedule> ofResult = Optional.of(schedule);
//        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
//        when(scheduleRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
//        ScheduleService scheduleService = new ScheduleService(scheduleRepository, new RouteStopDtoMapper());
//
//        TicketRepository ticketRepository = mock(TicketRepository.class);
//        when(ticketRepository.findByScheduleIdAndTicketStatus(Mockito.<Integer>any(), Mockito.<String>any()))
//                .thenReturn(new ArrayList<>());
//        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository = mock(BusStopRepository.class);
//        TicketService ticketService = new TicketService(ticketRepository, scheduleRepository2, busStopRepository,
//                new TicketMapper());
//
//        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
//                mock(RouteRepository.class));
//
//        TransportServiceMediator transportServiceMediator = new TransportServiceMediator(scheduleService, ticketService,
//                routeService, new BusService(mock(BusRepository.class)));
//
//        ScheduleRepository scheduleRepository3 = mock(ScheduleRepository.class);
//        ScheduleService scheduleService2 = new ScheduleService(scheduleRepository3, new RouteStopDtoMapper());
//
//        BusStopService busStopService = new BusStopService(mock(BusStopRepository.class));
//        TicketRepository ticketRepository2 = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository4 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository2 = mock(BusStopRepository.class);
//        TicketService ticketService2 = new TicketService(ticketRepository2, scheduleRepository4, busStopRepository2,
//                new TicketMapper());
//
//        ScheduleController scheduleController = new ScheduleController(transportServiceMediator, scheduleService2,
//                busStopService, ticketService2,
//                new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class), mock(RouteRepository.class)));
//        int id = 1;
//
//        // Act
//        ResponseEntity<List<Integer>> actualAvailableSeats = scheduleController.getAvailableSeats(id);
//
//        // Assert
//        verify(ticketRepository).findByScheduleIdAndTicketStatus(Mockito.<Integer>any(), Mockito.<String>any());
//        verify(scheduleRepository).findById(Mockito.<Integer>any());
//        List<Integer> body = actualAvailableSeats.getBody();
//        assertEquals(1, body.size());
//        assertEquals(1, body.get(0));
//        assertEquals(200, actualAvailableSeats.getStatusCodeValue());
//        assertTrue(actualAvailableSeats.hasBody());
//        assertTrue(actualAvailableSeats.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link ScheduleController#getAvailableSeats(Integer)}
//     */
//    @Test
//    void testGetAvailableSeats2() {
//        // Arrange
//        ScheduleService scheduleService = mock(ScheduleService.class);
//        when(scheduleService.getAvailableSeats(Mockito.<Integer>any(), Mockito.<List<Ticket>>any()))
//                .thenReturn(new ArrayList<>());
//        TicketRepository ticketRepository = mock(TicketRepository.class);
//        when(ticketRepository.findByScheduleIdAndTicketStatus(Mockito.<Integer>any(), Mockito.<String>any()))
//                .thenReturn(new ArrayList<>());
//        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository = mock(BusStopRepository.class);
//        TicketService ticketService = new TicketService(ticketRepository, scheduleRepository, busStopRepository,
//                new TicketMapper());
//
//        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
//                mock(RouteRepository.class));
//
//        TransportServiceMediator transportServiceMediator = new TransportServiceMediator(scheduleService, ticketService,
//                routeService, new BusService(mock(BusRepository.class)));
//
//        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
//        ScheduleService scheduleService2 = new ScheduleService(scheduleRepository2, new RouteStopDtoMapper());
//
//        BusStopService busStopService = new BusStopService(mock(BusStopRepository.class));
//        TicketRepository ticketRepository2 = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository3 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository2 = mock(BusStopRepository.class);
//        TicketService ticketService2 = new TicketService(ticketRepository2, scheduleRepository3, busStopRepository2,
//                new TicketMapper());
//
//        ScheduleController scheduleController = new ScheduleController(transportServiceMediator, scheduleService2,
//                busStopService, ticketService2,
//                new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class), mock(RouteRepository.class)));
//        int id = 1;
//
//        // Act
//        ResponseEntity<List<Integer>> actualAvailableSeats = scheduleController.getAvailableSeats(id);
//
//        // Assert
//        verify(ticketRepository).findByScheduleIdAndTicketStatus(Mockito.<Integer>any(), Mockito.<String>any());
//        verify(scheduleService).getAvailableSeats(Mockito.<Integer>any(), Mockito.<List<Ticket>>any());
//        assertEquals(200, actualAvailableSeats.getStatusCodeValue());
//        assertTrue(actualAvailableSeats.hasBody());
//        assertTrue(actualAvailableSeats.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link ScheduleController#getAvailableSeats(Integer)}
//     */
//    @Test
//    void testGetAvailableSeats3() {
//        // Arrange
//        ScheduleService scheduleService = mock(ScheduleService.class);
//        when(scheduleService.getAvailableSeats(Mockito.<Integer>any(), Mockito.<List<Ticket>>any()))
//                .thenReturn(new ArrayList<>());
//        TicketService ticketService = mock(TicketService.class);
//        when(ticketService.findAllByScheduleAndTicketStatus(Mockito.<Integer>any(), Mockito.<String>any()))
//                .thenReturn(new ArrayList<>());
//        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
//                mock(RouteRepository.class));
//
//        TransportServiceMediator transportServiceMediator = new TransportServiceMediator(scheduleService, ticketService,
//                routeService, new BusService(mock(BusRepository.class)));
//
//        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
//        ScheduleService scheduleService2 = new ScheduleService(scheduleRepository, new RouteStopDtoMapper());
//
//        BusStopService busStopService = new BusStopService(mock(BusStopRepository.class));
//        TicketRepository ticketRepository = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository = mock(BusStopRepository.class);
//        TicketService ticketService2 = new TicketService(ticketRepository, scheduleRepository2, busStopRepository,
//                new TicketMapper());
//
//        ScheduleController scheduleController = new ScheduleController(transportServiceMediator, scheduleService2,
//                busStopService, ticketService2,
//                new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class), mock(RouteRepository.class)));
//        int id = 1;
//
//        // Act
//        ResponseEntity<List<Integer>> actualAvailableSeats = scheduleController.getAvailableSeats(id);
//
//        // Assert
//        verify(scheduleService).getAvailableSeats(Mockito.<Integer>any(), Mockito.<List<Ticket>>any());
//        verify(ticketService).findAllByScheduleAndTicketStatus(Mockito.<Integer>any(), Mockito.<String>any());
//        assertEquals(200, actualAvailableSeats.getStatusCodeValue());
//        assertTrue(actualAvailableSeats.hasBody());
//        assertTrue(actualAvailableSeats.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link ScheduleController#getAvailableSeats(Integer)}
//     */
//    @Test
//    void testGetAvailableSeats4() {
//        // Arrange
//        TransportServiceMediator transportServiceMediator = mock(TransportServiceMediator.class);
//        when(transportServiceMediator.getAvailableSeats(Mockito.<Integer>any())).thenReturn(new ArrayList<>());
//        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
//        ScheduleService scheduleService = new ScheduleService(scheduleRepository, new RouteStopDtoMapper());
//
//        BusStopService busStopService = new BusStopService(mock(BusStopRepository.class));
//        TicketRepository ticketRepository = mock(TicketRepository.class);
//        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
//        BusStopRepository busStopRepository = mock(BusStopRepository.class);
//        TicketService ticketService = new TicketService(ticketRepository, scheduleRepository2, busStopRepository,
//                new TicketMapper());
//
//        ScheduleController scheduleController = new ScheduleController(transportServiceMediator, scheduleService,
//                busStopService, ticketService,
//                new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class), mock(RouteRepository.class)));
//        int id = 1;
//
//        // Act
//        ResponseEntity<List<Integer>> actualAvailableSeats = scheduleController.getAvailableSeats(id);
//
//        // Assert
//        verify(transportServiceMediator).getAvailableSeats(Mockito.<Integer>any());
//        assertEquals(200, actualAvailableSeats.getStatusCodeValue());
//        assertTrue(actualAvailableSeats.hasBody());
//        assertTrue(actualAvailableSeats.getHeaders().isEmpty());
//    }
}
