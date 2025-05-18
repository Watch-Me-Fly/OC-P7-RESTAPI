package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@Entity @Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @NotBlank(message = "Username is mandatory")
    @Column
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Column
    private String password;

    @NotBlank(message = "FullName is mandatory")
    @Column
    private String fullname;

    @NotBlank(message = "Role is mandatory")
    @Column
    private String role;
}
