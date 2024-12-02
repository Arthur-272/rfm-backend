package com.omnitrust.rfm.service;

import com.omnitrust.rfm.domain.CatchInfo;
import com.omnitrust.rfm.domain.Property;
import com.omnitrust.rfm.domain.ReleaseInfo;
import com.omnitrust.rfm.domain.Vehicle;
import com.omnitrust.rfm.repository.ReleaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Service
public class ReleaseService {

    @Autowired
    private ReleaseRepository releaseRepository;

    @Autowired
    private CatchService catchService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private UserService userService;


    public List<ReleaseInfo> getReleaseInfoForDay(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        return releaseRepository.findReleaseInfoByTimestampBetweenAndUser((Timestamp.valueOf(startOfDay)), Timestamp.valueOf(endOfDay), userService.getCurrentUser());
    }

    public ReleaseInfo releaseVehicle(ReleaseInfo releaseInfo) {

        Property property = propertyService.findById(releaseInfo.getProperty().getId());
        Vehicle vehicle = (Vehicle) vehicleService.getVehicleByNumberPlate(releaseInfo.getVehicle().getNumberPlate()).get("vehicle");
        if (property != null) {
            if (vehicle != null) {
                if (catchService.isVehicleBooted(vehicle)) {
                    if (vehicle.getNumberPlate() != null) {

                        if(releaseInfo.getTimestamp() != null) {
                            Date date = Timestamp.valueOf(releaseInfo.getTimestamp().toLocalDateTime());
                            releaseInfo.setTimestamp(Timestamp.valueOf(date.toString()));
                        }


                        releaseInfo
                                .setVehicle(vehicle)
                                .setUser(userService.getCurrentUser())
                                .setProperty(property);
                        CatchInfo catchInfo = catchService.getCatchInfoForReleaseByNumberPlate(releaseInfo.getVehicle().getNumberPlate());
                        releaseInfo = releaseRepository.save(releaseInfo);

                        if(releaseInfo.getTimestamp().before(catchInfo.getTimestamp())) {
                            throw new IllegalArgumentException("Release date cannot be before catch date");
                        }

                        catchInfo.setReleaseInfo(releaseInfo);
                        catchService.updateCatch(catchInfo);
                        return releaseInfo;
                    } else {
                        throw new NullPointerException("Vehicle number plate is null");
                    }
                } else {
                    throw new IllegalArgumentException("Vehicle with number plate "+ vehicle.getNumberPlate() + " hasn't been booted");
                }
            } else {
                throw new IllegalArgumentException("Vehicle cannot be found in database");
            }
        } else {
            throw new IllegalArgumentException("Property not found in database");
        }
    }
}
