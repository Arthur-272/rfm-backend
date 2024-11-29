package com.omnitrust.rfm.controller;

import com.omnitrust.rfm.domain.CatchInfo;
import com.omnitrust.rfm.domain.Property;
import com.omnitrust.rfm.domain.ReleaseInfo;
import com.omnitrust.rfm.domain.Vehicle;
import com.omnitrust.rfm.service.CatchService;
import com.omnitrust.rfm.service.ReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catch-release")
public class CatchReleaseController {

    @Autowired
    private CatchService catchService;

    @Autowired
    private ReleaseService releaseService;

    @PostMapping("/catch")
    public ResponseEntity<?> addCatch(@RequestBody CatchInfo catch_Info) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(catchService.addCatch(catch_Info));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
    }

    @PostMapping("/release")
    public ResponseEntity<?> addRelease(@RequestBody ReleaseInfo release_Info) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(releaseService.releaseVehicle(release_Info));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
    }



    @PostMapping("/property/live")
    public ResponseEntity<?> getLiveStats(@RequestBody Property property) {
        try {
            List<Vehicle> vehicles = catchService.getAllBootedVehiclesByProperty(property);
            return ResponseEntity.status(HttpStatus.OK).body(vehicles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
    }

    @GetMapping("/live")
    public ResponseEntity<?> getLiveCatches() {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(catchService.getLiveCatches());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
    }

    @PostMapping("/remove")
    public ResponseEntity<?> removeCatch(@RequestBody CatchInfo catch_Info) {
        try {
            catchService.removeCatch(catch_Info);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(catch_Info);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
    }

}
