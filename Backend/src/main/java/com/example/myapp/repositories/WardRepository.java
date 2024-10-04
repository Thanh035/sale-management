package com.example.myapp.repositories;

import com.example.myapp.domain.entities.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardRepository extends JpaRepository<Ward, String> {
    List<Ward> findByDistrictCode(String districtCode);

    Ward findByCode(String code);
}
