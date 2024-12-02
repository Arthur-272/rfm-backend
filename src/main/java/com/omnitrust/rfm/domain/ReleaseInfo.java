package com.omnitrust.rfm.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class ReleaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    private Vehicle vehicle;

    @ManyToOne
    private Property property;

    @ManyToOne
    private User user;

    private Timestamp timestamp;
}
