package com.hcb.hotchairs.models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Comments")
public class Comment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "time_stamp")
    private Timestamp timestamp;

    @Column(name = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    public Comment() {
    }

    public Comment(Long id, User user, Timestamp timestamp, String text, Place place) {
        this.id = id;
        this.user = user;
        this.timestamp = timestamp;
        this.text = text;
        this.place = place;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
