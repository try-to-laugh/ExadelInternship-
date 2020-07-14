package com.hcb.hotchairs.models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Details")
public class Detail {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_timestamp")
    private Timestamp start;

    @Column(name = "end_timestamp")
    private Timestamp end;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    public Detail() {
    }

    public Detail(Long id, Timestamp start, Timestamp end, Reservation reservation, Place place) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.reservation = reservation;
        this.place = place;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
