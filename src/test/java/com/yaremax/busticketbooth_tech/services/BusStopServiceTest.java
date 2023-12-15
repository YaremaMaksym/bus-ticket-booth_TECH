package com.yaremax.busticketbooth_tech.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yaremax.busticketbooth_tech.data.BusStop;
import com.yaremax.busticketbooth_tech.exception.DuplicateResourceException;
import com.yaremax.busticketbooth_tech.exception.ResourceNotFoundException;
import com.yaremax.busticketbooth_tech.repositories.BusStopRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BusStopService.class})
@ExtendWith(SpringExtension.class)
class BusStopServiceTest {
    @MockBean
    private BusStopRepository busStopRepository;

    @Autowired
    private BusStopService busStopService;

    /**
     * Method under test: {@link BusStopService#findAll()}
     */
    @Test
    void testFindAll() {
        // Arrange
        ArrayList<BusStop> busStopList = new ArrayList<>();
        when(busStopRepository.findAll()).thenReturn(busStopList);

        // Act
        List<BusStop> actualFindAllResult = busStopService.findAll();

        // Assert
        verify(busStopRepository).findAll();
        assertTrue(actualFindAllResult.isEmpty());
        assertSame(busStopList, actualFindAllResult);
    }

    /**
     * Method under test: {@link BusStopService#findAll()}
     */
    @Test
    void testFindAll2() {
        // Arrange
        when(busStopRepository.findAll()).thenThrow(new DuplicateResourceException("An error occurred"));

        // Act and Assert
        assertThrows(DuplicateResourceException.class, () -> busStopService.findAll());
        verify(busStopRepository).findAll();
    }

    /**
     * Method under test: {@link BusStopService#findById(Integer)}
     */
    @Test
    void testFindById() {
        // Arrange
        BusStop busStop = new BusStop();
        busStop.setId(1);
        busStop.setName("Name");
        Optional<BusStop> ofResult = Optional.of(busStop);
        when(busStopRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        int busStopId = 1;

        // Act
        BusStop actualFindByIdResult = busStopService.findById(busStopId);

        // Assert
        verify(busStopRepository).findById(Mockito.<Integer>any());
        assertSame(busStop, actualFindByIdResult);
    }

    /**
     * Method under test: {@link BusStopService#findById(Integer)}
     */
    @Test
    void testFindById2() {
        // Arrange
        Optional<BusStop> emptyResult = Optional.empty();
        when(busStopRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);
        int busStopId = 1;

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> busStopService.findById(busStopId));
        verify(busStopRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link BusStopService#findById(Integer)}
     */
    @Test
    void testFindById3() {
        // Arrange
        when(busStopRepository.findById(Mockito.<Integer>any()))
                .thenThrow(new DuplicateResourceException("An error occurred"));
        int busStopId = 1;

        // Act and Assert
        assertThrows(DuplicateResourceException.class, () -> busStopService.findById(busStopId));
        verify(busStopRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link BusStopService#addBusStop(BusStop)}
     */
    @Test
    void testAddBusStop() {
        // Arrange
        when(busStopRepository.existsByName(Mockito.<String>any())).thenReturn(true);

        BusStop busStop = new BusStop();
        busStop.setId(1);
        busStop.setName("Name");

        // Act and Assert
        assertThrows(DuplicateResourceException.class, () -> busStopService.addBusStop(busStop));
        verify(busStopRepository).existsByName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link BusStopService#addBusStop(BusStop)}
     */
    @Test
    void testAddBusStop2() {
        // Arrange
        BusStop busStop = new BusStop();
        busStop.setId(1);
        busStop.setName("Name");
        when(busStopRepository.existsByName(Mockito.<String>any())).thenReturn(false);
        when(busStopRepository.save(Mockito.<BusStop>any())).thenReturn(busStop);

        BusStop busStop2 = new BusStop();
        busStop2.setId(1);
        busStop2.setName("Name");

        // Act
        busStopService.addBusStop(busStop2);

        // Assert that nothing has changed
        verify(busStopRepository).existsByName(Mockito.<String>any());
        verify(busStopRepository).save(Mockito.<BusStop>any());
        assertEquals("Name", busStop2.getName());
        assertEquals(1, busStop2.getId().intValue());
    }

    /**
     * Method under test: {@link BusStopService#addBusStop(BusStop)}
     */
    @Test
    void testAddBusStop3() {
        // Arrange
        when(busStopRepository.existsByName(Mockito.<String>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));

        BusStop busStop = new BusStop();
        busStop.setId(1);
        busStop.setName("Name");

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> busStopService.addBusStop(busStop));
        verify(busStopRepository).existsByName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link BusStopService#deleteBusStop(Integer)}
     */
    @Test
    void testDeleteBusStop() {
        // Arrange
        BusStop busStop = new BusStop();
        busStop.setId(1);
        busStop.setName("Name");
        Optional<BusStop> ofResult = Optional.of(busStop);
        doNothing().when(busStopRepository).delete(Mockito.<BusStop>any());
        when(busStopRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        int busStopId = 1;

        // Act
        busStopService.deleteBusStop(busStopId);

        // Assert that nothing has changed
        verify(busStopRepository).delete(Mockito.<BusStop>any());
        verify(busStopRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link BusStopService#deleteBusStop(Integer)}
     */
    @Test
    void testDeleteBusStop2() {
        // Arrange
        BusStop busStop = new BusStop();
        busStop.setId(1);
        busStop.setName("Name");
        Optional<BusStop> ofResult = Optional.of(busStop);
        doThrow(new DuplicateResourceException("An error occurred")).when(busStopRepository).delete(Mockito.<BusStop>any());
        when(busStopRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        int busStopId = 1;

        // Act and Assert
        assertThrows(DuplicateResourceException.class, () -> busStopService.deleteBusStop(busStopId));
        verify(busStopRepository).delete(Mockito.<BusStop>any());
        verify(busStopRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link BusStopService#deleteBusStop(Integer)}
     */
    @Test
    void testDeleteBusStop3() {
        // Arrange
        Optional<BusStop> emptyResult = Optional.empty();
        when(busStopRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);
        int busStopId = 1;

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> busStopService.deleteBusStop(busStopId));
        verify(busStopRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link BusStopService#patchBusStop(Integer, String)}
     */
    @Test
    void testPatchBusStop() {
        // Arrange
        BusStop busStop = new BusStop();
        busStop.setId(1);
        busStop.setName("Name");
        Optional<BusStop> ofResult = Optional.of(busStop);
        when(busStopRepository.existsByName(Mockito.<String>any())).thenReturn(true);
        when(busStopRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        int busStopId = 1;
        String newName = "New Name";

        // Act and Assert
        assertThrows(DuplicateResourceException.class, () -> busStopService.patchBusStop(busStopId, newName));
        verify(busStopRepository).existsByName(Mockito.<String>any());
        verify(busStopRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link BusStopService#patchBusStop(Integer, String)}
     */
    @Test
    void testPatchBusStop2() {
        // Arrange
        BusStop busStop = new BusStop();
        busStop.setId(1);
        busStop.setName("Name");
        Optional<BusStop> ofResult = Optional.of(busStop);
        when(busStopRepository.existsByName(Mockito.<String>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        when(busStopRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        int busStopId = 1;
        String newName = "New Name";

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> busStopService.patchBusStop(busStopId, newName));
        verify(busStopRepository).existsByName(Mockito.<String>any());
        verify(busStopRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link BusStopService#patchBusStop(Integer, String)}
     */
    @Test
    void testPatchBusStop3() {
        // Arrange
        BusStop busStop = new BusStop();
        busStop.setId(1);
        busStop.setName("Name");
        Optional<BusStop> ofResult = Optional.of(busStop);

        BusStop busStop2 = new BusStop();
        busStop2.setId(1);
        busStop2.setName("Name");
        when(busStopRepository.existsByName(Mockito.<String>any())).thenReturn(false);
        when(busStopRepository.save(Mockito.<BusStop>any())).thenReturn(busStop2);
        when(busStopRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        int busStopId = 1;
        String newName = "New Name";

        // Act
        busStopService.patchBusStop(busStopId, newName);

        // Assert
        verify(busStopRepository).existsByName(Mockito.<String>any());
        verify(busStopRepository).findById(Mockito.<Integer>any());
        verify(busStopRepository).save(Mockito.<BusStop>any());
    }

    /**
     * Method under test: {@link BusStopService#patchBusStop(Integer, String)}
     */
    @Test
    void testPatchBusStop4() {
        // Arrange
        Optional<BusStop> emptyResult = Optional.empty();
        when(busStopRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);
        int busStopId = 1;
        String newName = "New Name";

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> busStopService.patchBusStop(busStopId, newName));
        verify(busStopRepository).findById(Mockito.<Integer>any());
    }
}
