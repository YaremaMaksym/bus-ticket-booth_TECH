package com.yaremax.busticketbooth_tech.services;

import com.yaremax.busticketbooth_tech.data.BusStop;
import com.yaremax.busticketbooth_tech.exception.ResourceNotFoundException;
import com.yaremax.busticketbooth_tech.repositories.BusStopRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BusStopService {
    private final BusStopRepository busStopRepository;

    public List<BusStop> findAll() {
        return busStopRepository.findAll();
    }

    public Optional<BusStop> findById(Integer id) {
        return busStopRepository.findById(id);
    }

    @Transactional
    public void addBusStop(BusStop busStop) {
        busStopRepository.save(busStop);
    }

    @Transactional
    public void deleteBusStop(Integer id) {
        BusStop busStop = busStopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bus stop with id " + id + " wasn't found"));
        busStopRepository.delete(busStop);
    }

    @Transactional
    public void patchBusStop(Integer id, String name) {
        BusStop busStop = busStopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bus stop with id " + id + " wasn't found"));
        busStop.setName(name);
        busStopRepository.save(busStop);
    }
}
