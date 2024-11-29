package com.omnitrust.rfm.dto;

import com.omnitrust.rfm.domain.Property;
import com.omnitrust.rfm.domain.Vehicle;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class PropertyVehicleRequest {

    private Property property;
    private Vehicle vehicle;
}
