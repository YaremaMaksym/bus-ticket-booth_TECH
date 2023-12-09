package com.yaremax.busticketbooth_tech.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "buses")
@NoArgsConstructor
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_id", nullable = false)
    private Integer id;

    @Column(name = "bus_serial_number", unique = true)
    private String serialNumber;

    @Column(name = "seat_capacity")
    private Integer seatCapacity;

    public Bus(String serialNumber, Integer seatCapacity) {
        this.serialNumber = serialNumber;
        this.seatCapacity = seatCapacity;
    }
}