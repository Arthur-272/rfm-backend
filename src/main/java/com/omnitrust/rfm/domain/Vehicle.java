package com.omnitrust.rfm.domain;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @JsonView({Views.Detailed.class, Views.Summary.class})
    private String numberPlate;

    @JsonView({Views.Detailed.class, Views.Summary.class})
    private String model;

    @JsonView({Views.Detailed.class, Views.Summary.class})
    private String color;
}
