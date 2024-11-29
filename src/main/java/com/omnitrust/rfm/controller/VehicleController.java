package com.omnitrust.rfm.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.omnitrust.rfm.domain.Vehicle;
import com.omnitrust.rfm.domain.Views;
import com.omnitrust.rfm.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/vehicle")
@CrossOrigin(origins = "*")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/get/all")
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    @GetMapping("/get")
    @JsonView(Views.Summary.class)
    public ResponseEntity<?> getVehicle(@RequestParam String plate) {
        try {
            Map<String, Object> map = vehicleService.getVehicleByNumberPlate(plate);
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
