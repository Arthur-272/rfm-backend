package com.omnitrust.rfm.repository;

import com.omnitrust.rfm.domain.ReleaseInfo;
import com.omnitrust.rfm.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ReleaseRepository extends CrudRepository <ReleaseInfo, String>{

    List<ReleaseInfo> findReleaseInfoByTimestampBetween(Timestamp timestamp, Timestamp timestamp2);

    List<ReleaseInfo> findReleaseInfoByTimestampBetweenAndUser(Timestamp timestampAfter, Timestamp timestampBefore, User user);
}
