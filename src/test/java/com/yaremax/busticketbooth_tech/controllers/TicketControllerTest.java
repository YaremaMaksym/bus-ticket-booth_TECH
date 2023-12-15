package com.yaremax.busticketbooth_tech.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yaremax.busticketbooth_tech.data.Bus;
import com.yaremax.busticketbooth_tech.data.BusStop;
import com.yaremax.busticketbooth_tech.data.Route;
import com.yaremax.busticketbooth_tech.data.Schedule;
import com.yaremax.busticketbooth_tech.data.Ticket;
import com.yaremax.busticketbooth_tech.dto.TicketDto;
import com.yaremax.busticketbooth_tech.mappers.RouteStopDtoMapper;
import com.yaremax.busticketbooth_tech.mappers.TicketMapper;
import com.yaremax.busticketbooth_tech.projections.TicketInfo;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

class TicketControllerTest {
    /**
     * Method under test: {@link TicketController#showTicketsPage(Model)}
     */
    @Test
    void testShowTicketsPage() { 
        // Arrange
        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.findAllTicketInfos()).thenReturn(new ArrayList<>());
        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
        BusStopRepository busStopRepository = mock(BusStopRepository.class);
        TicketService ticketService = new TicketService(ticketRepository, scheduleRepository, busStopRepository,
                new TicketMapper());

        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
        ScheduleService scheduleService = new ScheduleService(scheduleRepository2, new RouteStopDtoMapper());

        TicketRepository ticketRepository2 = mock(TicketRepository.class);
        ScheduleRepository scheduleRepository3 = mock(ScheduleRepository.class);
        BusStopRepository busStopRepository2 = mock(BusStopRepository.class);
        TicketService ticketService2 = new TicketService(ticketRepository2, scheduleRepository3, busStopRepository2,
                new TicketMapper());

        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
                mock(RouteRepository.class));

        TicketController ticketController = new TicketController(new TransportServiceMediator(scheduleService,
                ticketService2, routeService, new BusService(mock(BusRepository.class))), ticketService);
        ConcurrentModel model = new ConcurrentModel();

        // Act
        String actualShowTicketsPageResult = ticketController.showTicketsPage(model);

        // Assert
        verify(ticketRepository).findAllTicketInfos();
        assertEquals("tickets", actualShowTicketsPageResult);
    }

    /**
     * Method under test: {@link TicketController#showTicketsPage(Model)}
     */
    @Test
    void testShowTicketsPage2() { 
        // Arrange
        TicketService ticketService = mock(TicketService.class);
        when(ticketService.findAllTicketInfos()).thenReturn(new ArrayList<>());
        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
        ScheduleService scheduleService = new ScheduleService(scheduleRepository, new RouteStopDtoMapper());

        TicketRepository ticketRepository = mock(TicketRepository.class);
        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
        BusStopRepository busStopRepository = mock(BusStopRepository.class);
        TicketService ticketService2 = new TicketService(ticketRepository, scheduleRepository2, busStopRepository,
                new TicketMapper());

        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
                mock(RouteRepository.class));

        TicketController ticketController = new TicketController(new TransportServiceMediator(scheduleService,
                ticketService2, routeService, new BusService(mock(BusRepository.class))), ticketService);
        ConcurrentModel model = new ConcurrentModel();

        // Act
        String actualShowTicketsPageResult = ticketController.showTicketsPage(model);

        // Assert
        verify(ticketService).findAllTicketInfos();
        assertEquals("tickets", actualShowTicketsPageResult);
    }

    /**
     * Method under test: {@link TicketController#showTicketPage(Model, Integer)}
     */
    @Test
    void testShowTicketPage() { 
        // Arrange
        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.findByIdTicketInfo(Mockito.<Integer>any())).thenReturn(mock(TicketInfo.class));
        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
        BusStopRepository busStopRepository = mock(BusStopRepository.class);
        TicketService ticketService = new TicketService(ticketRepository, scheduleRepository, busStopRepository,
                new TicketMapper());

        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
        ScheduleService scheduleService = new ScheduleService(scheduleRepository2, new RouteStopDtoMapper());

        TicketRepository ticketRepository2 = mock(TicketRepository.class);
        ScheduleRepository scheduleRepository3 = mock(ScheduleRepository.class);
        BusStopRepository busStopRepository2 = mock(BusStopRepository.class);
        TicketService ticketService2 = new TicketService(ticketRepository2, scheduleRepository3, busStopRepository2,
                new TicketMapper());

        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
                mock(RouteRepository.class));

        TicketController ticketController = new TicketController(new TransportServiceMediator(scheduleService,
                ticketService2, routeService, new BusService(mock(BusRepository.class))), ticketService);
        ConcurrentModel model = new ConcurrentModel();
        int ticketId = 1;

        // Act
        String actualShowTicketPageResult = ticketController.showTicketPage(model, ticketId);

        // Assert
        verify(ticketRepository).findByIdTicketInfo(Mockito.<Integer>any());
        assertEquals("ticket_info", actualShowTicketPageResult);
    }

    /**
     * Method under test: {@link TicketController#showTicketPage(Model, Integer)}
     */
    @Test
    void testShowTicketPage2() { 
        // Arrange
        TicketService ticketService = mock(TicketService.class);
        when(ticketService.findByIdTicketInfo(Mockito.<Integer>any())).thenReturn(mock(TicketInfo.class));
        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
        ScheduleService scheduleService = new ScheduleService(scheduleRepository, new RouteStopDtoMapper());

        TicketRepository ticketRepository = mock(TicketRepository.class);
        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
        BusStopRepository busStopRepository = mock(BusStopRepository.class);
        TicketService ticketService2 = new TicketService(ticketRepository, scheduleRepository2, busStopRepository,
                new TicketMapper());

        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
                mock(RouteRepository.class));

        TicketController ticketController = new TicketController(new TransportServiceMediator(scheduleService,
                ticketService2, routeService, new BusService(mock(BusRepository.class))), ticketService);
        ConcurrentModel model = new ConcurrentModel();
        int ticketId = 1;

        // Act
        String actualShowTicketPageResult = ticketController.showTicketPage(model, ticketId);

        // Assert
        verify(ticketService).findByIdTicketInfo(Mockito.<Integer>any());
        assertEquals("ticket_info", actualShowTicketPageResult);
    }

    /**
     * Method under test: {@link TicketController#addTicket(TicketDto)}
     */
    @Test
    void testAddTicket() { 
        // Arrange
        BusStop busStop = new BusStop();
        busStop.setId(1);
        busStop.setName("Name");

        Bus bus = new Bus();
        bus.setId(1);
        bus.setSeatCapacity(1);
        bus.setSerialNumber("42");

        Route route = new Route();
        route.setId(1);
        route.setName("Name");
        route.setRouteStops(new HashSet<>());

        Schedule schedule = new Schedule();
        schedule.setBus(bus);
        schedule.setDepartureDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        schedule.setId(1);
        schedule.setRoute(route);
        schedule.setTickets(new HashSet<>());

        Ticket ticket = new Ticket();
        ticket.setBusStop(busStop);
        ticket.setId(1);
        ticket.setSchedule(schedule);
        ticket.setSeatNumber(10);
        ticket.setTicketStatus("Ticket Status");
        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.save(Mockito.<Ticket>any())).thenReturn(ticket);

        Bus bus2 = new Bus();
        bus2.setId(1);
        bus2.setSeatCapacity(1);
        bus2.setSerialNumber("42");

        Route route2 = new Route();
        route2.setId(1);
        route2.setName("Name");
        route2.setRouteStops(new HashSet<>());

        Schedule schedule2 = new Schedule();
        schedule2.setBus(bus2);
        schedule2.setDepartureDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        schedule2.setId(1);
        schedule2.setRoute(route2);
        schedule2.setTickets(new HashSet<>());
        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
        when(scheduleRepository.getReferenceById(Mockito.<Integer>any())).thenReturn(schedule2);

        BusStop busStop2 = new BusStop();
        busStop2.setId(1);
        busStop2.setName("Name");
        BusStopRepository busStopRepository = mock(BusStopRepository.class);
        when(busStopRepository.getReferenceById(Mockito.<Integer>any())).thenReturn(busStop2);
        TicketService ticketService = new TicketService(ticketRepository, scheduleRepository, busStopRepository,
                new TicketMapper());

        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
        ScheduleService scheduleService = new ScheduleService(scheduleRepository2, new RouteStopDtoMapper());

        TicketRepository ticketRepository2 = mock(TicketRepository.class);
        ScheduleRepository scheduleRepository3 = mock(ScheduleRepository.class);
        BusStopRepository busStopRepository2 = mock(BusStopRepository.class);
        TicketService ticketService2 = new TicketService(ticketRepository2, scheduleRepository3, busStopRepository2,
                new TicketMapper());

        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
                mock(RouteRepository.class));

        TicketController ticketController = new TicketController(new TransportServiceMediator(scheduleService,
                ticketService2, routeService, new BusService(mock(BusRepository.class))), ticketService);
        TicketDto ticketDto = new TicketDto(1, 1, 10, "Ticket Status");

        // Act
        String actualAddTicketResult = ticketController.addTicket(ticketDto);

        // Assert
        verify(busStopRepository).getReferenceById(Mockito.<Integer>any());
        verify(scheduleRepository).getReferenceById(Mockito.<Integer>any());
        verify(ticketRepository).save(Mockito.<Ticket>any());
        assertEquals("redirect:tickets/1", actualAddTicketResult);
    }

    /**
     * Method under test: {@link TicketController#addTicket(TicketDto)}
     */
    @Test
    void testAddTicket2() { 
        // Arrange
        BusStop busStop = new BusStop();
        busStop.setId(1);
        busStop.setName("Name");

        Bus bus = new Bus();
        bus.setId(1);
        bus.setSeatCapacity(1);
        bus.setSerialNumber("42");

        Route route = new Route();
        route.setId(1);
        route.setName("Name");
        route.setRouteStops(new HashSet<>());

        Schedule schedule = new Schedule();
        schedule.setBus(bus);
        schedule.setDepartureDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        schedule.setId(1);
        schedule.setRoute(route);
        schedule.setTickets(new HashSet<>());

        Ticket ticket = new Ticket();
        ticket.setBusStop(busStop);
        ticket.setId(1);
        ticket.setSchedule(schedule);
        ticket.setSeatNumber(10);
        ticket.setTicketStatus("Ticket Status");
        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.save(Mockito.<Ticket>any())).thenReturn(ticket);

        Bus bus2 = new Bus();
        bus2.setId(1);
        bus2.setSeatCapacity(1);
        bus2.setSerialNumber("42");

        Route route2 = new Route();
        route2.setId(1);
        route2.setName("Name");
        route2.setRouteStops(new HashSet<>());

        Schedule schedule2 = new Schedule();
        schedule2.setBus(bus2);
        schedule2.setDepartureDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        schedule2.setId(1);
        schedule2.setRoute(route2);
        schedule2.setTickets(new HashSet<>());
        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
        when(scheduleRepository.getReferenceById(Mockito.<Integer>any())).thenReturn(schedule2);

        BusStop busStop2 = new BusStop();
        busStop2.setId(1);
        busStop2.setName("Name");
        BusStopRepository busStopRepository = mock(BusStopRepository.class);
        when(busStopRepository.getReferenceById(Mockito.<Integer>any())).thenReturn(busStop2);

        BusStop busStop3 = new BusStop();
        busStop3.setId(1);
        busStop3.setName("Name");

        Bus bus3 = new Bus();
        bus3.setId(1);
        bus3.setSeatCapacity(1);
        bus3.setSerialNumber("42");

        Route route3 = new Route();
        route3.setId(1);
        route3.setName("Name");
        route3.setRouteStops(new HashSet<>());

        Schedule schedule3 = new Schedule();
        schedule3.setBus(bus3);
        schedule3.setDepartureDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        schedule3.setId(1);
        schedule3.setRoute(route3);
        schedule3.setTickets(new HashSet<>());

        Ticket ticket2 = new Ticket();
        ticket2.setBusStop(busStop3);
        ticket2.setId(1);
        ticket2.setSchedule(schedule3);
        ticket2.setSeatNumber(10);
        ticket2.setTicketStatus("Ticket Status");
        TicketMapper ticketMapper = mock(TicketMapper.class);
        when(ticketMapper.toEntity(Mockito.<TicketDto>any(), Mockito.<Schedule>any(), Mockito.<BusStop>any()))
                .thenReturn(ticket2);
        TicketService ticketService = new TicketService(ticketRepository, scheduleRepository, busStopRepository,
                ticketMapper);

        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
        ScheduleService scheduleService = new ScheduleService(scheduleRepository2, new RouteStopDtoMapper());

        TicketRepository ticketRepository2 = mock(TicketRepository.class);
        ScheduleRepository scheduleRepository3 = mock(ScheduleRepository.class);
        BusStopRepository busStopRepository2 = mock(BusStopRepository.class);
        TicketService ticketService2 = new TicketService(ticketRepository2, scheduleRepository3, busStopRepository2,
                new TicketMapper());

        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
                mock(RouteRepository.class));

        TicketController ticketController = new TicketController(new TransportServiceMediator(scheduleService,
                ticketService2, routeService, new BusService(mock(BusRepository.class))), ticketService);
        TicketDto ticketDto = new TicketDto(1, 1, 10, "Ticket Status");

        // Act
        String actualAddTicketResult = ticketController.addTicket(ticketDto);

        // Assert
        verify(ticketMapper).toEntity(Mockito.<TicketDto>any(), Mockito.<Schedule>any(), Mockito.<BusStop>any());
        verify(busStopRepository).getReferenceById(Mockito.<Integer>any());
        verify(scheduleRepository).getReferenceById(Mockito.<Integer>any());
        verify(ticketRepository).save(Mockito.<Ticket>any());
        assertEquals("redirect:tickets/1", actualAddTicketResult);
    }

    /**
     * Method under test:
     * {@link TicketController#showTicketRefundPage(Model, Integer)}
     */
    @Test
    void testShowTicketRefundPage() { 
        // Arrange
        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.findByScheduleIdAndTicketStatus(Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
        BusStopRepository busStopRepository = mock(BusStopRepository.class);
        TicketService ticketService = new TicketService(ticketRepository, scheduleRepository, busStopRepository,
                new TicketMapper());

        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
        ScheduleService scheduleService = new ScheduleService(scheduleRepository2, new RouteStopDtoMapper());

        TicketRepository ticketRepository2 = mock(TicketRepository.class);
        ScheduleRepository scheduleRepository3 = mock(ScheduleRepository.class);
        BusStopRepository busStopRepository2 = mock(BusStopRepository.class);
        TicketService ticketService2 = new TicketService(ticketRepository2, scheduleRepository3, busStopRepository2,
                new TicketMapper());

        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
                mock(RouteRepository.class));

        TicketController ticketController = new TicketController(new TransportServiceMediator(scheduleService,
                ticketService2, routeService, new BusService(mock(BusRepository.class))), ticketService);
        ConcurrentModel model = new ConcurrentModel();
        int scheduleId = 1;

        // Act
        String actualShowTicketRefundPageResult = ticketController.showTicketRefundPage(model, scheduleId);

        // Assert
        verify(ticketRepository).findByScheduleIdAndTicketStatus(Mockito.<Integer>any(), Mockito.<String>any());
        assertEquals("ticket_refund", actualShowTicketRefundPageResult);
    }

    /**
     * Method under test:
     * {@link TicketController#showTicketRefundPage(Model, Integer)}
     */
    @Test
    void testShowTicketRefundPage2() { 
        // Arrange
        TicketService ticketService = mock(TicketService.class);
        when(ticketService.findAllByScheduleAndTicketStatus(Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
        ScheduleService scheduleService = new ScheduleService(scheduleRepository, new RouteStopDtoMapper());

        TicketRepository ticketRepository = mock(TicketRepository.class);
        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
        BusStopRepository busStopRepository = mock(BusStopRepository.class);
        TicketService ticketService2 = new TicketService(ticketRepository, scheduleRepository2, busStopRepository,
                new TicketMapper());

        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
                mock(RouteRepository.class));

        TicketController ticketController = new TicketController(new TransportServiceMediator(scheduleService,
                ticketService2, routeService, new BusService(mock(BusRepository.class))), ticketService);
        ConcurrentModel model = new ConcurrentModel();
        int scheduleId = 1;

        // Act
        String actualShowTicketRefundPageResult = ticketController.showTicketRefundPage(model, scheduleId);

        // Assert
        verify(ticketService).findAllByScheduleAndTicketStatus(Mockito.<Integer>any(), Mockito.<String>any());
        assertEquals("ticket_refund", actualShowTicketRefundPageResult);
    }

    /**
     * Method under test: {@link TicketController#refundTicket(Integer)}
     */
    @Test
    void testRefundTicket() { 
        // Arrange
        BusStop busStop = new BusStop();
        busStop.setId(1);
        busStop.setName("Name");

        Bus bus = new Bus();
        bus.setId(1);
        bus.setSeatCapacity(1);
        bus.setSerialNumber("42");

        Route route = new Route();
        route.setId(1);
        route.setName("Name");
        route.setRouteStops(new HashSet<>());

        Schedule schedule = new Schedule();
        schedule.setBus(bus);
        schedule.setDepartureDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        schedule.setId(1);
        schedule.setRoute(route);
        schedule.setTickets(new HashSet<>());

        Ticket ticket = new Ticket();
        ticket.setBusStop(busStop);
        ticket.setId(1);
        ticket.setSchedule(schedule);
        ticket.setSeatNumber(10);
        ticket.setTicketStatus("Ticket Status");
        Optional<Ticket> ofResult = Optional.of(ticket);

        BusStop busStop2 = new BusStop();
        busStop2.setId(1);
        busStop2.setName("Name");

        Bus bus2 = new Bus();
        bus2.setId(1);
        bus2.setSeatCapacity(1);
        bus2.setSerialNumber("42");

        Route route2 = new Route();
        route2.setId(1);
        route2.setName("Name");
        route2.setRouteStops(new HashSet<>());

        Schedule schedule2 = new Schedule();
        schedule2.setBus(bus2);
        schedule2.setDepartureDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        schedule2.setId(1);
        schedule2.setRoute(route2);
        schedule2.setTickets(new HashSet<>());

        Ticket ticket2 = new Ticket();
        ticket2.setBusStop(busStop2);
        ticket2.setId(1);
        ticket2.setSchedule(schedule2);
        ticket2.setSeatNumber(10);
        ticket2.setTicketStatus("Ticket Status");
        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.saveAndFlush(Mockito.<Ticket>any())).thenReturn(ticket2);
        when(ticketRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
        BusStopRepository busStopRepository = mock(BusStopRepository.class);
        TicketService ticketService = new TicketService(ticketRepository, scheduleRepository, busStopRepository,
                new TicketMapper());

        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
        ScheduleService scheduleService = new ScheduleService(scheduleRepository2, new RouteStopDtoMapper());

        TicketRepository ticketRepository2 = mock(TicketRepository.class);
        ScheduleRepository scheduleRepository3 = mock(ScheduleRepository.class);
        BusStopRepository busStopRepository2 = mock(BusStopRepository.class);
        TicketService ticketService2 = new TicketService(ticketRepository2, scheduleRepository3, busStopRepository2,
                new TicketMapper());

        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
                mock(RouteRepository.class));

        TicketController ticketController = new TicketController(new TransportServiceMediator(scheduleService,
                ticketService2, routeService, new BusService(mock(BusRepository.class))), ticketService);
        int ticketId = 1;

        // Act
        String actualRefundTicketResult = ticketController.refundTicket(ticketId);

        // Assert
        verify(ticketRepository).saveAndFlush(Mockito.<Ticket>any());
        verify(ticketRepository).findById(Mockito.<Integer>any());
        assertEquals("redirect:/tickets", actualRefundTicketResult);
    }

    /**
     * Method under test: {@link TicketController#refundTicket(Integer)}
     */
    @Test
    void testRefundTicket2() { 
        // Arrange
        TicketService ticketService = mock(TicketService.class);
        doNothing().when(ticketService).refundTicket(Mockito.<Integer>any());
        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
        ScheduleService scheduleService = new ScheduleService(scheduleRepository, new RouteStopDtoMapper());

        TicketRepository ticketRepository = mock(TicketRepository.class);
        ScheduleRepository scheduleRepository2 = mock(ScheduleRepository.class);
        BusStopRepository busStopRepository = mock(BusStopRepository.class);
        TicketService ticketService2 = new TicketService(ticketRepository, scheduleRepository2, busStopRepository,
                new TicketMapper());

        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
                mock(RouteRepository.class));

        TicketController ticketController = new TicketController(new TransportServiceMediator(scheduleService,
                ticketService2, routeService, new BusService(mock(BusRepository.class))), ticketService);
        int ticketId = 1;

        // Act
        String actualRefundTicketResult = ticketController.refundTicket(ticketId);

        // Assert
        verify(ticketService).refundTicket(Mockito.<Integer>any());
        assertEquals("redirect:/tickets", actualRefundTicketResult);
    }
}
