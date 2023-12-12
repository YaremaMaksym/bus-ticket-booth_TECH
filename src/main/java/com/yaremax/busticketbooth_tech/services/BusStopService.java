package com.yaremax.busticketbooth_tech.services;

import com.yaremax.busticketbooth_tech.data.BusStop;
import com.yaremax.busticketbooth_tech.exception.ResourceNotFoundException;
import com.yaremax.busticketbooth_tech.repositories.BusStopRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BusStopService {
    private final BusStopRepository busStopRepository;

    public List<BusStop> findAll() {
        return busStopRepository.findAll();
    }

    public BusStop findById(Integer busStopId) {
        return busStopRepository.findById(busStopId)
                .orElseThrow(() -> new ResourceNotFoundException("Bus stop with id " + busStopId + " wasn't found"));
    }

    @Transactional
    public void addBusStop(BusStop busStop) {
        busStopRepository.save(busStop);
    }

    @Transactional
    public void deleteBusStop(Integer busStopId) {
        BusStop busStop = findById(busStopId);
        busStopRepository.delete(busStop);
    }

    @Transactional
    public void patchBusStop(Integer busStopId, String name) {
        BusStop busStop = findById(busStopId);
        busStop.setName(name);
        busStopRepository.save(busStop);
    }
}
