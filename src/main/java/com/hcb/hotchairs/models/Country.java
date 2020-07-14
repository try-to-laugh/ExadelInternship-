package com.hcb.hotchairs.models;

import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Countries")
public class Country {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "country", orphanRemoval = true)
    private List<City> cities = new ArrayList<>();

    @URL
    @Column(name = "pictureLink")
    private String pictureLink;

    public Country() {
    }

    public Country(Long id, String name, List<City> cities, @URL String pictureLink) {
        this.id = id;
        this.name = name;
        this.cities = cities;
        this.pictureLink = pictureLink;
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

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }
}
