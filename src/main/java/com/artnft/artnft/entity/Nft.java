package com.artnft.artnft.entity;

import com.artnft.artnft.dto.Views;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "nft")
@Data
public class Nft {

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    User user;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "collection_id")
    @JsonView(Views.Base.class)
    Collection collection;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Sensitive.class)
    private Long id;
    @JsonView(Views.Base.class)
    private String name;
    @JsonView(Views.Base.class)
    private String qtype;
    @JsonView(Views.Base.class)
    private int serial;
    @JsonView(Views.Base.class)
    private String image;
    @JsonView(Views.Base.class)
    private Long totalEdition;
    @JsonView(Views.Base.class)
    private Long totalRarity;
    @JsonView(Views.Base.class)
    private String dropDate;
    @JsonView(Views.Base.class)
    private String listPrice;
    @JsonView(Views.Base.class)
    private String licence;
    @JsonView(Views.Sensitive.class)
    private boolean sellStatus = false;

}
