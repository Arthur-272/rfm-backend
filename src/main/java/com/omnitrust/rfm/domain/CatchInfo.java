package com.omnitrust.rfm.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Accessors(chain = true)
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

    @CreationTimestamp
    @NonNull
    private Timestamp timestamp;

    @OneToOne
    private ReleaseInfo releaseInfo;
}
