package com.hcb.hotchairs.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Reservations")
public class Reservation {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "host_id")
    private Reservation host;

    @OneToMany(mappedBy = "host", orphanRemoval = true)
    private List<Reservation> invited = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "reservation", orphanRemoval = true)
    private List<Detail> details = new ArrayList<>();

    /* TODO:
        Specify the presentation format of days of the week, time for each day of the week.
     */

    public Reservation() {
    }

    public Reservation(Long id, Reservation host, List<Reservation> invited, User user, List<Detail> details) {
        this.id = id;
        this.host = host;
        this.invited = invited;
        this.user = user;
        this.details = details;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reservation getHost() {
        return host;
    }

    public void setHost(Reservation host) {
        this.host = host;
    }

    public List<Reservation> getInvited() {
        return invited;
    }

    public void setInvited(List<Reservation> invited) {
        this.invited = invited;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }
}
