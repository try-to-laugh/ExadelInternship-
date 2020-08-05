package com.hcb.hotchairs.entities;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

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
import java.sql.Time;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Reservations")
@TypeDef(
        name = "int-array",
        typeClass = IntArrayType.class
)
public class Reservation {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "host_id")
    private Reservation host;

    @OneToMany(mappedBy = "host", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Reservation> invited = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "start_timestamp")
    private Time startTime;

    @Column(name = "end_timestamp")
    private Time endTime;

    @Type(type = "int-array")
    @Column(name = "week_days", columnDefinition = "integer[]")
    private int[] weekDays;

    @OneToMany(mappedBy = "reservation", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Detail> details = new ArrayList<>();

    @OneToMany(mappedBy = "reservation", orphanRemoval = true, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Comment> comments = new ArrayList<>();
}
