package com.yaremax.busticketbooth_tech.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "routes")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "destination_stop_id")
    private BusStop destinationBusStop;

    @OneToMany(mappedBy = "route")
    private Set<Bus> buses = new LinkedHashSet<>();

    @OneToMany(mappedBy = "route")
    private Set<RouteStop> routeStops = new LinkedHashSet<>();

    @OneToMany(mappedBy = "route")
    private Set<Schedule> schedules = new LinkedHashSet<>();

}