package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

//	@Query("SELECT u FROM Customer u WHERE u.name LIKE %:filter% OR u. LIKE %:filter% OR u.phoneNumber LIKE %:filter%")
//	Page<Customer> findAllWithFilter(String filter, Pageable pageable);

	Optional<Customer> findOneByEmail(String email);

	Optional<Customer> findOneByMobile(String mobile);

	boolean existsCustomerByMobile(String mobile);

	boolean existsCustomerByEmail(String email);

}
