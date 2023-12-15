package com.yaremax.busticketbooth_tech.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yaremax.busticketbooth_tech.data.BusStop;
import com.yaremax.busticketbooth_tech.repositories.BusStopRepository;
import com.yaremax.busticketbooth_tech.services.BusStopService;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

class BusStopControllerTest {
  /**
   * Method under test: {@link BusStopController#getBusStops(Model)}
   */
  @Test
  void testGetBusStops() { 

    // Arrange
    BusStopRepository busStopRepository = mock(BusStopRepository.class);
    when(busStopRepository.findAll()).thenReturn(new ArrayList<>());
    BusStopController busStopController = new BusStopController(new BusStopService(busStopRepository));
    ConcurrentModel model = new ConcurrentModel();

    // Act
    String actualBusStops = busStopController.getBusStops(model);

    // Assert
    verify(busStopRepository).findAll();
    assertEquals("bus_stops", actualBusStops);
  }

  /**
   * Method under test: {@link BusStopController#getBusStops(Model)}
   */
  @Test
  void testGetBusStops2() { 

    // Arrange
    BusStopService busStopService = mock(BusStopService.class);
    when(busStopService.findAll()).thenReturn(new ArrayList<>());
    BusStopController busStopController = new BusStopController(busStopService);
    ConcurrentModel model = new ConcurrentModel();

    // Act
    String actualBusStops = busStopController.getBusStops(model);

    // Assert
    verify(busStopService).findAll();
    assertEquals("bus_stops", actualBusStops);
  }

  /**
   * Method under test: {@link BusStopController#addBusStop(String)}
   */
  @Test
  void testAddBusStop() { 
    // Arrange
    BusStop busStop = new BusStop();
    busStop.setId(1);
    busStop.setName("Name");
    BusStopRepository busStopRepository = mock(BusStopRepository.class);
    when(busStopRepository.existsByName(Mockito.<String>any())).thenReturn(false);
    when(busStopRepository.save(Mockito.<BusStop>any())).thenReturn(busStop);
    BusStopController busStopController = new BusStopController(new BusStopService(busStopRepository));
    String name = "Name";

    // Act
    String actualAddBusStopResult = busStopController.addBusStop(name);

    // Assert
    verify(busStopRepository).existsByName(Mockito.<String>any());
    verify(busStopRepository).save(Mockito.<BusStop>any());
    assertEquals("redirect:/bus_stops", actualAddBusStopResult);
  }

  /**
   * Method under test: {@link BusStopController#addBusStop(String)}
   */
  @Test
  void testAddBusStop2() { 
    // Arrange
    BusStopService busStopService = mock(BusStopService.class);
    doNothing().when(busStopService).addBusStop(Mockito.<BusStop>any());
    BusStopController busStopController = new BusStopController(busStopService);
    String name = "Name";

    // Act
    String actualAddBusStopResult = busStopController.addBusStop(name);

    // Assert
    verify(busStopService).addBusStop(Mockito.<BusStop>any());
    assertEquals("redirect:/bus_stops", actualAddBusStopResult);
  }

  /**
   * Method under test: {@link BusStopController#deleteBusStop(Integer)}
   */
  @Test
  void testDeleteBusStop() { 

    // Arrange
    BusStop busStop = new BusStop();
    busStop.setId(1);
    busStop.setName("Name");
    Optional<BusStop> ofResult = Optional.of(busStop);
    BusStopRepository busStopRepository = mock(BusStopRepository.class);
    doNothing().when(busStopRepository).delete(Mockito.<BusStop>any());
    when(busStopRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
    BusStopController busStopController = new BusStopController(new BusStopService(busStopRepository));
    int id = 1;

    // Act
    String actualDeleteBusStopResult = busStopController.deleteBusStop(id);

    // Assert
    verify(busStopRepository).delete(Mockito.<BusStop>any());
    verify(busStopRepository).findById(Mockito.<Integer>any());
    assertEquals("redirect:/bus_stops", actualDeleteBusStopResult);
  }

  /**
   * Method under test: {@link BusStopController#deleteBusStop(Integer)}
   */
  @Test
  void testDeleteBusStop2() { 

    // Arrange
    BusStopService busStopService = mock(BusStopService.class);
    doNothing().when(busStopService).deleteBusStop(Mockito.<Integer>any());
    BusStopController busStopController = new BusStopController(busStopService);
    int id = 1;

    // Act
    String actualDeleteBusStopResult = busStopController.deleteBusStop(id);

    // Assert
    verify(busStopService).deleteBusStop(Mockito.<Integer>any());
    assertEquals("redirect:/bus_stops", actualDeleteBusStopResult);
  }

  /**
   * Method under test: {@link BusStopController#patchBusStop(Integer, String)}
   */
  @Test
  void testPatchBusStop() { 

    // Arrange
    BusStop busStop = new BusStop();
    busStop.setId(1);
    busStop.setName("Name");
    Optional<BusStop> ofResult = Optional.of(busStop);

    BusStop busStop2 = new BusStop();
    busStop2.setId(1);
    busStop2.setName("Name");
    BusStopRepository busStopRepository = mock(BusStopRepository.class);
    when(busStopRepository.save(Mockito.<BusStop>any())).thenReturn(busStop2);
    when(busStopRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
    BusStopController busStopController = new BusStopController(new BusStopService(busStopRepository));
    int id = 1;
    String name = "Name";

    // Act
    String actualPatchBusStopResult = busStopController.patchBusStop(id, name);

    // Assert
    verify(busStopRepository).findById(Mockito.<Integer>any());
    verify(busStopRepository).save(Mockito.<BusStop>any());
    assertEquals("redirect:/bus_stops", actualPatchBusStopResult);
  }

  /**
   * Method under test: {@link BusStopController#patchBusStop(Integer, String)}
   */
  @Test
  void testPatchBusStop2() { 

    // Arrange
    BusStop busStop = mock(BusStop.class);
    when(busStop.getName()).thenReturn("foo");
    doNothing().when(busStop).setId(Mockito.<Integer>any());
    doNothing().when(busStop).setName(Mockito.<String>any());
    busStop.setId(1);
    busStop.setName("Name");
    Optional<BusStop> ofResult = Optional.of(busStop);

    BusStop busStop2 = new BusStop();
    busStop2.setId(1);
    busStop2.setName("Name");
    BusStopRepository busStopRepository = mock(BusStopRepository.class);
    when(busStopRepository.existsByName(Mockito.<String>any())).thenReturn(false);
    when(busStopRepository.save(Mockito.<BusStop>any())).thenReturn(busStop2);
    when(busStopRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
    BusStopController busStopController = new BusStopController(new BusStopService(busStopRepository));
    int id = 1;
    String name = "Name";

    // Act
    String actualPatchBusStopResult = busStopController.patchBusStop(id, name);

    // Assert
    verify(busStop).getName();
    verify(busStop).setId(Mockito.<Integer>any());
    verify(busStop, atLeast(1)).setName(Mockito.<String>any());
    verify(busStopRepository).existsByName(Mockito.<String>any());
    verify(busStopRepository).findById(Mockito.<Integer>any());
    verify(busStopRepository).save(Mockito.<BusStop>any());
    assertEquals("redirect:/bus_stops", actualPatchBusStopResult);
  }

  /**
   * Method under test: {@link BusStopController#patchBusStop(Integer, String)}
   */
  @Test
  void testPatchBusStop3() { 

    // Arrange
    BusStopService busStopService = mock(BusStopService.class);
    doNothing().when(busStopService).patchBusStop(Mockito.<Integer>any(), Mockito.<String>any());
    BusStopController busStopController = new BusStopController(busStopService);
    int id = 1;
    String name = "Name";

    // Act
    String actualPatchBusStopResult = busStopController.patchBusStop(id, name);

    // Assert
    verify(busStopService).patchBusStop(Mockito.<Integer>any(), Mockito.<String>any());
    assertEquals("redirect:/bus_stops", actualPatchBusStopResult);
  }
}
