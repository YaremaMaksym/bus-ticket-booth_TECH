package com.yaremax.busticketbooth_tech.services;

import com.yaremax.busticketbooth_tech.data.Bus;
import com.yaremax.busticketbooth_tech.exception.ResourceNotFoundException;
import com.yaremax.busticketbooth_tech.repositories.BusRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BusService {
    private final BusRepository busRepository;

    public List<Bus> findAll() {
        return busRepository.findAll();
    }

    public Optional<Bus> findById(Integer id) {
        return busRepository.findById(id);
    }

    @Transactional
    public void addBus(Bus bus) {
        busRepository.save(bus);
    }

    @Transactional
    public void deleteBus(Integer id) {
        Bus bus = busRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bus with id " + id + " wasn't found"));
        busRepository.delete(bus);
    }

    @Transactional
    public void patchBus(Integer id, Integer newSeatCapacity) {
        Bus bus = busRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bus with id " + id + " wasn't found"));
        // TODO: перевірка чи newSeatCapacity більше за кількість вже зайнятих місць, якщо так то всьо файно, нє - повідомлення
        bus.setSeatCapacity(newSeatCapacity);
        busRepository.save(bus);
    }
}
