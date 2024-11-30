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
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/get/all")
    public ResponseEntity<?> getAllProperty() {
        try {
            List<Property> properties = propertyService.getAllActive();
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

    @GetMapping("/get")
    public ResponseEntity<?> getPropertyById(@RequestParam String id) {
        try {
            Property property = propertyService.findById(id);
            return new ResponseEntity<>(property, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deletePropertyById(@RequestBody Property property) {
        try{
            propertyService.deletePropertyById(property.getId());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/remove-vehicle")
    public ResponseEntity<?> removeVehicleFromAuthorizedList(@RequestBody PropertyVehicleRequest request) {
        try{
            propertyService.removeVehicleFromAuthorizedList(request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
