package com.yaremax.busticketbooth_tech.data;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class RouteStopId implements Serializable {
    private static final long serialVersionUID = 2279525142043879564L;
    @Column(name = "route_id", nullable = false)
    private Integer routeId;

    @Column(name = "stop_id", nullable = false)
    private Integer stopId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RouteStopId entity = (RouteStopId) o;
        return Objects.equals(this.routeId, entity.routeId) &&
                Objects.equals(this.stopId, entity.stopId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routeId, stopId);
    }

}