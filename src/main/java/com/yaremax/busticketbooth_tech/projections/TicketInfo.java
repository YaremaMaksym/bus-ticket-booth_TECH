package com.yaremax.busticketbooth_tech.projections;

/**
 * Projection for {@link com.yaremax.busticketbooth_tech.data.Ticket}
 */
public interface TicketInfo {
    Integer getId();

    Integer getSeatNumber();

    String getTicketStatus();

    ScheduleInfo getSchedule();

    BusStopInfo getBusStop();
}