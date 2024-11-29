package com.omnitrust.rfm.domain;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @JsonView({Views.Summary.class})
    @Column(unique = true)
    private String fullAddress;

    @JsonView({Views.Detailed.class})
    private String streetAddress;

    @JsonView({Views.Detailed.class})
    private String city;

    @JsonView({Views.Detailed.class})
    private String state;

    @JsonView({Views.Detailed.class})
    private String postalCode;

    @JsonView({Views.Detailed.class})
    private String country;
}
