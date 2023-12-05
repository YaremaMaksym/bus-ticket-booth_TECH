package com.yaremax.busticketbooth_tech.repositories;

import com.yaremax.busticketbooth_tech.data.BoardingManifest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardingManifestRepository extends JpaRepository<BoardingManifest, Integer> {
}