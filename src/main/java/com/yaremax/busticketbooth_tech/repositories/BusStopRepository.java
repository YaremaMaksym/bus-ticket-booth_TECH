package com.yaremax.busticketbooth_tech.repositories;

import com.yaremax.busticketbooth_tech.data.BusStop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusStopRepository extends JpaRepository<BusStop, Integer> {
    boolean existsByName(String name);
}