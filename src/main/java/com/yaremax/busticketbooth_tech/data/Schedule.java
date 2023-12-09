package com.yaremax.busticketbooth_tech.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "bus_id")
    private Bus bus;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "route_id")
    private Route route;

    @Column(name = "departure_time")
    private LocalTime departureTime;


    @OneToMany(mappedBy = "schedule")
    private Set<BoardingManifest> boardingManifests = new LinkedHashSet<>();

    @OneToMany(mappedBy = "schedule")
    private Set<Ticket> tickets = new LinkedHashSet<>();

}