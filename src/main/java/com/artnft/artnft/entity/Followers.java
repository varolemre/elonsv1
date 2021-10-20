package com.artnft.artnft.entity;

import com.artnft.artnft.dto.UserDto;
import com.artnft.artnft.dto.Views;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;

@Entity
public class Followers {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="from_user_fk",unique = true)
    @JsonIgnore
    private User from;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="to_user_fk")
    @JsonIgnoreProperties({ "password", "balance","id","mail" })
    private User to;

    public Followers() {};

    public Followers(User from, User to) {
        this.from = from;
        this.to = to;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }
}
