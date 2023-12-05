package com.yaremax.busticketbooth_tech.repositories;

import com.yaremax.busticketbooth_tech.data.Stop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StopRepository extends JpaRepository<Stop, Integer> {
}