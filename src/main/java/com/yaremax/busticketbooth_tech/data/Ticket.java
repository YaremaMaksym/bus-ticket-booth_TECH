package com.yaremax.busticketbooth_tech.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "tickets")
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "stop_id")
    private BusStop busStop;

    @Column(name = "seat_number")
    private Integer seatNumber;

    @Column(name = "ticket_status", length = 50)
    private String ticketStatus;

    public Ticket(Schedule schedule, BusStop busStop, Integer seatNumber, String ticketStatus) {
        this.schedule = schedule;
        this.busStop = busStop;
        this.seatNumber = seatNumber;
        this.ticketStatus = ticketStatus;
    }
}