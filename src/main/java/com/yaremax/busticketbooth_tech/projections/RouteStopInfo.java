package com.yaremax.busticketbooth_tech.projections;

import com.yaremax.busticketbooth_tech.data.RouteStop;

/**
 * Projection for {@link RouteStop}
 */
public interface RouteStopInfo {
    Integer getSequenceNumber();

    Integer getDepartureOffset();

    RouteStopIdInfo getId();

    RouteInfo getRoute();

    BusStopInfo getBusStop();
}