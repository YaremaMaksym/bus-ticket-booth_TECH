package com.yaremax.busticketbooth_tech.repositories;

import com.yaremax.busticketbooth_tech.data.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRepository extends JpaRepository<Bus, Integer> {
    boolean existsBySerialNumber(String serialNumber);
}