package com.yaremax.busticketbooth_tech.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "boarding_manifest")
public class BoardingManifest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manifest_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

}