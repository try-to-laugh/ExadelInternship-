package com.hcb.hotchairs.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Offices")
public class Office {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street")
    private String street;

    @OneToMany(mappedBy = "office", orphanRemoval = true)
    private List<Floor> floors = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    public Office() {
    }

    public Office(Long id, String street, List<Floor> floors, City city) {
        this.id = id;
        this.street = street;
        this.floors = floors;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
