package com.yaremax.busticketbooth_tech.projections;

import com.yaremax.busticketbooth_tech.data.RouteStopId;

/**
 * Projection for {@link RouteStopId}
 */
public interface RouteStopIdInfo {
    Integer getRouteId();

    Integer getStopId();
}