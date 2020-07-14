package com.hcb.hotchairs.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Floors")
public class Floor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "floor",orphanRemoval = true)
    private List<Place> places = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "office_id")
    private Office office;

    public Floor() {
    }

    public Floor(Long id, String name, Office office) {
        this.id = id;
        this.name = name;
        this.office = office;
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

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }
}
