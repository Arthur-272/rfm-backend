package com.omnitrust.rfm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omnitrust.rfm.domain.Property;
import com.omnitrust.rfm.dto.PropertyVehicleRequest;
import com.omnitrust.rfm.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property")
@CrossOrigin(origins = "*")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/get/all")
    public ResponseEntity<?> getAllProperty() {
        try {
            List<Property> properties = propertyService.getAll();
            return new ResponseEntity<>(properties, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProperty(@RequestBody Property property) {
        try {
            return new ResponseEntity<>(propertyService.add(property), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/update")
    public ResponseEntity<?> updateProperty(@RequestBody PropertyVehicleRequest request) {
        try {
            Property property = propertyService.addVehicleToProperty(request.getProperty(), request.getVehicle());
            return new ResponseEntity<>(property, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
