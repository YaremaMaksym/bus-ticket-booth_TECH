package com.yaremax.busticketbooth_tech.projections;

/**
 * Projection for {@link com.yaremax.busticketbooth_tech.data.Bus}
 */
public interface BusInfo {
    Integer getId();

    String getSerialNumber();

    Integer getSeatCapacity();
}