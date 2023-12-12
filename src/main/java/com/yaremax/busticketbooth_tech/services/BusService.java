package com.yaremax.busticketbooth_tech.services;

import com.yaremax.busticketbooth_tech.data.Bus;
import com.yaremax.busticketbooth_tech.enums.ValidationError;
import com.yaremax.busticketbooth_tech.exception.ResourceNotFoundException;
import com.yaremax.busticketbooth_tech.repositories.BusRepository;
import com.yaremax.busticketbooth_tech.repositories.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BusService {
    private final BusRepository busRepository;
    private final TicketRepository ticketRepository;

    public List<Bus> findAll() {
        return busRepository.findAll();
    }

    public Bus findById(Integer busId) {
        return busRepository.findById(busId)
                .orElseThrow(() -> new ResourceNotFoundException("Bus with id " + busId + " wasn't found"));
    }

    @Transactional
    public Optional<ValidationError> addBus(Bus bus) {
        if (busRepository.existsBySerialNumber(bus.getSerialNumber())) {
            return Optional.of(ValidationError.SERIAL_NUMBER_EXISTS);
        }
        busRepository.save(bus);
        return Optional.empty();
    }

    @Transactional
    public void deleteBus(Integer busId) {
        Bus bus = findById(busId);
        busRepository.delete(bus);
    }

    @Transactional
    public Optional<ValidationError> patchBus(Integer busId,
                                              String newSerialNumber,
                                              Integer newSeatCapacity) {
        Bus bus = findById(busId);

        if (!bus.getSerialNumber().equals(newSerialNumber) && busRepository.existsBySerialNumber(newSerialNumber)) {
            return Optional.of(ValidationError.SERIAL_NUMBER_EXISTS);
        }
        if (newSeatCapacity < ticketRepository.countTicketsForScheduleByBusSerialNumber(busId)) {
            return Optional.of(ValidationError.INVALID_SEAT_CAPACITY);
        }

        bus.setSerialNumber(newSerialNumber);
        bus.setSeatCapacity(newSeatCapacity);
        busRepository.save(bus);
        return Optional.empty();
    }
}
