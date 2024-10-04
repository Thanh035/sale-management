package com.example.myapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.myapp.domain.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

}
