package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter @Getter
@Entity @Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotBlank(message = "La notation Moody's est obligatoire")
    private String moodysRating;

    @Column
    @NotBlank(message = "La notation S&P est obligatoire")
    private String sandPRating;

    @Column
    @NotBlank(message = "La notation Fitch est obligatoire")
    private String fitchRating;

    @Column
    @NotNull(message = "Le numéro de la commande est obligatoire")
    private Integer orderNumber;
}
