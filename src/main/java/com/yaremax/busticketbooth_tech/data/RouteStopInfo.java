package com.yaremax.busticketbooth_tech.data;

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