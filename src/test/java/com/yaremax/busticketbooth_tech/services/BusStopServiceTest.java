package com.yaremax.busticketbooth_tech.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.yaremax.busticketbooth_tech.data.BusStop;
import com.yaremax.busticketbooth_tech.exception.DuplicateResourceException;
import com.yaremax.busticketbooth_tech.exception.ResourceNotFoundException;
import com.yaremax.busticketbooth_tech.repositories.BusStopRepository;

import java.util.ArrayList;
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
class BusStopServiceTest {
    @MockBean
    private BusStopRepository busStopRepository;

    @Autowired
    private BusStopService busStopService;

    @Nested
    class FindAllTests {
        @Test
        void findAll_shouldReturnEmptyList_whenNoBusStopsExist() {
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

        @Test
        void findAll_shouldThrowDuplicateResourceException_whenRepositoryThrowsException() {
            // Arrange
            when(busStopRepository.findAll()).thenThrow(new DuplicateResourceException("An error occurred"));

            // Act and Assert
            assertThrows(DuplicateResourceException.class, () -> busStopService.findAll());
            verify(busStopRepository).findAll();
        }
    }

    @Nested
    class FindByIdTests {
        @Test
        void findById_shouldReturnBusStop_whenBusStopExists() {
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

        @Test
        void findById_shouldThrowResourceNotFoundException_whenBusStopDoesNotExist() {
            // Arrange
            Optional<BusStop> emptyResult = Optional.empty();
            when(busStopRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);
            int busStopId = 1;

            // Act and Assert
            assertThrows(ResourceNotFoundException.class, () -> busStopService.findById(busStopId));
            verify(busStopRepository).findById(Mockito.<Integer>any());
        }

        @Test
        void findById_shouldThrowDuplicateResourceException_whenRepositoryThrowsException() {
            // Arrange
            when(busStopRepository.findById(Mockito.<Integer>any()))
                    .thenThrow(new DuplicateResourceException("An error occurred"));
            int busStopId = 1;

            // Act and Assert
            assertThrows(DuplicateResourceException.class, () -> busStopService.findById(busStopId));
            verify(busStopRepository).findById(Mockito.<Integer>any());
        }
    }

    @Nested
    class AddBusStopTests {
        @Test
        void addBusStop_shouldThrowDuplicateResourceException_whenBusStopNameAlreadyExists() {
            // Arrange
            when(busStopRepository.existsByName(Mockito.<String>any())).thenReturn(true);

            BusStop busStop = new BusStop();
            busStop.setId(1);
            busStop.setName("Name");

            // Act and Assert
            assertThrows(DuplicateResourceException.class, () -> busStopService.addBusStop(busStop));
            verify(busStopRepository).existsByName(Mockito.<String>any());
        }

        @Test
        void addBusStop_shouldAddBusStop_whenBusStopNameDoesNotExist() {
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

            // Assert
            verify(busStopRepository).existsByName(Mockito.<String>any());
            verify(busStopRepository).save(Mockito.<BusStop>any());
            assertEquals("Name", busStop2.getName());
            assertEquals(1, busStop2.getId().intValue());
        }

        @Test
        void addBusStop_shouldThrowResourceNotFoundException_whenRepositoryThrowsException() {
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
    }

    @Nested
    class DeleteBusStopTests {
        @Test
        void deleteBusStop_shouldDeleteBusStop_whenBusStopExists() {
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

            // Assert
            verify(busStopRepository).delete(Mockito.<BusStop>any());
            verify(busStopRepository).findById(Mockito.<Integer>any());
        }

        @Test
        void deleteBusStop_shouldThrowDuplicateResourceException_whenRepositoryThrowsException() {
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

        @Test
        void deleteBusStop_shouldThrowResourceNotFoundException_whenBusStopDoesNotExist() {
            // Arrange
            Optional<BusStop> emptyResult = Optional.empty();
            when(busStopRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);
            int busStopId = 1;

            // Act and Assert
            assertThrows(ResourceNotFoundException.class, () -> busStopService.deleteBusStop(busStopId));
            verify(busStopRepository).findById(Mockito.<Integer>any());
        }
    }

    @Nested
    class PatchBusStopTests {
        @Test
        void patchBusStop_shouldThrowDuplicateResourceException_whenNewNameAlreadyExists() {
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

        @Test
        void patchBusStop_shouldThrowResourceNotFoundException_whenRepositoryThrowsException() {
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

        @Test
        void patchBusStop_shouldUpdateBusStopName_whenNewNameDoesNotExist() {
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

        @Test
        void patchBusStop_shouldThrowResourceNotFoundException_whenBusStopDoesNotExist() {
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
}