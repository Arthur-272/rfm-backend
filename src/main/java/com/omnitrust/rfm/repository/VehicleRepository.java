package com.omnitrust.rfm.repository;

import com.omnitrust.rfm.domain.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle, String> {

    Optional<Vehicle> findByNumberPlate(String plate);

    Optional<Vehicle> findByIdAndNumberPlate(String id, String numberPlate);
}
