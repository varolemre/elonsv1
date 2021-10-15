package com.artnft.artnft.entity;

import com.artnft.artnft.dto.Views;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "nft")
@Data
public class Nft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Sensitive.class)
    private Long id;

    @JsonView(Views.Base.class)
    @NotNull(message = "Nft Name Can Not Be Empty!")
    private String name;

    @JsonView(Views.Base.class)
    @NotNull(message = "Nft Type Not Be Empty!")
    private String qtype;

    @JsonView(Views.Base.class)
    @NotNull(message = "Nft Serial Can Not Be Empty!")
    private int serial;

    @JsonView(Views.Base.class)
    private String image;

    @JsonView(Views.Sensitive.class)
    private boolean sellStatus=false;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name="user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    User user;

}
