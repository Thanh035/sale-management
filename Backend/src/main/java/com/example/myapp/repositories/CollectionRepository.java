package com.example.myapp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.myapp.domain.entities.Collection;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Integer> {

	boolean existsById(Integer id);

	@Query("SELECT u FROM Collection u WHERE (unaccent(lower(u.name)) LIKE unaccent(lower(concat('%', :filter, '%'))))")
	Page<Collection> findAllWithFilter(String filter, Pageable pageable);

	List<Collection> findByProducts_Id(Long productId);

	Optional<Collection> findOneById(Integer id);
}
