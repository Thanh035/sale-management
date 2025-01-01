package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.entities.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, String> {

}
