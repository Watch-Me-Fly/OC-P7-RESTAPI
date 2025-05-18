package com.nnk.springboot.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Getter @Setter
@Entity @Table(name = "curvepoint")
public class CurvePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer curveId;

    @Column
    private Timestamp asOfDate;

    @Column
    @NotNull(message = "le term ne doit pas être nul")
    private Double term;

    @Column
    @NotNull(message = "La valeur est obligatoire")
    private Double value;

    @Column
    private Timestamp creationDate;
}
