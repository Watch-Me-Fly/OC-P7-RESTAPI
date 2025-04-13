package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Getter @Setter
@Entity @Table(name = "bidlist")
public class BidList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bidListId;

    @Column(nullable = false)
    private String account;

    @Column(nullable = false)
    private String type;

    @Column
    private Double bidQuantity;

    @Column
    private Double askQuantity;

    @Column
    private Double bid;

    @Column
    private Double ask;

    @Column
    private String benchmark;

    @Column
    private Timestamp bidListDate;

    @Column
    private String commentary;

    @Column
    private String security;

    @Column
    private String status;

    @Column
    private String trader;

    @Column
    private String book;

    @Column
    private String creationName;

    @Column
    private Timestamp creationDate;

    @Column
    private String revisionName;

    @Column
    private Timestamp revisionDate;

    @Column
    private String dealName;

    @Column
    private String dealType;

    @Column
    private String sourceListId;

    @Column
    private String side;
}
