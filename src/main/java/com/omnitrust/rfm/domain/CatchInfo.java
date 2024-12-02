package com.omnitrust.rfm.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class CatchInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @NonNull
    private Vehicle vehicle;

    @ManyToOne
    @NonNull
    private Property property;

    @ManyToOne
    @NonNull
    private User user;

    private Timestamp timestamp;

    @OneToOne
    private ReleaseInfo releaseInfo;
}
