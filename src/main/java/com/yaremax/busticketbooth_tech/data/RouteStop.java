package com.yaremax.busticketbooth_tech.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "routes_stops")
@NoArgsConstructor
public class RouteStop {
    @EmbeddedId
    private RouteStopId id;

    @MapsId("routeId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @MapsId("stopId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "stop_id", nullable = false)
    private BusStop busStop;

    @Column(name = "sequence_number")
    private Integer sequenceNumber;

    @Column(name = "departure_offset")
    private Integer departureOffset;

}