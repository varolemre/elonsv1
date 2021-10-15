package com.artnft.artnft.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class TradeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @OneToOne
    private Nft nft;

    @Basic
    private LocalDateTime utilDate;

    @Basic
    private Date utilTime;

    private String status;
}

