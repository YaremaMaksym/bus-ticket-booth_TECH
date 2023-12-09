package com.yaremax.busticketbooth_tech.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "stops")
public class BusStop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stop_id", nullable = false)
    private Integer id;

    @Column(name = "stop_name", unique = true)
    private String name;

//    @OneToMany(mappedBy = "busStop")
//    private Set<RouteStop> routeStops = new LinkedHashSet<>();

//    @OneToMany(mappedBy = "busStop")
//    private Set<Ticket> tickets = new LinkedHashSet<>();

    public BusStop(String name) {
        this.name = name;
    }
}