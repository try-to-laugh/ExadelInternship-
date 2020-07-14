package com.hcb.hotchairs.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Email
    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "hr_id")
    private User hr;

    @OneToMany(mappedBy = "hr")
    private List<User> subordinates;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "UserRole",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )
    private List<Role> roles = new ArrayList<>();

    // TODO: Check, if we need here orphanRemoval.
    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    public User() {
    }

    public User(Long id, String name, @Email String email, User hr, List<User> subordinates,
                List<Role> roles, List<Comment> comments, List<Reservation> reservations) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.hr = hr;
        this.subordinates = subordinates;
        this.roles = roles;
        this.comments = comments;
        this.reservations = reservations;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getHr() {
        return hr;
    }

    public void setHr(User hr) {
        this.hr = hr;
    }

    public List<User> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(List<User> subordinates) {
        this.subordinates = subordinates;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
