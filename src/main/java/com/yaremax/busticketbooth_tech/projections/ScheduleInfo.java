package com.yaremax.busticketbooth_tech.projections;
import com.yaremax.busticketbooth_tech.data.Bus;
import com.yaremax.busticketbooth_tech.data.Route;
import com.yaremax.busticketbooth_tech.data.Schedule;

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