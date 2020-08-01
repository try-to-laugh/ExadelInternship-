package com.hcb.hotchairs.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Floors")
public class Floor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number")
    private Integer number;

    @OneToMany(mappedBy = "floor",orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Place> places = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "office_id")
    private Office office;

    @Basic
    @Column(name = "map")
    private byte[] map;

    @Basic
    @Column(name = "plan")
    private byte[] plan;
}
