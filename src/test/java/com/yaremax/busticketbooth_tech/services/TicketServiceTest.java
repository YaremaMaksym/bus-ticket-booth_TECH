package com.yaremax.busticketbooth_tech.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yaremax.busticketbooth_tech.data.Bus;
import com.yaremax.busticketbooth_tech.data.BusStop;
import com.yaremax.busticketbooth_tech.data.Route;
import com.yaremax.busticketbooth_tech.data.Schedule;
import com.yaremax.busticketbooth_tech.data.Ticket;
import com.yaremax.busticketbooth_tech.dto.TicketDto;
import com.yaremax.busticketbooth_tech.exception.ResourceNotFoundException;
import com.yaremax.busticketbooth_tech.mappers.TicketMapper;
import com.yaremax.busticketbooth_tech.projections.TicketInfo;
import com.yaremax.busticketbooth_tech.repositories.BusStopRepository;
import com.yaremax.busticketbooth_tech.repositories.ScheduleRepository;
import com.yaremax.busticketbooth_tech.repositories.TicketRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TicketService.class})
@ExtendWith(SpringExtension.class)
class TicketServiceTest {
    @MockBean
    private BusStopRepository busStopRepository;

    @MockBean
    private ScheduleRepository scheduleRepository;

    @MockBean
    private TicketMapper ticketMapper;

    @MockBean
    private TicketRepository ticketRepository;

    @Autowired
    private TicketService ticketService;

    /**
     * Method under test: {@link TicketService#findById(Integer)}
     */
    @Test
    void testFindById() {
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
        when(ticketRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        int ticketId = 1;

        // Act
        Ticket actualFindByIdResult = ticketService.findById(ticketId);

        // Assert
        verify(ticketRepository).findById(Mockito.<Integer>any());
        assertSame(ticket, actualFindByIdResult);
    }

    /**
     * Method under test: {@link TicketService#findById(Integer)}
     */
    @Test
    void testFindById2() {
        // Arrange
        Optional<Ticket> emptyResult = Optional.empty();
        when(ticketRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);
        int ticketId = 1;

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> ticketService.findById(ticketId));
        verify(ticketRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link TicketService#findById(Integer)}
     */
    @Test
    void testFindById3() {
        // Arrange
        when(ticketRepository.findById(Mockito.<Integer>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        int ticketId = 1;

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> ticketService.findById(ticketId));
        verify(ticketRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test:
     * {@link TicketService#findAllByScheduleAndTicketStatus(Integer, String)}
     */
    @Test
    void testFindAllByScheduleAndTicketStatus() {
        // Arrange
        ArrayList<Ticket> ticketList = new ArrayList<>();
        when(ticketRepository.findByScheduleIdAndTicketStatus(Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(ticketList);
        int scheduleId = 1;
        String status = "Status";

        // Act
        List<Ticket> actualFindAllByScheduleAndTicketStatusResult = ticketService
                .findAllByScheduleAndTicketStatus(scheduleId, status);

        // Assert
        verify(ticketRepository).findByScheduleIdAndTicketStatus(Mockito.<Integer>any(), Mockito.<String>any());
        assertTrue(actualFindAllByScheduleAndTicketStatusResult.isEmpty());
        assertSame(ticketList, actualFindAllByScheduleAndTicketStatusResult);
    }

    /**
     * Method under test:
     * {@link TicketService#findAllByScheduleAndTicketStatus(Integer, String)}
     */
    @Test
    void testFindAllByScheduleAndTicketStatus2() {
        // Arrange
        when(ticketRepository.findByScheduleIdAndTicketStatus(Mockito.<Integer>any(), Mockito.<String>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        int scheduleId = 1;
        String status = "Status";

        // Act and Assert
        assertThrows(ResourceNotFoundException.class,
                () -> ticketService.findAllByScheduleAndTicketStatus(scheduleId, status));
        verify(ticketRepository).findByScheduleIdAndTicketStatus(Mockito.<Integer>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link TicketService#addTicket(TicketDto)}
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
        when(scheduleRepository.getReferenceById(Mockito.<Integer>any())).thenReturn(schedule2);

        BusStop busStop2 = new BusStop();
        busStop2.setId(1);
        busStop2.setName("Name");
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
        when(ticketMapper.toEntity(Mockito.<TicketDto>any(), Mockito.<Schedule>any(), Mockito.<BusStop>any()))
                .thenReturn(ticket2);
        TicketDto ticketDto = new TicketDto(1, 1, 10, "Ticket Status");

        // Act
        Ticket actualAddTicketResult = ticketService.addTicket(ticketDto);

        // Assert
        verify(ticketMapper).toEntity(Mockito.<TicketDto>any(), Mockito.<Schedule>any(), Mockito.<BusStop>any());
        verify(busStopRepository).getReferenceById(Mockito.<Integer>any());
        verify(scheduleRepository).getReferenceById(Mockito.<Integer>any());
        verify(ticketRepository).save(Mockito.<Ticket>any());
        assertSame(ticket, actualAddTicketResult);
    }

    /**
     * Method under test: {@link TicketService#addTicket(TicketDto)}
     */
    @Test
    void testAddTicket2() {
        // Arrange
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
        when(scheduleRepository.getReferenceById(Mockito.<Integer>any())).thenReturn(schedule);

        BusStop busStop = new BusStop();
        busStop.setId(1);
        busStop.setName("Name");
        when(busStopRepository.getReferenceById(Mockito.<Integer>any())).thenReturn(busStop);
        when(ticketMapper.toEntity(Mockito.<TicketDto>any(), Mockito.<Schedule>any(), Mockito.<BusStop>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        TicketDto ticketDto = new TicketDto(1, 1, 10, "Ticket Status");

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> ticketService.addTicket(ticketDto));
        verify(ticketMapper).toEntity(Mockito.<TicketDto>any(), Mockito.<Schedule>any(), Mockito.<BusStop>any());
        verify(busStopRepository).getReferenceById(Mockito.<Integer>any());
        verify(scheduleRepository).getReferenceById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link TicketService#refundTicket(Integer)}
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
        when(ticketRepository.saveAndFlush(Mockito.<Ticket>any())).thenReturn(ticket2);
        when(ticketRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        int ticketId = 1;

        // Act
        ticketService.refundTicket(ticketId);

        // Assert
        verify(ticketRepository).saveAndFlush(Mockito.<Ticket>any());
        verify(ticketRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link TicketService#refundTicket(Integer)}
     */
    @Test
    void testRefundTicket2() {
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
        when(ticketRepository.saveAndFlush(Mockito.<Ticket>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        when(ticketRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        int ticketId = 1;

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> ticketService.refundTicket(ticketId));
        verify(ticketRepository).saveAndFlush(Mockito.<Ticket>any());
        verify(ticketRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test:
     * {@link TicketService#countTicketsForScheduleByBus(Integer)}
     */
    @Test
    void testCountTicketsForScheduleByBus() {
        // Arrange
        when(ticketRepository.countTicketsForScheduleByBus(Mockito.<Integer>any())).thenReturn(3);
        int busId = 1;

        // Act
        Integer actualCountTicketsForScheduleByBusResult = ticketService.countTicketsForScheduleByBus(busId);

        // Assert
        verify(ticketRepository).countTicketsForScheduleByBus(Mockito.<Integer>any());
        assertEquals(3, actualCountTicketsForScheduleByBusResult.intValue());
    }

    /**
     * Method under test:
     * {@link TicketService#countTicketsForScheduleByBus(Integer)}
     */
    @Test
    void testCountTicketsForScheduleByBus2() {
        // Arrange
        when(ticketRepository.countTicketsForScheduleByBus(Mockito.<Integer>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        int busId = 1;

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> ticketService.countTicketsForScheduleByBus(busId));
        verify(ticketRepository).countTicketsForScheduleByBus(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link TicketService#findAllTicketInfos()}
     */
    @Test
    void testFindAllTicketInfos() {
        // Arrange
        ArrayList<TicketInfo> ticketInfoList = new ArrayList<>();
        when(ticketRepository.findAllTicketInfos()).thenReturn(ticketInfoList);

        // Act
        List<TicketInfo> actualFindAllTicketInfosResult = ticketService.findAllTicketInfos();

        // Assert
        verify(ticketRepository).findAllTicketInfos();
        assertTrue(actualFindAllTicketInfosResult.isEmpty());
        assertSame(ticketInfoList, actualFindAllTicketInfosResult);
    }

    /**
     * Method under test: {@link TicketService#findAllTicketInfos()}
     */
    @Test
    void testFindAllTicketInfos2() {
        // Arrange
        when(ticketRepository.findAllTicketInfos()).thenThrow(new ResourceNotFoundException("An error occurred"));

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> ticketService.findAllTicketInfos());
        verify(ticketRepository).findAllTicketInfos();
    }

    /**
     * Method under test: {@link TicketService#findByIdTicketInfo(Integer)}
     */
    @Test
    void testFindByIdTicketInfo() {
        // Arrange
        when(ticketRepository.findByIdTicketInfo(Mockito.<Integer>any())).thenReturn(mock(TicketInfo.class));
        int ticketId = 1;

        // Act
        ticketService.findByIdTicketInfo(ticketId);

        // Assert
        verify(ticketRepository).findByIdTicketInfo(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link TicketService#findByIdTicketInfo(Integer)}
     */
    @Test
    void testFindByIdTicketInfo2() {
        // Arrange
        when(ticketRepository.findByIdTicketInfo(Mockito.<Integer>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        int ticketId = 1;

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> ticketService.findByIdTicketInfo(ticketId));
        verify(ticketRepository).findByIdTicketInfo(Mockito.<Integer>any());
    }
}
