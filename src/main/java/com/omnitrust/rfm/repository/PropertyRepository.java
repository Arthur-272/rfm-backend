package com.omnitrust.rfm.repository;

import com.omnitrust.rfm.domain.Property;
import com.omnitrust.rfm.domain.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends CrudRepository<Property, String> {
    List<Property> findAllByAuthorizedVehicles(List<Vehicle> authorizedVehicles);

    List<Property> findAllByAuthorizedVehiclesContaining(Vehicle vehicle);
}
