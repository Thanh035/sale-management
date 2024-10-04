package com.example.myapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myapp.domain.entities.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, String> {

}
