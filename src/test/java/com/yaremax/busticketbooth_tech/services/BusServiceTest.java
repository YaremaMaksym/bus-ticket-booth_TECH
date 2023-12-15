package com.yaremax.busticketbooth_tech.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yaremax.busticketbooth_tech.data.Bus;
import com.yaremax.busticketbooth_tech.data.BusStop;
import com.yaremax.busticketbooth_tech.data.Route;
import com.yaremax.busticketbooth_tech.data.RouteStop;
import com.yaremax.busticketbooth_tech.data.RouteStopId;
import com.yaremax.busticketbooth_tech.exception.DuplicateResourceException;
import com.yaremax.busticketbooth_tech.exception.ResourceNotFoundException;
import com.yaremax.busticketbooth_tech.projections.BusInfo;
import com.yaremax.busticketbooth_tech.repositories.BusRepository;

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

@ContextConfiguration(classes = {BusService.class})
@ExtendWith(SpringExtension.class)
class BusServiceTest {
    @MockBean
    private BusRepository busRepository;

    @Autowired
    private BusService busService;

    /**
     * Method under test: {@link BusService#findAll()}
     */
    @Test
    void testFindAll() {
        // Arrange
        ArrayList<Bus> busList = new ArrayList<>();
        when(busRepository.findAll()).thenReturn(busList);

        // Act
        List<Bus> actualFindAllResult = busService.findAll();

        // Assert
        verify(busRepository).findAll();
        assertTrue(actualFindAllResult.isEmpty());
        assertSame(busList, actualFindAllResult);
    }

    /**
     * Method under test: {@link BusService#findAll()}
     */
    @Test
    void testFindAll2() {
        // Arrange
        when(busRepository.findAll()).thenThrow(new DuplicateResourceException("An error occurred"));

        // Act and Assert
        assertThrows(DuplicateResourceException.class, () -> busService.findAll());
        verify(busRepository).findAll();
    }

    /**
     * Method under test: {@link BusService#findById(Integer)}
     */
    @Test
    void testFindById() {
        // Arrange
        Bus bus = new Bus();
        bus.setId(1);
        bus.setSeatCapacity(1);
        bus.setSerialNumber("42");
        Optional<Bus> ofResult = Optional.of(bus);
        when(busRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        int busId = 1;

        // Act
        Bus actualFindByIdResult = busService.findById(busId);

        // Assert
        verify(busRepository).findById(Mockito.<Integer>any());
        assertSame(bus, actualFindByIdResult);
    }

    /**
     * Method under test: {@link BusService#findById(Integer)}
     */
    @Test
    void testFindById2() {
        // Arrange
        Optional<Bus> emptyResult = Optional.empty();
        when(busRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);
        int busId = 1;

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> busService.findById(busId));
        verify(busRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link BusService#findById(Integer)}
     */
    @Test
    void testFindById3() {
        // Arrange
        when(busRepository.findById(Mockito.<Integer>any())).thenThrow(new DuplicateResourceException("An error occurred"));
        int busId = 1;

        // Act and Assert
        assertThrows(DuplicateResourceException.class, () -> busService.findById(busId));
        verify(busRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link BusService#addBus(Bus)}
     */
    @Test
    void testAddBus() {
        // Arrange
        when(busRepository.existsBySerialNumber(Mockito.<String>any())).thenReturn(true);

        Bus bus = new Bus();
        bus.setId(1);
        bus.setSeatCapacity(1);
        bus.setSerialNumber("42");

        // Act and Assert
        assertThrows(DuplicateResourceException.class, () -> busService.addBus(bus));
        verify(busRepository).existsBySerialNumber(Mockito.<String>any());
    }

    /**
     * Method under test: {@link BusService#addBus(Bus)}
     */
    @Test
    void testAddBus2() {
        // Arrange
        Bus bus = new Bus();
        bus.setId(1);
        bus.setSeatCapacity(1);
        bus.setSerialNumber("42");
        when(busRepository.existsBySerialNumber(Mockito.<String>any())).thenReturn(false);
        when(busRepository.save(Mockito.<Bus>any())).thenReturn(bus);

        Bus bus2 = new Bus();
        bus2.setId(1);
        bus2.setSeatCapacity(1);
        bus2.setSerialNumber("42");

        // Act
        busService.addBus(bus2);

        // Assert that nothing has changed
        verify(busRepository).existsBySerialNumber(Mockito.<String>any());
        verify(busRepository).save(Mockito.<Bus>any());
        assertEquals("42", bus2.getSerialNumber());
        assertEquals(1, bus2.getId().intValue());
        assertEquals(1, bus2.getSeatCapacity().intValue());
    }

    /**
     * Method under test: {@link BusService#addBus(Bus)}
     */
    @Test
    void testAddBus3() {
        // Arrange
        when(busRepository.existsBySerialNumber(Mockito.<String>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));

        Bus bus = new Bus();
        bus.setId(1);
        bus.setSeatCapacity(1);
        bus.setSerialNumber("42");

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> busService.addBus(bus));
        verify(busRepository).existsBySerialNumber(Mockito.<String>any());
    }

    /**
     * Method under test: {@link BusService#deleteBus(Integer)}
     */
    @Test
    void testDeleteBus() {
        // Arrange
        Bus bus = new Bus();
        bus.setId(1);
        bus.setSeatCapacity(1);
        bus.setSerialNumber("42");
        Optional<Bus> ofResult = Optional.of(bus);
        doNothing().when(busRepository).delete(Mockito.<Bus>any());
        when(busRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        int busId = 1;

        // Act
        busService.deleteBus(busId);

        // Assert that nothing has changed
        verify(busRepository).delete(Mockito.<Bus>any());
        verify(busRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link BusService#deleteBus(Integer)}
     */
    @Test
    void testDeleteBus2() {
        // Arrange
        Bus bus = new Bus();
        bus.setId(1);
        bus.setSeatCapacity(1);
        bus.setSerialNumber("42");
        Optional<Bus> ofResult = Optional.of(bus);
        doThrow(new DuplicateResourceException("An error occurred")).when(busRepository).delete(Mockito.<Bus>any());
        when(busRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        int busId = 1;

        // Act and Assert
        assertThrows(DuplicateResourceException.class, () -> busService.deleteBus(busId));
        verify(busRepository).delete(Mockito.<Bus>any());
        verify(busRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link BusService#deleteBus(Integer)}
     */
    @Test
    void testDeleteBus3() {
        // Arrange
        Optional<Bus> emptyResult = Optional.empty();
        when(busRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);
        int busId = 1;

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> busService.deleteBus(busId));
        verify(busRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test:
     * {@link BusService#patchBus(Integer, String, Integer, Integer)}
     */
    @Test
    void testPatchBus() {
        // Arrange
        Bus bus = new Bus();
        bus.setId(1);
        bus.setSeatCapacity(1);
        bus.setSerialNumber("42");
        Optional<Bus> ofResult = Optional.of(bus);

        Bus bus2 = new Bus();
        bus2.setId(1);
        bus2.setSeatCapacity(1);
        bus2.setSerialNumber("42");
        when(busRepository.save(Mockito.<Bus>any())).thenReturn(bus2);
        when(busRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        int busId = 1;
        String newSerialNumber = "42";
        int newSeatCapacity = 1;
        int ticketsSold = 1;

        // Act
        busService.patchBus(busId, newSerialNumber, newSeatCapacity, ticketsSold);

        // Assert
        verify(busRepository).findById(Mockito.<Integer>any());
        verify(busRepository).save(Mockito.<Bus>any());
    }

    /**
     * Method under test:
     * {@link BusService#patchBus(Integer, String, Integer, Integer)}
     */
    @Test
    void testPatchBus2() {
        // Arrange
        Bus bus = new Bus();
        bus.setId(1);
        bus.setSeatCapacity(1);
        bus.setSerialNumber("42");
        Optional<Bus> ofResult = Optional.of(bus);
        when(busRepository.save(Mockito.<Bus>any())).thenThrow(new DuplicateResourceException("An error occurred"));
        when(busRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        int busId = 1;
        String newSerialNumber = "42";
        int newSeatCapacity = 1;
        int ticketsSold = 1;

        // Act and Assert
        assertThrows(DuplicateResourceException.class,
                () -> busService.patchBus(busId, newSerialNumber, newSeatCapacity, ticketsSold));
        verify(busRepository).findById(Mockito.<Integer>any());
        verify(busRepository).save(Mockito.<Bus>any());
    }

    /**
     * Method under test:
     * {@link BusService#patchBus(Integer, String, Integer, Integer)}
     */
    @Test
    void testPatchBus3() {
        // Arrange
        Bus bus = mock(Bus.class);
        when(bus.getSerialNumber()).thenReturn("foo");
        doNothing().when(bus).setId(Mockito.<Integer>any());
        doNothing().when(bus).setSeatCapacity(Mockito.<Integer>any());
        doNothing().when(bus).setSerialNumber(Mockito.<String>any());
        bus.setId(1);
        bus.setSeatCapacity(1);
        bus.setSerialNumber("42");
        Optional<Bus> ofResult = Optional.of(bus);
        when(busRepository.existsBySerialNumber(Mockito.<String>any())).thenReturn(true);
        when(busRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        int busId = 1;
        String newSerialNumber = "42";
        int newSeatCapacity = 1;
        int ticketsSold = 1;

        // Act and Assert
        assertThrows(DuplicateResourceException.class,
                () -> busService.patchBus(busId, newSerialNumber, newSeatCapacity, ticketsSold));
        verify(bus).getSerialNumber();
        verify(bus).setId(Mockito.<Integer>any());
        verify(bus).setSeatCapacity(Mockito.<Integer>any());
        verify(bus).setSerialNumber(Mockito.<String>any());
        verify(busRepository).existsBySerialNumber(Mockito.<String>any());
        verify(busRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test:
     * {@link BusService#patchBus(Integer, String, Integer, Integer)}
     */
    @Test
    void testPatchBus4() {
        // Arrange
        Bus bus = mock(Bus.class);
        when(bus.getSerialNumber()).thenReturn("foo");
        doNothing().when(bus).setId(Mockito.<Integer>any());
        doNothing().when(bus).setSeatCapacity(Mockito.<Integer>any());
        doNothing().when(bus).setSerialNumber(Mockito.<String>any());
        bus.setId(1);
        bus.setSeatCapacity(1);
        bus.setSerialNumber("42");
        Optional<Bus> ofResult = Optional.of(bus);

        Bus bus2 = new Bus();
        bus2.setId(1);
        bus2.setSeatCapacity(1);
        bus2.setSerialNumber("42");
        when(busRepository.existsBySerialNumber(Mockito.<String>any())).thenReturn(false);
        when(busRepository.save(Mockito.<Bus>any())).thenReturn(bus2);
        when(busRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        int busId = 1;
        String newSerialNumber = "42";
        int newSeatCapacity = 1;
        int ticketsSold = 1;

        // Act
        busService.patchBus(busId, newSerialNumber, newSeatCapacity, ticketsSold);

        // Assert that nothing has changed
        verify(bus).getSerialNumber();
        verify(bus).setId(Mockito.<Integer>any());
        verify(bus, atLeast(1)).setSeatCapacity(Mockito.<Integer>any());
        verify(bus, atLeast(1)).setSerialNumber(Mockito.<String>any());
        verify(busRepository).existsBySerialNumber(Mockito.<String>any());
        verify(busRepository).findById(Mockito.<Integer>any());
        verify(busRepository).save(Mockito.<Bus>any());
    }

    /**
     * Method under test:
     * {@link BusService#patchBus(Integer, String, Integer, Integer)}
     */
    @Test
    void testPatchBus5() {
        // Arrange
        Optional<Bus> emptyResult = Optional.empty();
        when(busRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);
        int busId = 1;
        String newSerialNumber = "42";
        int newSeatCapacity = 1;
        int ticketsSold = 1;

        // Act and Assert
        assertThrows(ResourceNotFoundException.class,
                () -> busService.patchBus(busId, newSerialNumber, newSeatCapacity, ticketsSold));
        verify(busRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test:
     * {@link BusService#patchBus(Integer, String, Integer, Integer)}
     */
    @Test
    void testPatchBus6() {
        // Arrange
        Bus bus = mock(Bus.class);
        when(bus.getSerialNumber()).thenReturn("foo");
        doNothing().when(bus).setId(Mockito.<Integer>any());
        doNothing().when(bus).setSeatCapacity(Mockito.<Integer>any());
        doNothing().when(bus).setSerialNumber(Mockito.<String>any());
        bus.setId(1);
        bus.setSeatCapacity(1);
        bus.setSerialNumber("42");
        Optional<Bus> ofResult = Optional.of(bus);
        when(busRepository.existsBySerialNumber(Mockito.<String>any()))
                .thenThrow(new DuplicateResourceException("An error occurred"));
        when(busRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        int busId = 1;
        String newSerialNumber = "42";
        int newSeatCapacity = 1;
        int ticketsSold = 1;

        // Act and Assert
        assertThrows(DuplicateResourceException.class,
                () -> busService.patchBus(busId, newSerialNumber, newSeatCapacity, ticketsSold));
        verify(bus).getSerialNumber();
        verify(bus).setId(Mockito.<Integer>any());
        verify(bus).setSeatCapacity(Mockito.<Integer>any());
        verify(bus).setSerialNumber(Mockito.<String>any());
        verify(busRepository).existsBySerialNumber(Mockito.<String>any());
        verify(busRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test:
     * {@link BusService#findAvailableBusInfosByTimeAndRoute(LocalDateTime, Set)}
     */
    @Test
    void testFindAvailableBusInfosByTimeAndRoute() {
        // Arrange
        ArrayList<BusInfo> busInfoList = new ArrayList<>();
        when(busRepository.findAvailableBusInfosByTime(Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any()))
                .thenReturn(busInfoList);
        LocalDateTime departureDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        HashSet<RouteStop> routeStops = new HashSet<>();

        // Act
        List<BusInfo> actualFindAvailableBusInfosByTimeAndRouteResult = busService
                .findAvailableBusInfosByTimeAndRoute(departureDateTime, routeStops);

        // Assert
        verify(busRepository).findAvailableBusInfosByTime(Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any());
        assertTrue(actualFindAvailableBusInfosByTimeAndRouteResult.isEmpty());
        assertSame(busInfoList, actualFindAvailableBusInfosByTimeAndRouteResult);
    }

    /**
     * Method under test:
     * {@link BusService#findAvailableBusInfosByTimeAndRoute(LocalDateTime, Set)}
     */
    @Test
    void testFindAvailableBusInfosByTimeAndRoute2() {
        // Arrange
        ArrayList<BusInfo> busInfoList = new ArrayList<>();
        when(busRepository.findAvailableBusInfosByTime(Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any()))
                .thenReturn(busInfoList);
        LocalDateTime departureDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();

        BusStop busStop = new BusStop();
        busStop.setId(1);
        busStop.setName("Name");

        RouteStopId id = new RouteStopId();
        id.setRouteId(1);
        id.setStopId(1);

        Route route = new Route();
        route.setId(1);
        route.setName("Name");
        route.setRouteStops(new HashSet<>());

        RouteStop routeStop = new RouteStop();
        routeStop.setBusStop(busStop);
        routeStop.setDepartureMinutesOffset(1);
        routeStop.setId(id);
        routeStop.setRoute(route);
        routeStop.setSequenceNumber(10);

        HashSet<RouteStop> routeStops = new HashSet<>();
        routeStops.add(routeStop);

        // Act
        List<BusInfo> actualFindAvailableBusInfosByTimeAndRouteResult = busService
                .findAvailableBusInfosByTimeAndRoute(departureDateTime, routeStops);

        // Assert
        verify(busRepository).findAvailableBusInfosByTime(Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any());
        assertTrue(actualFindAvailableBusInfosByTimeAndRouteResult.isEmpty());
        assertSame(busInfoList, actualFindAvailableBusInfosByTimeAndRouteResult);
    }

    /**
     * Method under test:
     * {@link BusService#findAvailableBusInfosByTimeAndRoute(LocalDateTime, Set)}
     */
    @Test
    void testFindAvailableBusInfosByTimeAndRoute3() {
        // Arrange
        ArrayList<BusInfo> busInfoList = new ArrayList<>();
        when(busRepository.findAvailableBusInfosByTime(Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any()))
                .thenReturn(busInfoList);
        LocalDateTime departureDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();

        BusStop busStop = new BusStop();
        busStop.setId(1);
        busStop.setName("Name");

        RouteStopId id = new RouteStopId();
        id.setRouteId(1);
        id.setStopId(1);

        Route route = new Route();
        route.setId(1);
        route.setName("Name");
        route.setRouteStops(new HashSet<>());

        RouteStop routeStop = new RouteStop();
        routeStop.setBusStop(busStop);
        routeStop.setDepartureMinutesOffset(1);
        routeStop.setId(id);
        routeStop.setRoute(route);
        routeStop.setSequenceNumber(10);

        BusStop busStop2 = new BusStop();
        busStop2.setId(2);
        busStop2.setName("42");

        RouteStopId id2 = new RouteStopId();
        id2.setRouteId(2);
        id2.setStopId(2);

        Route route2 = new Route();
        route2.setId(2);
        route2.setName("42");
        route2.setRouteStops(new HashSet<>());

        RouteStop routeStop2 = new RouteStop();
        routeStop2.setBusStop(busStop2);
        routeStop2.setDepartureMinutesOffset(0);
        routeStop2.setId(id2);
        routeStop2.setRoute(route2);
        routeStop2.setSequenceNumber(1);

        HashSet<RouteStop> routeStops = new HashSet<>();
        routeStops.add(routeStop2);
        routeStops.add(routeStop);

        // Act
        List<BusInfo> actualFindAvailableBusInfosByTimeAndRouteResult = busService
                .findAvailableBusInfosByTimeAndRoute(departureDateTime, routeStops);

        // Assert
        verify(busRepository).findAvailableBusInfosByTime(Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any());
        assertTrue(actualFindAvailableBusInfosByTimeAndRouteResult.isEmpty());
        assertSame(busInfoList, actualFindAvailableBusInfosByTimeAndRouteResult);
    }

    /**
     * Method under test:
     * {@link BusService#findAvailableBusInfosByTimeAndRoute(LocalDateTime, Set)}
     */
    @Test
    void testFindAvailableBusInfosByTimeAndRoute4() {
        // Arrange
        when(busRepository.findAvailableBusInfosByTime(Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any()))
                .thenThrow(new DuplicateResourceException("An error occurred"));
        LocalDateTime departureDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        HashSet<RouteStop> routeStops = new HashSet<>();

        // Act and Assert
        assertThrows(DuplicateResourceException.class,
                () -> busService.findAvailableBusInfosByTimeAndRoute(departureDateTime, routeStops));
        verify(busRepository).findAvailableBusInfosByTime(Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any());
    }
}
