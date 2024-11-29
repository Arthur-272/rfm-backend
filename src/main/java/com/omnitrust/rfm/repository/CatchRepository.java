package com.omnitrust.rfm.repository;

import com.omnitrust.rfm.domain.CatchInfo;
import com.omnitrust.rfm.domain.Property;
import com.omnitrust.rfm.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface CatchRepository extends CrudRepository<CatchInfo, String> {

    CatchInfo findCatchInfoByVehicle_NumberPlateAndReleaseInfoIsNull(String vehicleNumberPlate);

    List<CatchInfo> findCatchInfoByPropertyAndReleaseInfoIsNull(Property property);

    List<CatchInfo> findCatchInfoByReleaseInfoIsNull();

    List<CatchInfo> findCatchInfoByTimestampBetweenAndUserAndReleaseInfoIsNotNull(Timestamp timestamp, Timestamp timestamp1, User currentUser);

    List<CatchInfo> findCatchInfoByTimestampBetweenAndReleaseInfoIsNotNull(Timestamp timestamp, Timestamp timestamp1);
}
