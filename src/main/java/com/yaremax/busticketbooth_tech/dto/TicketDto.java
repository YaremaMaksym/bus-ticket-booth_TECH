package com.yaremax.busticketbooth_tech.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.yaremax.busticketbooth_tech.data.Ticket}
 */
@Value
public class TicketDto implements Serializable {
    Integer scheduleId;
    Integer busStopId;
    Integer seatNumber;
    String ticketStatus;
}