package com.omnitrust.rfm.service;

import com.omnitrust.rfm.domain.Address;
import com.omnitrust.rfm.repository.AddressRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public List<Address> getAddresses() {
        List<Address> addresses = new ArrayList<Address>();
        addressRepository.findAll().forEach(addresses::add);
        return addresses;
    }

    public Address getAddress(String fullAddress) {
        return addressRepository.findByFullAddress(fullAddress);
    }

    @Transactional
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }
}
