package com.omnitrust.rfm.domain;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "users")
@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long uuid;

    @Column(unique = true, length = 100, nullable = false)
    private String firstName;

    @Column(unique = true, length = 100)
    private String lastName;

    @Column(unique = true, length = 100, nullable = false)
    private String email;

    @Column(unique = true, length = 100, nullable = false)
    private String username;

    @Column(length = 100, nullable = false)
    private String password;
}
