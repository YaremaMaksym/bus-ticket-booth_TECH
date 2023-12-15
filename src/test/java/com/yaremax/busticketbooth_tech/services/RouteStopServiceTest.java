package com.yaremax.busticketbooth_tech.services;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yaremax.busticketbooth_tech.projections.BusStopInfo;
import com.yaremax.busticketbooth_tech.projections.RouteStopInfo;
import com.yaremax.busticketbooth_tech.repositories.RouteStopRepository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RouteStopService.class})
@ExtendWith(SpringExtension.class)
class RouteStopServiceTest {
    @MockBean
    private RouteStopRepository routeStopRepository;

    @Autowired
    private RouteStopService routeStopService;

    /**
     * Method under test: {@link RouteStopService#getRouteStopsByRouteId(Integer)}
     */
    @Test
    void testGetRouteStopsByRouteId() {
        // Arrange
        ArrayList<RouteStopInfo> routeStopInfoList = new ArrayList<>();
        when(routeStopRepository.findRouteStopsByRouteId(Mockito.<Integer>any())).thenReturn(routeStopInfoList);
        int routeId = 1;

        // Act
        List<RouteStopInfo> actualRouteStopsByRouteId = routeStopService.getRouteStopsByRouteId(routeId);

        // Assert
        verify(routeStopRepository).findRouteStopsByRouteId(Mockito.<Integer>any());
        assertTrue(actualRouteStopsByRouteId.isEmpty());
        assertSame(routeStopInfoList, actualRouteStopsByRouteId);
    }

    /**
     * Method under test: {@link RouteStopService#getStopsForRoute(Integer)}
     */
    @Test
    void testGetStopsForRoute() {
        // Arrange
        ArrayList<BusStopInfo> busStopInfoList = new ArrayList<>();
        when(routeStopRepository.findBusStopInfosByRouteId(Mockito.<Integer>any())).thenReturn(busStopInfoList);
        int id = 1;

        // Act
        List<BusStopInfo> actualStopsForRoute = routeStopService.getStopsForRoute(id);

        // Assert
        verify(routeStopRepository).findBusStopInfosByRouteId(Mockito.<Integer>any());
        assertTrue(actualStopsForRoute.isEmpty());
        assertSame(busStopInfoList, actualStopsForRoute);
    }
}
