package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findOneByCode(String code);

}
