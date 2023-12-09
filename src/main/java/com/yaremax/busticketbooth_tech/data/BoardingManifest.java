package com.yaremax.busticketbooth_tech.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "boarding_manifest")
public class BoardingManifest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manifest_id", nullable = false)
    private Integer id;

    @OneToOne(mappedBy = "boardingManifest")
    private Schedule schedule;

}