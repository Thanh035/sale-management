package com.example.myapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myapp.domain.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findOneByCode(String code);

}
