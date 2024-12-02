package com.omnitrust.rfm.service;

import com.omnitrust.rfm.domain.CatchInfo;
import com.omnitrust.rfm.domain.Property;
import com.omnitrust.rfm.domain.User;
import com.omnitrust.rfm.domain.Vehicle;
import com.omnitrust.rfm.repository.CatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CatchService {

    @Autowired
    private CatchRepository catchRepository;

    @Autowired
    @Lazy
    private VehicleService vehicleService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private UserService userService;


    public CatchInfo addCatch(CatchInfo catch_Info) {
        Property property = propertyService.findById(catch_Info.getProperty().getId());
        if(property == null) {
            throw new IllegalArgumentException("Property not found");
        }
        Vehicle vehicle = null;
        Map<String, Object> map = vehicleService.getVehicleByNumberPlate(catch_Info.getVehicle().getNumberPlate());
        if(map != null) {
            vehicle = (Vehicle) vehicleService.getVehicleByNumberPlate(catch_Info.getVehicle().getNumberPlate()).get("vehicle");
            if (property.getAuthorizedVehicles().contains(vehicle)) {
                throw new IllegalArgumentException("Vehicle is authorized");
            } else {
                if (isVehicleBooted(catch_Info.getVehicle())) {
                    throw new IllegalArgumentException("Vehicle is already booted");
                }
            }
        }
        if(vehicle == null) {
            vehicle = vehicleService.addVehicle(catch_Info.getVehicle());
        }

        if(catch_Info.getTimestamp() != null) {
            Date date = Timestamp.valueOf(catch_Info.getTimestamp().toLocalDateTime());
            catch_Info.setTimestamp(Timestamp.valueOf(date.toString()));
        }


        catch_Info
                .setVehicle(vehicle)
                .setReleaseInfo(null)
                .setProperty(property)
                .setUser(userService.getCurrentUser());

        return catchRepository.save(catch_Info);
    }

    public boolean isVehicleBooted(Vehicle vehicle) {
        return catchRepository.findCatchInfoByVehicle_NumberPlateAndReleaseInfoIsNull(vehicle.getNumberPlate()) != null;
    }

    public CatchInfo getCatchInfoForReleaseByNumberPlate(String numberPlate) {
        return catchRepository.findCatchInfoByVehicle_NumberPlateAndReleaseInfoIsNull(numberPlate);
    }

    public List<CatchInfo> getCatchInfoForDayByUser(LocalDate date) {

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        return catchRepository.findCatchInfoByTimestampBetweenAndUserAndReleaseInfoIsNotNull(Timestamp.valueOf(startOfDay), Timestamp.valueOf(endOfDay), userService.getCurrentUser());
    }

    public CatchInfo updateCatch(CatchInfo catchInfo) {
        return catchRepository.save(catchInfo);
    }

    public List<Vehicle> getAllBootedVehiclesByProperty(Property property) {
        property = propertyService.findById(property.getId());
        if (property == null) {
            throw new NullPointerException("Property not found");
        } else {
            List<CatchInfo> catchInfos = catchRepository.findCatchInfoByPropertyAndReleaseInfoIsNull(property);
            List<Vehicle> vehicles = new ArrayList<>();
            catchInfos.forEach(catchInfo -> {
                vehicles.add(catchInfo.getVehicle());
            });
            return vehicles;
        }
    }


    public List<CatchInfo> getLiveCatches() {
        return catchRepository.findCatchInfoByReleaseInfoIsNull();
    }

    public void removeCatch(CatchInfo catchInfo) {
        catchRepository.delete(catchInfo);
    }

    public List<CatchInfo> getAllCatchForDay(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        return catchRepository.findCatchInfoByTimestampBetweenAndReleaseInfoIsNotNull(Timestamp.valueOf(startOfDay), Timestamp.valueOf(endOfDay));
    }

    public List<CatchInfo> getAllCatchForDayWithReleaseInfoNotNull(LocalDate reportDate) {
        LocalDateTime startOfDay = reportDate.atStartOfDay();
        LocalDateTime endOfDay = reportDate.atTime(LocalTime.MAX);
        return catchRepository.findCatchInfoByReleaseInfoIsNotNullAndReleaseInfo_TimestampBetween(Timestamp.valueOf(startOfDay), Timestamp.valueOf(endOfDay));
    }

    public List<CatchInfo> getAllCatchForDayWithReleaseInfoNotNullByUser(LocalDate reportDate) {
        LocalDateTime startOfDay = reportDate.atStartOfDay();
        LocalDateTime endOfDay = reportDate.atTime(LocalTime.MAX);
        User currentUser = userService.getCurrentUser();
        return catchRepository.findCatchInfoByReleaseInfoIsNotNullAndReleaseInfo_TimestampBetweenAndUserOrReleaseInfo_User(Timestamp.valueOf(startOfDay), Timestamp.valueOf(endOfDay), currentUser);
    }
}
