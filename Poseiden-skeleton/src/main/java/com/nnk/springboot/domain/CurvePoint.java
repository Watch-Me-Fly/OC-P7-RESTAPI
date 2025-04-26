package com.nnk.springboot.domain;

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
    private Double term;

    @Column
    private Double value;

    @Column
    private Timestamp creationDate;
}
