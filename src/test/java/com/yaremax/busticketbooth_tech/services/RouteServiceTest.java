package com.yaremax.busticketbooth_tech.services;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yaremax.busticketbooth_tech.data.BusStop;
import com.yaremax.busticketbooth_tech.data.Route;
import com.yaremax.busticketbooth_tech.data.RouteStop;
import com.yaremax.busticketbooth_tech.data.RouteStopId;
import com.yaremax.busticketbooth_tech.dto.RouteDto;
import com.yaremax.busticketbooth_tech.dto.RouteStopDto;
import com.yaremax.busticketbooth_tech.exception.ResourceNotFoundException;
import com.yaremax.busticketbooth_tech.mappers.RouteStopDtoMapper;
import com.yaremax.busticketbooth_tech.repositories.BusStopRepository;
import com.yaremax.busticketbooth_tech.repositories.RouteRepository;

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
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class RouteServiceTest {
    @MockBean
    private BusStopRepository busStopRepository;

    @MockBean
    private RouteRepository routeRepository;

    @Autowired
    private RouteService routeService;

    @MockBean
    private RouteStopDtoMapper routeStopDtoMapper;

    @Nested
    class FindAllTests {
        @Test
        void findAll_shouldReturnEmptyList_whenNoRoutesExist() {
            // Arrange
            ArrayList<Route> routeList = new ArrayList<>();
            when(routeRepository.findAll()).thenReturn(routeList);

            // Act
            List<Route> actualFindAllResult = routeService.findAll();

            // Assert
            verify(routeRepository).findAll();
            assertTrue(actualFindAllResult.isEmpty());
            assertSame(routeList, actualFindAllResult);
        }

        @Test
        void findAll_shouldThrowResourceNotFoundException_whenRepositoryThrowsException() {
            // Arrange
            when(routeRepository.findAll()).thenThrow(new ResourceNotFoundException("An error occurred"));

            // Act and Assert
            assertThrows(ResourceNotFoundException.class, () -> routeService.findAll());
            verify(routeRepository).findAll();
        }
    }

    @Nested
    class FindByIdTests {
        @Test
        void findById_shouldReturnRoute_whenRouteExists() {
            // Arrange
            Route route = new Route();
            route.setId(1);
            route.setName("Name");
            route.setRouteStops(new HashSet<>());
            Optional<Route> ofResult = Optional.of(route);
            when(routeRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
            int id = 1;

            // Act
            Route actualFindByIdResult = routeService.findById(id);

            // Assert
            verify(routeRepository).findById(Mockito.<Integer>any());
            assertSame(route, actualFindByIdResult);
        }

        @Test
        void findById_shouldThrowResourceNotFoundException_whenRouteDoesNotExist() {
            // Arrange
            Optional<Route> emptyResult = Optional.empty();
            when(routeRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);
            int id = 1;

            // Act and Assert
            assertThrows(ResourceNotFoundException.class, () -> routeService.findById(id));
            verify(routeRepository).findById(Mockito.<Integer>any());
        }

        @Test
        void findById_shouldThrowResourceNotFoundException_whenRepositoryThrowsException() {
            // Arrange
            when(routeRepository.findById(Mockito.<Integer>any()))
                    .thenThrow(new ResourceNotFoundException("An error occurred"));
            int id = 1;

            // Act and Assert
            assertThrows(ResourceNotFoundException.class, () -> routeService.findById(id));
            verify(routeRepository).findById(Mockito.<Integer>any());
        }
    }

    @Nested
    class AddRouteTests {
        @Test
        void addRoute_shouldAddRoute_whenValidRouteDtoProvided() {
            // Arrange
            Route route = new Route();
            route.setId(1);
            route.setName("Name");
            route.setRouteStops(new HashSet<>());
            when(routeRepository.save(Mockito.<Route>any())).thenReturn(route);

            RouteDto routeDto = new RouteDto();
            routeDto.setId(1);
            routeDto.setName("Name");
            routeDto.setRouteStops(new ArrayList<>());

            // Act
            routeService.addRoute(routeDto);

            // Assert
            verify(routeRepository, atLeast(1)).save(Mockito.<Route>any());
        }

        @Test
        void addRoute_shouldAddRouteWithStops_whenValidRouteDtoWithStopsProvided() {
            // Arrange
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
            when(routeStopDtoMapper.toEntity(Mockito.<RouteStopDto>any(), Mockito.<Route>any(), Mockito.<BusStop>any()))
                    .thenReturn(routeStop);

            BusStop busStop2 = new BusStop();
            busStop2.setId(1);
            busStop2.setName("Name");
            when(busStopRepository.getReferenceById(Mockito.<Integer>any())).thenReturn(busStop2);

            Route route2 = new Route();
            route2.setId(1);
            route2.setName("Name");
            route2.setRouteStops(new HashSet<>());
            when(routeRepository.save(Mockito.<Route>any())).thenReturn(route2);

            BusStop busStop3 = new BusStop();
            busStop3.setId(1);
            busStop3.setName("Name");

            Route route3 = new Route();
            route3.setId(1);
            route3.setName("Name");
            route3.setRouteStops(new HashSet<>());

            RouteStopDto routeStopDto = new RouteStopDto();
            routeStopDto.setArrivalDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
            routeStopDto.setBusStop(busStop3);
            routeStopDto.setDepartureMinutesOffset(1);
            routeStopDto.setRoute(route3);
            routeStopDto.setRouteId(1);
            routeStopDto.setSequenceNumber(10);
            routeStopDto.setStopId(1);

            ArrayList<RouteStopDto> routeStops = new ArrayList<>();
            routeStops.add(routeStopDto);

            RouteDto routeDto = new RouteDto();
            routeDto.setId(1);
            routeDto.setName("Name");
            routeDto.setRouteStops(routeStops);

            // Act
            routeService.addRoute(routeDto);

            // Assert
            verify(routeStopDtoMapper).toEntity(Mockito.<RouteStopDto>any(), Mockito.<Route>any(), Mockito.<BusStop>any());
            verify(busStopRepository).getReferenceById(Mockito.<Integer>any());
            verify(routeRepository, atLeast(1)).save(Mockito.<Route>any());
        }

        @Test
        void addRoute_shouldThrowResourceNotFoundException_whenBusStopNotFound() {
            // Arrange
            when(busStopRepository.getReferenceById(Mockito.<Integer>any()))
                    .thenThrow(new ResourceNotFoundException("An error occurred"));

            Route route = new Route();
            route.setId(1);
            route.setName("Name");
            route.setRouteStops(new HashSet<>());
            when(routeRepository.save(Mockito.<Route>any())).thenReturn(route);

            BusStop busStop = new BusStop();
            busStop.setId(1);
            busStop.setName("Name");

            Route route2 = new Route();
            route2.setId(1);
            route2.setName("Name");
            route2.setRouteStops(new HashSet<>());

            RouteStopDto routeStopDto = new RouteStopDto();
            routeStopDto.setArrivalDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
            routeStopDto.setBusStop(busStop);
            routeStopDto.setDepartureMinutesOffset(1);
            routeStopDto.setRoute(route2);
            routeStopDto.setRouteId(1);
            routeStopDto.setSequenceNumber(10);
            routeStopDto.setStopId(1);

            ArrayList<RouteStopDto> routeStops = new ArrayList<>();
            routeStops.add(routeStopDto);

            RouteDto routeDto = new RouteDto();
            routeDto.setId(1);
            routeDto.setName("Name");
            routeDto.setRouteStops(routeStops);

            // Act and Assert
            assertThrows(ResourceNotFoundException.class, () -> routeService.addRoute(routeDto));
            verify(busStopRepository).getReferenceById(Mockito.<Integer>any());
            verify(routeRepository).save(Mockito.<Route>any());
        }
    }

    @Nested
    class DeleteRouteTests {
        @Test
        void deleteRoute_shouldDeleteRoute_whenRouteExists() {
            // Arrange
            Route route = new Route();
            route.setId(1);
            route.setName("Name");
            route.setRouteStops(new HashSet<>());
            Optional<Route> ofResult = Optional.of(route);
            doNothing().when(routeRepository).delete(Mockito.<Route>any());
            when(routeRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
            int id = 1;

            // Act
            routeService.deleteRoute(id);

            // Assert
            verify(routeRepository).delete(Mockito.<Route>any());
            verify(routeRepository).findById(Mockito.<Integer>any());
        }

        @Test
        void deleteRoute_shouldThrowResourceNotFoundException_whenDeletionFails() {
            // Arrange
            Route route = new Route();
            route.setId(1);
            route.setName("Name");
            route.setRouteStops(new HashSet<>());
            Optional<Route> ofResult = Optional.of(route);
            doThrow(new ResourceNotFoundException("An error occurred")).when(routeRepository).delete(Mockito.<Route>any());
            when(routeRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
            int id = 1;

            // Act and Assert
            assertThrows(ResourceNotFoundException.class, () -> routeService.deleteRoute(id));
            verify(routeRepository).delete(Mockito.<Route>any());
            verify(routeRepository).findById(Mockito.<Integer>any());
        }

        @Test
        void deleteRoute_shouldThrowResourceNotFoundException_whenRouteDoesNotExist() {
            // Arrange
            Optional<Route> emptyResult = Optional.empty();
            when(routeRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);
            int id = 1;

            // Act and Assert
            assertThrows(ResourceNotFoundException.class, () -> routeService.deleteRoute(id));
            verify(routeRepository).findById(Mockito.<Integer>any());
        }
    }
}
