package com.omnitrust.rfm.service;

import com.omnitrust.rfm.domain.Property;
import com.omnitrust.rfm.domain.Vehicle;
import com.omnitrust.rfm.dto.PropertyVehicleRequest;
import com.omnitrust.rfm.repository.PropertyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    AddressService addressService;

    @Autowired
    @Lazy
    private VehicleService vehicleService;

    public List<Property> getAll() {
        List<Property> properties = new ArrayList<Property>();
        propertyRepository.findAll().forEach(properties::add);
        return properties;
    }

    public List<Property> getAllActive() {
        return propertyRepository.findAllByIsActiveTrue();
    }

    public Property add(Property property) {
        if (addressService.getAddress(property.getAddress().getFullAddress()) == null) {
            property.getAddress().setId(null);
            property.setId(null);
            property.setIsActive(true);
            property.setAddress(addressService.saveAddress(property.getAddress()));
            property.setAuthorizedVehicles(new HashSet<>());
            return propertyRepository.save(property);
        } else {
            throw new IllegalArgumentException("Address " + property.getAddress().getFullAddress() + " already exists");
        }
    }

    public Property addVehicleToProperty(Property propertyToBeAddedIn, Vehicle vehicle) {
        System.out.println();
        Optional<Property> propertyInDB = propertyRepository.findById(propertyToBeAddedIn.getId());
        Vehicle authorizedVehicle = vehicleService.addVehicle(vehicle);
        if (authorizedVehicle != null) {
            if (propertyInDB.isPresent()) {
                propertyInDB.get().getAuthorizedVehicles().add(authorizedVehicle);
            } else {
                throw new IllegalArgumentException("Property " + propertyToBeAddedIn.getId() + " does not exist");
            }
        } else {
            throw new RuntimeException("Error adding vehicle to property " + propertyToBeAddedIn.getId());
        }
        return propertyRepository.save(propertyInDB.get());
    }

    public List<Property> findByAuthorizedVehicles(Vehicle vehicle) {
        return propertyRepository.findAllByAuthorizedVehiclesContaining(vehicle);
    }

    public Property findById(String id) {
        return propertyRepository.findById(id).get();
    }

    public void deletePropertyById(String id) throws Exception{
        Optional<Property> property = propertyRepository.findById(id);
        if (property.isPresent()) {
            property.get().setIsActive(false);
            propertyRepository.save(property.get());
        } else {
            throw new IllegalArgumentException("Property not found");
        }
    }

    public Property removeVehicleFromAuthorizedList(PropertyVehicleRequest request) throws Exception {
        Property property = propertyRepository.findById(request.getProperty().getId()).get();
        Vehicle vehicle = vehicleService.findByIdAndNumberPlate(request.getVehicle().getId(), request.getVehicle().getNumberPlate());
        if (property == null) {
            throw new IllegalArgumentException("Property not found");
        }
        if (property.getAuthorizedVehicles().contains(vehicle)) {
            property.getAuthorizedVehicles().remove(vehicle);
            return propertyRepository.save(property);
        }
        throw new IllegalArgumentException("Vehicle not found in property");
    }
}
