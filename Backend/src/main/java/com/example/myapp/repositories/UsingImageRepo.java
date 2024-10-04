package com.example.myapp.repositories;

import com.example.myapp.domain.entities.UsingImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsingImageRepo extends JpaRepository<UsingImage, Long> {
}
