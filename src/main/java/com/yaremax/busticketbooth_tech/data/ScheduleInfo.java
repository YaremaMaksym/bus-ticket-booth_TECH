package com.yaremax.busticketbooth_tech.data;
import java.time.LocalDateTime;

/**
 * Projection for {@link Schedule}
 */
public interface ScheduleInfo {
    Integer getId();

    Integer getAvailableSeats();

    Integer getTotalSeats();

    String getFinalDestinationName();

    LocalDateTime getDepartureDateTime();

    Bus getBus();

    Route getRoute();
}