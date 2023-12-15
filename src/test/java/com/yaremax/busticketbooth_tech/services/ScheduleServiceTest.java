package com.yaremax.busticketbooth_tech.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yaremax.busticketbooth_tech.data.Bus;
import com.yaremax.busticketbooth_tech.data.BusStop;
import com.yaremax.busticketbooth_tech.data.Route;
import com.yaremax.busticketbooth_tech.data.RouteStop;
import com.yaremax.busticketbooth_tech.data.Schedule;
import com.yaremax.busticketbooth_tech.data.Ticket;
import com.yaremax.busticketbooth_tech.dto.RouteStopDto;
import com.yaremax.busticketbooth_tech.exception.ResourceNotFoundException;
import com.yaremax.busticketbooth_tech.mappers.RouteStopDtoMapper;
import com.yaremax.busticketbooth_tech.projections.ScheduleInfo;
import com.yaremax.busticketbooth_tech.repositories.ScheduleRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ScheduleService.class})
@ExtendWith(SpringExtension.class)
class ScheduleServiceTest {
    @MockBean
    private RouteStopDtoMapper routeStopDtoMapper;

    @MockBean
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleService scheduleService;

    /**
     * Method under test: {@link ScheduleService#findById(Integer)}
     */
    @Test
    void testFindById() {
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
        Optional<Schedule> ofResult = Optional.of(schedule);
        when(scheduleRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        int scheduleId = 1;

        // Act
        Schedule actualFindByIdResult = scheduleService.findById(scheduleId);

        // Assert
        verify(scheduleRepository).findById(Mockito.<Integer>any());
        assertSame(schedule, actualFindByIdResult);
    }

    /**
     * Method under test: {@link ScheduleService#findById(Integer)}
     */
    @Test
    void testFindById2() {
        // Arrange
        Optional<Schedule> emptyResult = Optional.empty();
        when(scheduleRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);
        int scheduleId = 1;

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> scheduleService.findById(scheduleId));
        verify(scheduleRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link ScheduleService#findById(Integer)}
     */
    @Test
    void testFindById3() {
        // Arrange
        when(scheduleRepository.findById(Mockito.<Integer>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        int scheduleId = 1;

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> scheduleService.findById(scheduleId));
        verify(scheduleRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link ScheduleService#findByIdScheduleInfo(Integer)}
     */
    @Test
    void testFindByIdScheduleInfo() {
        // Arrange
        Optional<ScheduleInfo> ofResult = Optional.of(mock(ScheduleInfo.class));
        when(scheduleRepository.findByIdScheduleInfo(Mockito.<Integer>any())).thenReturn(ofResult);
        int scheduleId = 1;

        // Act
        scheduleService.findByIdScheduleInfo(scheduleId);

        // Assert
        verify(scheduleRepository).findByIdScheduleInfo(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link ScheduleService#findByIdScheduleInfo(Integer)}
     */
    @Test
    void testFindByIdScheduleInfo2() {
        // Arrange
        Optional<ScheduleInfo> emptyResult = Optional.empty();
        when(scheduleRepository.findByIdScheduleInfo(Mockito.<Integer>any())).thenReturn(emptyResult);
        int scheduleId = 1;

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> scheduleService.findByIdScheduleInfo(scheduleId));
        verify(scheduleRepository).findByIdScheduleInfo(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link ScheduleService#findByIdScheduleInfo(Integer)}
     */
    @Test
    void testFindByIdScheduleInfo3() {
        // Arrange
        when(scheduleRepository.findByIdScheduleInfo(Mockito.<Integer>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        int scheduleId = 1;

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> scheduleService.findByIdScheduleInfo(scheduleId));
        verify(scheduleRepository).findByIdScheduleInfo(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link ScheduleService#findAllPlannedScheduleInfo()}
     */
    @Test
    void testFindAllPlannedScheduleInfo() {
        // Arrange
        when(scheduleRepository.findAllScheduleInfo()).thenReturn(new ArrayList<>());

        // Act
        List<ScheduleInfo> actualFindAllPlannedScheduleInfoResult = scheduleService.findAllPlannedScheduleInfo();

        // Assert
        verify(scheduleRepository).findAllScheduleInfo();
        assertTrue(actualFindAllPlannedScheduleInfoResult.isEmpty());
    }

    /**
     * Method under test: {@link ScheduleService#findAllPlannedScheduleInfo()}
     */
    @Test
    void testFindAllPlannedScheduleInfo2() {
        // Arrange
        ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
        when(scheduleInfo.getDepartureDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());

        ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
        scheduleInfoList.add(scheduleInfo);
        when(scheduleRepository.findAllScheduleInfo()).thenReturn(scheduleInfoList);

        // Act
        List<ScheduleInfo> actualFindAllPlannedScheduleInfoResult = scheduleService.findAllPlannedScheduleInfo();

        // Assert
        verify(scheduleInfo).getDepartureDateTime();
        verify(scheduleRepository).findAllScheduleInfo();
        assertTrue(actualFindAllPlannedScheduleInfoResult.isEmpty());
    }

    /**
     * Method under test: {@link ScheduleService#findAllPlannedScheduleInfo()}
     */
    @Test
    void testFindAllPlannedScheduleInfo3() {
        // Arrange
        ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
        when(scheduleInfo.getDepartureDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
        when(scheduleInfo2.getDepartureDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());

        ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
        scheduleInfoList.add(scheduleInfo2);
        scheduleInfoList.add(scheduleInfo);
        when(scheduleRepository.findAllScheduleInfo()).thenReturn(scheduleInfoList);

        // Act
        List<ScheduleInfo> actualFindAllPlannedScheduleInfoResult = scheduleService.findAllPlannedScheduleInfo();

        // Assert
        verify(scheduleInfo2).getDepartureDateTime();
        verify(scheduleInfo).getDepartureDateTime();
        verify(scheduleRepository).findAllScheduleInfo();
        assertTrue(actualFindAllPlannedScheduleInfoResult.isEmpty());
    }

    /**
     * Method under test: {@link ScheduleService#findBestSchedulesToStop(Integer)}
     */
    @Test
    void testFindBestSchedulesToStop() {
        // Arrange
        when(scheduleRepository.findAllScheduleInfo()).thenReturn(new ArrayList<>());
        int busStopId = 1;

        // Act
        Optional<List<ScheduleInfo>> actualFindBestSchedulesToStopResult = scheduleService
                .findBestSchedulesToStop(busStopId);

        // Assert
        verify(scheduleRepository).findAllScheduleInfo();
        assertTrue(actualFindBestSchedulesToStopResult.isPresent());
    }

    /**
     * Method under test: {@link ScheduleService#findBestSchedulesToStop(Integer)}
     */
    @Test
    void testFindBestSchedulesToStop2() {
        // Arrange
        ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
        when(scheduleInfo.getDepartureDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());

        ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
        scheduleInfoList.add(scheduleInfo);
        when(scheduleRepository.findAllScheduleInfo()).thenReturn(scheduleInfoList);
        int busStopId = 1;

        // Act
        Optional<List<ScheduleInfo>> actualFindBestSchedulesToStopResult = scheduleService
                .findBestSchedulesToStop(busStopId);

        // Assert
        verify(scheduleInfo).getDepartureDateTime();
        verify(scheduleRepository).findAllScheduleInfo();
        assertTrue(actualFindBestSchedulesToStopResult.isPresent());
    }

    /**
     * Method under test: {@link ScheduleService#findBestSchedulesToStop(Integer)}
     */
    @Test
    void testFindBestSchedulesToStop3() {
        // Arrange
        ScheduleInfo scheduleInfo = mock(ScheduleInfo.class);
        when(scheduleInfo.getDepartureDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        ScheduleInfo scheduleInfo2 = mock(ScheduleInfo.class);
        when(scheduleInfo2.getDepartureDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());

        ArrayList<ScheduleInfo> scheduleInfoList = new ArrayList<>();
        scheduleInfoList.add(scheduleInfo2);
        scheduleInfoList.add(scheduleInfo);
        when(scheduleRepository.findAllScheduleInfo()).thenReturn(scheduleInfoList);
        int busStopId = 1;

        // Act
        Optional<List<ScheduleInfo>> actualFindBestSchedulesToStopResult = scheduleService
                .findBestSchedulesToStop(busStopId);

        // Assert
        verify(scheduleInfo2).getDepartureDateTime();
        verify(scheduleInfo).getDepartureDateTime();
        verify(scheduleRepository).findAllScheduleInfo();
        assertTrue(actualFindBestSchedulesToStopResult.isPresent());
    }

    /**
     * Method under test:
     * {@link ScheduleService#getRouteStopDtosWithArrivalTime(Integer)}
     */
    @Test
    void testGetRouteStopDtosWithArrivalTime() {
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
        Optional<Schedule> ofResult = Optional.of(schedule);
        when(scheduleRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        ArrayList<RouteStopDto> routeStopDtoList = new ArrayList<>();
        when(routeStopDtoMapper.toSortedDtoList(Mockito.<List<RouteStop>>any())).thenReturn(routeStopDtoList);
        int scheduleId = 1;

        // Act
        List<RouteStopDto> actualRouteStopDtosWithArrivalTime = scheduleService.getRouteStopDtosWithArrivalTime(scheduleId);

        // Assert
        verify(routeStopDtoMapper).toSortedDtoList(Mockito.<List<RouteStop>>any());
        verify(scheduleRepository).findById(Mockito.<Integer>any());
        assertTrue(actualRouteStopDtosWithArrivalTime.isEmpty());
        assertSame(routeStopDtoList, actualRouteStopDtosWithArrivalTime);
    }

    /**
     * Method under test:
     * {@link ScheduleService#getRouteStopDtosWithArrivalTime(Integer)}
     */
    @Test
    void testGetRouteStopDtosWithArrivalTime2() {
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
        Optional<Schedule> ofResult = Optional.of(schedule);
        when(scheduleRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        when(routeStopDtoMapper.toSortedDtoList(Mockito.<List<RouteStop>>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        int scheduleId = 1;

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> scheduleService.getRouteStopDtosWithArrivalTime(scheduleId));
        verify(routeStopDtoMapper).toSortedDtoList(Mockito.<List<RouteStop>>any());
        verify(scheduleRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test:
     * {@link ScheduleService#getRouteStopDtosWithArrivalTime(Integer)}
     */
    @Test
    void testGetRouteStopDtosWithArrivalTime3() {
        // Arrange
        Optional<Schedule> emptyResult = Optional.empty();
        when(scheduleRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);
        int scheduleId = 1;

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> scheduleService.getRouteStopDtosWithArrivalTime(scheduleId));
        verify(scheduleRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test:
     * {@link ScheduleService#getRouteStopDtosWithArrivalTime(Integer)}
     */
    @Test
    void testGetRouteStopDtosWithArrivalTime4() {
        // Arrange
        Bus bus = new Bus();
        bus.setId(1);
        bus.setSeatCapacity(1);
        bus.setSerialNumber("42");

        Route route = new Route();
        route.setId(1);
        route.setName("Name");
        route.setRouteStops(new HashSet<>());

        Route route2 = new Route();
        route2.setId(1);
        route2.setName("Name");
        route2.setRouteStops(new HashSet<>());
        Schedule schedule = mock(Schedule.class);
        when(schedule.getDepartureDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(schedule.getRoute()).thenReturn(route2);
        doNothing().when(schedule).setBus(Mockito.<Bus>any());
        doNothing().when(schedule).setDepartureDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(schedule).setId(Mockito.<Integer>any());
        doNothing().when(schedule).setRoute(Mockito.<Route>any());
        doNothing().when(schedule).setTickets(Mockito.<Set<Ticket>>any());
        schedule.setBus(bus);
        schedule.setDepartureDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        schedule.setId(1);
        schedule.setRoute(route);
        schedule.setTickets(new HashSet<>());
        Optional<Schedule> ofResult = Optional.of(schedule);
        when(scheduleRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        BusStop busStop = new BusStop();
        busStop.setId(1);
        busStop.setName("Name");

        Route route3 = new Route();
        route3.setId(1);
        route3.setName("Name");
        route3.setRouteStops(new HashSet<>());

        RouteStopDto routeStopDto = new RouteStopDto();
        routeStopDto.setArrivalDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        routeStopDto.setBusStop(busStop);
        routeStopDto.setDepartureMinutesOffset(1);
        routeStopDto.setRoute(route3);
        routeStopDto.setRouteId(1);
        routeStopDto.setSequenceNumber(10);
        routeStopDto.setStopId(1);

        ArrayList<RouteStopDto> routeStopDtoList = new ArrayList<>();
        routeStopDtoList.add(routeStopDto);
        when(routeStopDtoMapper.toSortedDtoList(Mockito.<List<RouteStop>>any())).thenReturn(routeStopDtoList);
        int scheduleId = 1;

        // Act
        List<RouteStopDto> actualRouteStopDtosWithArrivalTime = scheduleService.getRouteStopDtosWithArrivalTime(scheduleId);

        // Assert
        verify(schedule).getDepartureDateTime();
        verify(schedule).getRoute();
        verify(schedule).setBus(Mockito.<Bus>any());
        verify(schedule).setDepartureDateTime(Mockito.<LocalDateTime>any());
        verify(schedule).setId(Mockito.<Integer>any());
        verify(schedule).setRoute(Mockito.<Route>any());
        verify(schedule).setTickets(Mockito.<Set<Ticket>>any());
        verify(routeStopDtoMapper).toSortedDtoList(Mockito.<List<RouteStop>>any());
        verify(scheduleRepository).findById(Mockito.<Integer>any());
        assertEquals(1, actualRouteStopDtosWithArrivalTime.size());
        assertSame(routeStopDtoList, actualRouteStopDtosWithArrivalTime);
    }

    /**
     * Method under test: {@link ScheduleService#addSchedule(Schedule)}
     */
    @Test
    void testAddSchedule() {
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
        when(scheduleRepository.save(Mockito.<Schedule>any())).thenReturn(schedule);

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

        // Act
        scheduleService.addSchedule(schedule2);

        // Assert that nothing has changed
        verify(scheduleRepository).save(Mockito.<Schedule>any());
        assertEquals("00:00", schedule2.getDepartureDateTime().toLocalTime().toString());
        assertEquals(1, schedule2.getId().intValue());
        assertTrue(schedule2.getTickets().isEmpty());
        assertSame(bus2, schedule2.getBus());
        assertSame(route2, schedule2.getRoute());
    }

    /**
     * Method under test: {@link ScheduleService#addSchedule(Schedule)}
     */
    @Test
    void testAddSchedule2() {
        // Arrange
        when(scheduleRepository.save(Mockito.<Schedule>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));

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

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> scheduleService.addSchedule(schedule));
        verify(scheduleRepository).save(Mockito.<Schedule>any());
    }

    /**
     * Method under test: {@link ScheduleService#deleteSchedule(Integer)}
     */
    @Test
    void testDeleteSchedule() {
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
        Optional<Schedule> ofResult = Optional.of(schedule);
        doNothing().when(scheduleRepository).delete(Mockito.<Schedule>any());
        when(scheduleRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        int scheduleId = 1;

        // Act
        scheduleService.deleteSchedule(scheduleId);

        // Assert that nothing has changed
        verify(scheduleRepository).delete(Mockito.<Schedule>any());
        verify(scheduleRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link ScheduleService#deleteSchedule(Integer)}
     */
    @Test
    void testDeleteSchedule2() {
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
        Optional<Schedule> ofResult = Optional.of(schedule);
        doThrow(new ResourceNotFoundException("An error occurred")).when(scheduleRepository)
                .delete(Mockito.<Schedule>any());
        when(scheduleRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        int scheduleId = 1;

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> scheduleService.deleteSchedule(scheduleId));
        verify(scheduleRepository).delete(Mockito.<Schedule>any());
        verify(scheduleRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link ScheduleService#deleteSchedule(Integer)}
     */
    @Test
    void testDeleteSchedule3() {
        // Arrange
        Optional<Schedule> emptyResult = Optional.empty();
        when(scheduleRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);
        int scheduleId = 1;

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> scheduleService.deleteSchedule(scheduleId));
        verify(scheduleRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link ScheduleService#getAvailableSeats(Integer, List)}
     */
    @Test
    void testGetAvailableSeats() {
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
        Optional<Schedule> ofResult = Optional.of(schedule);
        when(scheduleRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        int scheduleId = 1;
        ArrayList<Ticket> bookedTickets = new ArrayList<>();

        // Act
        List<Integer> actualAvailableSeats = scheduleService.getAvailableSeats(scheduleId, bookedTickets);

        // Assert
        verify(scheduleRepository).findById(Mockito.<Integer>any());
        assertEquals(1, actualAvailableSeats.size());
        assertEquals(1, actualAvailableSeats.get(0));
    }

    /**
     * Method under test: {@link ScheduleService#getAvailableSeats(Integer, List)}
     */
    @Test
    void testGetAvailableSeats2() {
        // Arrange
        Optional<Schedule> emptyResult = Optional.empty();
        when(scheduleRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);
        int scheduleId = 1;
        ArrayList<Ticket> bookedTickets = new ArrayList<>();

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> scheduleService.getAvailableSeats(scheduleId, bookedTickets));
        verify(scheduleRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link ScheduleService#getAvailableSeats(Integer, List)}
     */
    @Test
    void testGetAvailableSeats3() {
        // Arrange
        Bus bus = new Bus();
        bus.setId(1);
        bus.setSeatCapacity(1);
        bus.setSerialNumber("42");

        Route route = new Route();
        route.setId(1);
        route.setName("Name");
        route.setRouteStops(new HashSet<>());

        Bus bus2 = new Bus();
        bus2.setId(1);
        bus2.setSeatCapacity(1);
        bus2.setSerialNumber("42");
        Schedule schedule = mock(Schedule.class);
        when(schedule.getBus()).thenReturn(bus2);
        doNothing().when(schedule).setBus(Mockito.<Bus>any());
        doNothing().when(schedule).setDepartureDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(schedule).setId(Mockito.<Integer>any());
        doNothing().when(schedule).setRoute(Mockito.<Route>any());
        doNothing().when(schedule).setTickets(Mockito.<Set<Ticket>>any());
        schedule.setBus(bus);
        schedule.setDepartureDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        schedule.setId(1);
        schedule.setRoute(route);
        schedule.setTickets(new HashSet<>());
        Optional<Schedule> ofResult = Optional.of(schedule);
        when(scheduleRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        int scheduleId = 1;

        BusStop busStop = new BusStop();
        busStop.setId(1);
        busStop.setName("Name");

        Bus bus3 = new Bus();
        bus3.setId(1);
        bus3.setSeatCapacity(1);
        bus3.setSerialNumber("42");

        Route route2 = new Route();
        route2.setId(1);
        route2.setName("Name");
        route2.setRouteStops(new HashSet<>());

        Schedule schedule2 = new Schedule();
        schedule2.setBus(bus3);
        schedule2.setDepartureDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        schedule2.setId(1);
        schedule2.setRoute(route2);
        schedule2.setTickets(new HashSet<>());

        Ticket ticket = new Ticket();
        ticket.setBusStop(busStop);
        ticket.setId(1);
        ticket.setSchedule(schedule2);
        ticket.setSeatNumber(10);
        ticket.setTicketStatus("Ticket Status");

        ArrayList<Ticket> bookedTickets = new ArrayList<>();
        bookedTickets.add(ticket);

        // Act
        List<Integer> actualAvailableSeats = scheduleService.getAvailableSeats(scheduleId, bookedTickets);

        // Assert
        verify(schedule).getBus();
        verify(schedule).setBus(Mockito.<Bus>any());
        verify(schedule).setDepartureDateTime(Mockito.<LocalDateTime>any());
        verify(schedule).setId(Mockito.<Integer>any());
        verify(schedule).setRoute(Mockito.<Route>any());
        verify(schedule).setTickets(Mockito.<Set<Ticket>>any());
        verify(scheduleRepository).findById(Mockito.<Integer>any());
        assertEquals(1, actualAvailableSeats.size());
        assertEquals(1, actualAvailableSeats.get(0));
    }

    /**
     * Method under test: {@link ScheduleService#getAvailableSeats(Integer, List)}
     */
    @Test
    void testGetAvailableSeats4() {
        // Arrange
        Bus bus = new Bus();
        bus.setId(1);
        bus.setSeatCapacity(1);
        bus.setSerialNumber("42");

        Route route = new Route();
        route.setId(1);
        route.setName("Name");
        route.setRouteStops(new HashSet<>());

        Bus bus2 = new Bus();
        bus2.setId(1);
        bus2.setSeatCapacity(1);
        bus2.setSerialNumber("42");
        Schedule schedule = mock(Schedule.class);
        when(schedule.getBus()).thenReturn(bus2);
        doNothing().when(schedule).setBus(Mockito.<Bus>any());
        doNothing().when(schedule).setDepartureDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(schedule).setId(Mockito.<Integer>any());
        doNothing().when(schedule).setRoute(Mockito.<Route>any());
        doNothing().when(schedule).setTickets(Mockito.<Set<Ticket>>any());
        schedule.setBus(bus);
        schedule.setDepartureDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        schedule.setId(1);
        schedule.setRoute(route);
        schedule.setTickets(new HashSet<>());
        Optional<Schedule> ofResult = Optional.of(schedule);
        when(scheduleRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        int scheduleId = 1;

        BusStop busStop = new BusStop();
        busStop.setId(1);
        busStop.setName("Name");

        Bus bus3 = new Bus();
        bus3.setId(1);
        bus3.setSeatCapacity(1);
        bus3.setSerialNumber("42");

        Route route2 = new Route();
        route2.setId(1);
        route2.setName("Name");
        route2.setRouteStops(new HashSet<>());

        Schedule schedule2 = new Schedule();
        schedule2.setBus(bus3);
        schedule2.setDepartureDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        schedule2.setId(1);
        schedule2.setRoute(route2);
        schedule2.setTickets(new HashSet<>());

        Ticket ticket = new Ticket();
        ticket.setBusStop(busStop);
        ticket.setId(1);
        ticket.setSchedule(schedule2);
        ticket.setSeatNumber(10);
        ticket.setTicketStatus("Ticket Status");

        BusStop busStop2 = new BusStop();
        busStop2.setId(2);
        busStop2.setName("42");

        Bus bus4 = new Bus();
        bus4.setId(2);
        bus4.setSeatCapacity(2);
        bus4.setSerialNumber("Serial Number");

        Route route3 = new Route();
        route3.setId(2);
        route3.setName("42");
        route3.setRouteStops(new HashSet<>());

        Schedule schedule3 = new Schedule();
        schedule3.setBus(bus4);
        schedule3.setDepartureDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        schedule3.setId(2);
        schedule3.setRoute(route3);
        schedule3.setTickets(new HashSet<>());

        Ticket ticket2 = new Ticket();
        ticket2.setBusStop(busStop2);
        ticket2.setId(2);
        ticket2.setSchedule(schedule3);
        ticket2.setSeatNumber(1);
        ticket2.setTicketStatus("42");

        ArrayList<Ticket> bookedTickets = new ArrayList<>();
        bookedTickets.add(ticket2);
        bookedTickets.add(ticket);

        // Act
        List<Integer> actualAvailableSeats = scheduleService.getAvailableSeats(scheduleId, bookedTickets);

        // Assert
        verify(schedule).getBus();
        verify(schedule).setBus(Mockito.<Bus>any());
        verify(schedule).setDepartureDateTime(Mockito.<LocalDateTime>any());
        verify(schedule).setId(Mockito.<Integer>any());
        verify(schedule).setRoute(Mockito.<Route>any());
        verify(schedule).setTickets(Mockito.<Set<Ticket>>any());
        verify(scheduleRepository).findById(Mockito.<Integer>any());
        assertTrue(actualAvailableSeats.isEmpty());
    }
}
