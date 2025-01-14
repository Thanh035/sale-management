package com.example.demo.repositories;

import com.example.demo.domain.entities.UsingImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsingImageRepo extends JpaRepository<UsingImage, Long> {
}
