package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
@Entity @Table(name = "trade")
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tradeId;

    @NotBlank(message = "Un compte est obligatoire")
    @Column
    private String account;

    @NotBlank(message = "Un type est obligatoire")
    @Column
    private String type;

    // @Digits(integer = 10, fraction = 2, message = "La quantité doit être une valeur numérique")
    @Column
    private Double buyQuantity;

    @Column
    private Double sellQuantity;

    @Column
    private Double buyPrice;

    @Column
    private Double sellPrice;

    @Column
    private Timestamp tradeDate;

    @Column
    private String security;

    @Column
    private String status;

    @Column
    private String trader;

    @Column
    private String benchmark;

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