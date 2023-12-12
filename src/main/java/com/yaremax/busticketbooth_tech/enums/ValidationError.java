package com.yaremax.busticketbooth_tech.enums;

import lombok.Getter;

@Getter
public enum ValidationError {
    SERIAL_NUMBER_EXISTS("Serial number already exists."),
    INVALID_SEAT_CAPACITY("Seat capacity is less than the number of sold tickets.");

    private final String message;

    ValidationError(String message) {
        this.message = message;
    }

}
