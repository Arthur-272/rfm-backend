package com.omnitrust.rfm.domain;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Set;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonView({Views.Summary.class, Views.Detailed.class})
    private String id;

    @JsonView({Views.Summary.class, Views.Detailed.class})
    private String name;

    @JsonView({Views.Summary.class, Views.Detailed.class})
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;

    @ManyToMany
    @JsonView(Views.Detailed.class)
    private Set<Vehicle> authorizedVehicles;
}
