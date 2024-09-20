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

    @Nested
    class FindByIdTests {

        @Test
        void findById_shouldReturnTicket_whenTicketExists() {
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

        @Test
        void findById_shouldThrowException_whenTicketNotFound() {
            // Arrange
            Optional<Ticket> emptyResult = Optional.empty();
            when(ticketRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);
            int ticketId = 1;

            // Act and Assert
            assertThrows(ResourceNotFoundException.class, () -> ticketService.findById(ticketId));
            verify(ticketRepository).findById(Mockito.<Integer>any());
        }

        @Test
        void findById_shouldThrowException_whenRepositoryThrowsError() {
            // Arrange
            when(ticketRepository.findById(Mockito.<Integer>any()))
                    .thenThrow(new ResourceNotFoundException("An error occurred"));
            int ticketId = 1;

            // Act and Assert
            assertThrows(ResourceNotFoundException.class, () -> ticketService.findById(ticketId));
            verify(ticketRepository).findById(Mockito.<Integer>any());
        }
    }

    @Nested
    class FindAllByScheduleAndTicketStatusTests {

        @Test
        void findAllByScheduleAndTicketStatus_shouldReturnEmptyList_whenNoTicketsFound() {
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

        @Test
        void findAllByScheduleAndTicketStatus_shouldThrowException_whenRepositoryThrowsError() {
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
    }

    @Nested
    class AddTicketTests {

        @Test
        void addTicket_shouldAddTicketSuccessfully_whenAllReferencesAreValid() {
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

        @Test
        void addTicket_shouldThrowException_whenMapperThrowsError() {
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
    }

    @Nested
    class RefundTicketTests {

        @Test
        void refundTicket_shouldRefundTicket_whenTicketExists() {
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
            when(ticketRepository.saveAndFlush(Mockito.<Ticket>any())).thenReturn(ticket);
            when(ticketRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
            int ticketId = 1;

            // Act
            ticketService.refundTicket(ticketId);

            // Assert
            verify(ticketRepository).saveAndFlush(Mockito.<Ticket>any());
            verify(ticketRepository).findById(Mockito.<Integer>any());
        }

        @Test
        void refundTicket_shouldThrowException_whenRepositoryThrowsError() {
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
    }

    @Nested
    class CountTicketsForScheduleByBusTests {

        @Test
        void countTicketsForScheduleByBus_shouldReturnCount_whenBusExists() {
            // Arrange
            when(ticketRepository.countTicketsForScheduleByBus(Mockito.<Integer>any())).thenReturn(3);
            int busId = 1;

            // Act
            Integer actualCountTicketsForScheduleByBusResult = ticketService.countTicketsForScheduleByBus(busId);

            // Assert
            verify(ticketRepository).countTicketsForScheduleByBus(Mockito.<Integer>any());
            assertEquals(3, actualCountTicketsForScheduleByBusResult.intValue());
        }

        @Test
        void countTicketsForScheduleByBus_shouldThrowException_whenRepositoryThrowsError() {
            // Arrange
            when(ticketRepository.countTicketsForScheduleByBus(Mockito.<Integer>any()))
                    .thenThrow(new ResourceNotFoundException("An error occurred"));
            int busId = 1;

            // Act and Assert
            assertThrows(ResourceNotFoundException.class, () -> ticketService.countTicketsForScheduleByBus(busId));
            verify(ticketRepository).countTicketsForScheduleByBus(Mockito.<Integer>any());
        }
    }

    @Nested
    class FindAllTicketInfosTests {

        @Test
        void findAllTicketInfos_shouldReturnEmptyList_whenNoTicketsFound() {
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

        @Test
        void findAllTicketInfos_shouldThrowException_whenRepositoryThrowsError() {
            // Arrange
            when(ticketRepository.findAllTicketInfos()).thenThrow(new ResourceNotFoundException("An error occurred"));

            // Act and Assert
            assertThrows(ResourceNotFoundException.class, () -> ticketService.findAllTicketInfos());
            verify(ticketRepository).findAllTicketInfos();
        }
    }

    @Nested
    class FindByIdTicketInfoTests {

        @Test
        void findByIdTicketInfo_shouldReturnTicketInfo_whenTicketInfoExists() {
            // Arrange
            when(ticketRepository.findByIdTicketInfo(Mockito.<Integer>any())).thenReturn(mock(TicketInfo.class));
            int ticketId = 1;

            // Act
            ticketService.findByIdTicketInfo(ticketId);

            // Assert
            verify(ticketRepository).findByIdTicketInfo(Mockito.<Integer>any());
        }

        @Test
        void findByIdTicketInfo_shouldThrowException_whenRepositoryThrowsError() {
            // Arrange
            when(ticketRepository.findByIdTicketInfo(Mockito.<Integer>any()))
                    .thenThrow(new ResourceNotFoundException("An error occurred"));
            int ticketId = 1;

            // Act and Assert
            assertThrows(ResourceNotFoundException.class, () -> ticketService.findByIdTicketInfo(ticketId));
            verify(ticketRepository).findByIdTicketInfo(Mockito.<Integer>any());
        }
    }
}
