package com.example.myapp.repositories;

import com.example.myapp.domain.entities.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, String> {
    void findOneByCode(String provinceCode);

    Province findByCode(String code);
}


