package com.example.demo.repositories;

import com.example.demo.domain.entities.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardRepository extends JpaRepository<Ward, String> {
    List<Ward> findByDistrictCode(String districtCode);

    Ward findByCode(String code);
}
