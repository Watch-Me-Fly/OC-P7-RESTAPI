package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
@Entity @Table(name = "BidList")
public class BidList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bidListId;

    @Column(nullable = false)
    @NotBlank(message = "Un compte est obligatoire")
    private String account;

    @Column(nullable = false)
    @NotBlank(message = "Un type est obligatoire")
    private String type;

    // @Digits(integer = 10, fraction = 2, message = "La quantité doit être une valeur numérique")
    @Column
    private Double bidQuantity;

    // @Digits(integer = 10, fraction = 2, message = "La quantité doit être une valeur numérique")
    @Column
    private Double askQuantity;

    // @Digits(integer = 10, fraction = 2, message = "Le montant doit être une valeur numérique")
    @Column
    private Double bid;

    // @Digits(integer = 10, fraction = 2, message = "Le montant doit être une valeur numérique")
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
