package com.omnitrust.rfm.service;

import com.omnitrust.rfm.domain.Property;
import com.omnitrust.rfm.domain.Vehicle;
import com.omnitrust.rfm.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private CatchService catchService;

    public List<Vehicle> getAllVehicles() {
        List<Vehicle> authorizedVehicles = new ArrayList<Vehicle>();
        vehicleRepository.findAll().forEach(authorizedVehicles::add);
        return authorizedVehicles;
    }

    public Map<String, Object> getVehicleByNumberPlate(String plate) {
        if(plate == null) {
            throw new NullPointerException("plate cannot be null");
        }

        Optional<Vehicle> vehicle = vehicleRepository.findByNumberPlate(plate);
        if(vehicle.isPresent()) {
            List<Property> properties = propertyService.findByAuthorizedVehicles(vehicle.get());

            Map<String, Object> result = new HashMap<>();
            result.put("properties", properties);
            result.put("vehicle", vehicle.get());
            return result;
        } else {
            return null;
        }
    }

    public Vehicle addVehicle(Vehicle vehicle) {
        if(vehicle == null) {
            throw new IllegalArgumentException("vehicle is cannot be null");
        }
        vehicle = vehicleRepository.save(vehicle);
        return vehicle;
    }
}
