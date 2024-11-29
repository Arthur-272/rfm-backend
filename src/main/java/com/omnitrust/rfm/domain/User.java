package com.omnitrust.rfm.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "users")
@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long uuid;

    @Column(unique = true, length = 100, nullable = false)
    private String username;

    @Column(length = 100, nullable = false)
    private String password;
}
