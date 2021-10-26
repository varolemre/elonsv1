package com.artnft.artnft.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String licence;

    private String dropDate;

    private String year;

    private Long totalEdition;

    private Long listPrice;

    private Long todaySale;

    private Long volume;

    private String category;


}
