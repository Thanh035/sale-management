package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.entities.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

	Optional<Group> findOneByCode(String code);

}
