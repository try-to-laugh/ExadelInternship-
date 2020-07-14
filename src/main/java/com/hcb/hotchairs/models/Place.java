package com.hcb.hotchairs.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Places")
public class Place {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "capacity")
    private Long capacity;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "PlaceRole",
            joinColumns = { @JoinColumn(name = "place_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )
    private List<Role> roles = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "PlaceTag",
            joinColumns = { @JoinColumn(name = "place_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") }
    )
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "floor_id")
    private Floor floor;

    @OneToMany(mappedBy = "place", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "place", orphanRemoval = true)
    private List<Detail> details = new ArrayList<>();

    public Place() {
    }

    public Place(Long id, String name, Long capacity, List<Role> roles, List<Tag> tags,
                 Floor floor, List<Comment> comments, List<Detail> details) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.roles = roles;
        this.tags = tags;
        this.floor = floor;
        this.comments = comments;
        this.details = details;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }
}

