package com.yaremax.busticketbooth_tech.data;
import java.time.LocalTime;

/**
 * Projection for {@link Schedule}
 */
public interface ScheduleInfo {
    Integer getId();

    Integer getAvailableSeats();

    Integer getTotalSeats();

    String getFinalDestinationName();

    LocalTime getDepartureTime();

    Bus getBus();

    Route getRoute();
}