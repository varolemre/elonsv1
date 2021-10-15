package com.artnft.artnft.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "TransferLog")
@Data
public class TransferLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    private String wallet;

    private Long amount;

    @Basic
    private LocalDateTime utilDate;

    @Basic
    private Date utilTime;

    private String status;
}

