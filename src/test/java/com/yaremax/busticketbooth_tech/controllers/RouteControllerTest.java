package com.yaremax.busticketbooth_tech.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yaremax.busticketbooth_tech.data.Route;
import com.yaremax.busticketbooth_tech.dto.RouteDto;
import com.yaremax.busticketbooth_tech.mappers.RouteStopDtoMapper;
import com.yaremax.busticketbooth_tech.projections.BusStopInfo;
import com.yaremax.busticketbooth_tech.projections.RouteStopInfo;
import com.yaremax.busticketbooth_tech.repositories.BusStopRepository;
import com.yaremax.busticketbooth_tech.repositories.RouteRepository;
import com.yaremax.busticketbooth_tech.repositories.RouteStopRepository;
import com.yaremax.busticketbooth_tech.services.BusStopService;
import com.yaremax.busticketbooth_tech.services.RouteService;
import com.yaremax.busticketbooth_tech.services.RouteStopService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

class RouteControllerTest {
    /**
     * Method under test: {@link RouteController#getRoutes(Model)}
     */
    @Test
    void testGetRoutes() { 
        // Arrange
        BusStopRepository busStopRepository = mock(BusStopRepository.class);
        when(busStopRepository.findAll()).thenReturn(new ArrayList<>());
        BusStopService busStopService = new BusStopService(busStopRepository);
        RouteRepository routeRepository = mock(RouteRepository.class);
        when(routeRepository.findAll()).thenReturn(new ArrayList<>());
        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
                routeRepository);

        RouteController routeController = new RouteController(new RouteStopService(mock(RouteStopRepository.class)),
                busStopService, routeService);
        ConcurrentModel model = new ConcurrentModel();

        // Act
        String actualRoutes = routeController.getRoutes(model);

        // Assert
        verify(busStopRepository).findAll();
        verify(routeRepository).findAll();
        assertEquals("routes", actualRoutes);
    }

    /**
     * Method under test: {@link RouteController#getRoutes(Model)}
     */
    @Test
    void testGetRoutes2() { 
        // Arrange
        BusStopRepository busStopRepository = mock(BusStopRepository.class);
        when(busStopRepository.findAll()).thenReturn(new ArrayList<>());
        BusStopService busStopService = new BusStopService(busStopRepository);
        RouteService routeService = mock(RouteService.class);
        when(routeService.findAll()).thenReturn(new ArrayList<>());
        RouteController routeController = new RouteController(new RouteStopService(mock(RouteStopRepository.class)),
                busStopService, routeService);
        ConcurrentModel model = new ConcurrentModel();

        // Act
        String actualRoutes = routeController.getRoutes(model);

        // Assert
        verify(routeService).findAll();
        verify(busStopRepository).findAll();
        assertEquals("routes", actualRoutes);
    }

    /**
     * Method under test: {@link RouteController#addRoute(RouteDto)}
     */
    @Test
    void testAddRoute() { 
        // Arrange
        Route route = new Route();
        route.setId(1);
        route.setName("Name");
        route.setRouteStops(new HashSet<>());
        RouteRepository routeRepository = mock(RouteRepository.class);
        when(routeRepository.save(Mockito.<Route>any())).thenReturn(route);
        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
                routeRepository);

        RouteStopService routeStopService = new RouteStopService(mock(RouteStopRepository.class));
        RouteController routeController = new RouteController(routeStopService,
                new BusStopService(mock(BusStopRepository.class)), routeService);

        RouteDto routeDto = new RouteDto();
        routeDto.setId(1);
        routeDto.setName("Name");
        routeDto.setRouteStops(new ArrayList<>());

        // Act
        String actualAddRouteResult = routeController.addRoute(routeDto);

        // Assert
        verify(routeRepository, atLeast(1)).save(Mockito.<Route>any());
        assertEquals("redirect:/routes", actualAddRouteResult);
    }

    /**
     * Method under test: {@link RouteController#addRoute(RouteDto)}
     */
    @Test
    void testAddRoute2() { 
        // Arrange
        RouteService routeService = mock(RouteService.class);
        doNothing().when(routeService).addRoute(Mockito.<RouteDto>any());
        RouteStopService routeStopService = new RouteStopService(mock(RouteStopRepository.class));
        RouteController routeController = new RouteController(routeStopService,
                new BusStopService(mock(BusStopRepository.class)), routeService);

        RouteDto routeDto = new RouteDto();
        routeDto.setId(1);
        routeDto.setName("Name");
        routeDto.setRouteStops(new ArrayList<>());

        // Act
        String actualAddRouteResult = routeController.addRoute(routeDto);

        // Assert
        verify(routeService).addRoute(Mockito.<RouteDto>any());
        assertEquals("redirect:/routes", actualAddRouteResult);
    }

    /**
     * Method under test: {@link RouteController#deleteRoute(Integer)}
     */
    @Test
    void testDeleteRoute() { 
        // Arrange
        Route route = new Route();
        route.setId(1);
        route.setName("Name");
        route.setRouteStops(new HashSet<>());
        Optional<Route> ofResult = Optional.of(route);
        RouteRepository routeRepository = mock(RouteRepository.class);
        doNothing().when(routeRepository).delete(Mockito.<Route>any());
        when(routeRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        RouteService routeService = new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class),
                routeRepository);

        RouteStopService routeStopService = new RouteStopService(mock(RouteStopRepository.class));
        RouteController routeController = new RouteController(routeStopService,
                new BusStopService(mock(BusStopRepository.class)), routeService);
        int id = 1;

        // Act
        String actualDeleteRouteResult = routeController.deleteRoute(id);

        // Assert
        verify(routeRepository).delete(Mockito.<Route>any());
        verify(routeRepository).findById(Mockito.<Integer>any());
        assertEquals("redirect:/routes", actualDeleteRouteResult);
    }

    /**
     * Method under test: {@link RouteController#deleteRoute(Integer)}
     */
    @Test
    void testDeleteRoute2() { 
        // Arrange
        RouteService routeService = mock(RouteService.class);
        doNothing().when(routeService).deleteRoute(Mockito.<Integer>any());
        RouteStopService routeStopService = new RouteStopService(mock(RouteStopRepository.class));
        RouteController routeController = new RouteController(routeStopService,
                new BusStopService(mock(BusStopRepository.class)), routeService);
        int id = 1;

        // Act
        String actualDeleteRouteResult = routeController.deleteRoute(id);

        // Assert
        verify(routeService).deleteRoute(Mockito.<Integer>any());
        assertEquals("redirect:/routes", actualDeleteRouteResult);
    }

    /**
     * Method under test: {@link RouteController#getStopsForRoute(Integer)}
     */
    @Test
    void testGetStopsForRoute() { 
        // Arrange
        RouteStopRepository routeStopRepository = mock(RouteStopRepository.class);
        when(routeStopRepository.findBusStopInfosByRouteId(Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        RouteStopService routeStopService = new RouteStopService(routeStopRepository);
        BusStopService busStopService = new BusStopService(mock(BusStopRepository.class));
        RouteController routeController = new RouteController(routeStopService, busStopService,
                new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class), mock(RouteRepository.class)));
        int id = 1;

        // Act
        ResponseEntity<List<BusStopInfo>> actualStopsForRoute = routeController.getStopsForRoute(id);

        // Assert
        verify(routeStopRepository).findBusStopInfosByRouteId(Mockito.<Integer>any());
        assertEquals(200, actualStopsForRoute.getStatusCodeValue());
        assertTrue(actualStopsForRoute.hasBody());
        assertTrue(actualStopsForRoute.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link RouteController#getStopsForRoute(Integer)}
     */
    @Test
    void testGetStopsForRoute2() { 
        // Arrange
        RouteStopService routeStopService = mock(RouteStopService.class);
        when(routeStopService.getStopsForRoute(Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        BusStopService busStopService = new BusStopService(mock(BusStopRepository.class));
        RouteController routeController = new RouteController(routeStopService, busStopService,
                new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class), mock(RouteRepository.class)));
        int id = 1;

        // Act
        ResponseEntity<List<BusStopInfo>> actualStopsForRoute = routeController.getStopsForRoute(id);

        // Assert
        verify(routeStopService).getStopsForRoute(Mockito.<Integer>any());
        assertEquals(200, actualStopsForRoute.getStatusCodeValue());
        assertTrue(actualStopsForRoute.hasBody());
        assertTrue(actualStopsForRoute.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link RouteController#getRouteStopsByRouteId(Integer)}
     */
    @Test
    void testGetRouteStopsByRouteId() { 
        // Arrange
        RouteStopRepository routeStopRepository = mock(RouteStopRepository.class);
        when(routeStopRepository.findRouteStopsByRouteId(Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        RouteStopService routeStopService = new RouteStopService(routeStopRepository);
        BusStopService busStopService = new BusStopService(mock(BusStopRepository.class));
        RouteController routeController = new RouteController(routeStopService, busStopService,
                new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class), mock(RouteRepository.class)));
        int routeId = 1;

        // Act
        ResponseEntity<List<RouteStopInfo>> actualRouteStopsByRouteId = routeController.getRouteStopsByRouteId(routeId);

        // Assert
        verify(routeStopRepository).findRouteStopsByRouteId(Mockito.<Integer>any());
        assertEquals(200, actualRouteStopsByRouteId.getStatusCodeValue());
        assertTrue(actualRouteStopsByRouteId.hasBody());
        assertTrue(actualRouteStopsByRouteId.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link RouteController#getRouteStopsByRouteId(Integer)}
     */
    @Test
    void testGetRouteStopsByRouteId2() { 
        // Arrange
        RouteStopService routeStopService = mock(RouteStopService.class);
        when(routeStopService.getRouteStopsByRouteId(Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        BusStopService busStopService = new BusStopService(mock(BusStopRepository.class));
        RouteController routeController = new RouteController(routeStopService, busStopService,
                new RouteService(new RouteStopDtoMapper(), mock(BusStopRepository.class), mock(RouteRepository.class)));
        int routeId = 1;

        // Act
        ResponseEntity<List<RouteStopInfo>> actualRouteStopsByRouteId = routeController.getRouteStopsByRouteId(routeId);

        // Assert
        verify(routeStopService).getRouteStopsByRouteId(Mockito.<Integer>any());
        assertEquals(200, actualRouteStopsByRouteId.getStatusCodeValue());
        assertTrue(actualRouteStopsByRouteId.hasBody());
        assertTrue(actualRouteStopsByRouteId.getHeaders().isEmpty());
    }
}
