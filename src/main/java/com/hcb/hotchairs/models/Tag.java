package com.hcb.hotchairs.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tag {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Place> places = new ArrayList<>();

    public Tag() {
    }

    public Tag(Long id, String name, List<Place> places) {
        this.id = id;
        this.name = name;
        this.places = places;
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

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }
}
