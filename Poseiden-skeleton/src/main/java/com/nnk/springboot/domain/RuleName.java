package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter @Getter
@Entity @Table(name = "rulename")
public class RuleName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotBlank(message = "Le nom est obligatoire")
    private String name;

    @Column
    @NotBlank(message = "La description est obligatoire")
    private String description;

    @Column
    @NotBlank(message = "Le champ JSON est obligatoire")
    private String json;

    @Column
    @NotBlank(message = "Le modèle est obligatoire")
    private String template;

    @Column
    @NotBlank(message = "La requête SQL est obligatoire")
    private String sqlStr;

    @Column
    @NotBlank(message = "La partie SQL est obligatoire")
    private String sqlPart;
}
