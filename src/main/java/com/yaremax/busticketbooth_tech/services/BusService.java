package com.yaremax.busticketbooth_tech.services;

import com.yaremax.busticketbooth_tech.data.Bus;
import com.yaremax.busticketbooth_tech.exception.DuplicateResourceException;
import com.yaremax.busticketbooth_tech.exception.ResourceNotFoundException;
import com.yaremax.busticketbooth_tech.exception.ValidationException;
import com.yaremax.busticketbooth_tech.repositories.BusRepository;
import com.yaremax.busticketbooth_tech.repositories.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void addBus(Bus bus) {
        if (busRepository.existsBySerialNumber(bus.getSerialNumber())) {
            throw new DuplicateResourceException("Serial number already exists.");
        }
        busRepository.save(bus);
    }

    @Transactional
    public void deleteBus(Integer busId) {
        Bus bus = findById(busId);
        busRepository.delete(bus);
    }

    @Transactional
    public void patchBus(Integer busId, String newSerialNumber, Integer newSeatCapacity) {
        Bus bus = findById(busId);

        if (!bus.getSerialNumber().equals(newSerialNumber) && busRepository.existsBySerialNumber(newSerialNumber)) {
            throw new DuplicateResourceException("Serial number already exists.");
        }
        if (newSeatCapacity < ticketRepository.countTicketsForScheduleByBusSerialNumber(busId)) {
            throw new ValidationException("Invalid seat capacity: less than the number of sold tickets.");
        }

        bus.setSerialNumber(newSerialNumber);
        bus.setSeatCapacity(newSeatCapacity);
        busRepository.save(bus);
    }
}

