package com.omnitrust.rfm.repository;

import com.omnitrust.rfm.domain.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {

    Address findByFullAddress(String fullAddress);
}
