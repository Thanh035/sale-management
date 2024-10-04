package com.example.myapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.myapp.domain.entities.CustomerAddress;

@Repository
public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Long> {

}
