package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

//	@Query("SELECT u FROM Order u WHERE u.fullname LIKE %:filter% OR u.email LIKE %:filter% OR u.phoneNumber LIKE %:filter%")
//	Page<User> findAllWithFilter(String filter, Pageable pageable);

	Page<Order> findAllByStatusFalse(Pageable pageable);

	Page<Order> findAllByStatusTrue(Pageable pageable);

	@EntityGraph(attributePaths = "orderDetails")
	Optional<Order> findOneWithOrderDetailsById(Long id);

	@EntityGraph(attributePaths = {"orderDetails", "customer"})
	Optional<Order> findOneWithOrderDetailsAndCustomerById(Long id);

}
