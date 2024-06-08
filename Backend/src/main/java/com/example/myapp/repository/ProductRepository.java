package com.example.myapp.repository;

import com.example.myapp.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByDeletedAtIsNull(Pageable pageable);

    @Query("SELECT u FROM Product u WHERE (unaccent(lower(u.productName)) LIKE unaccent(lower(concat('%', :filter, '%')))) AND u.deletedAt IS NULL")
    Page<Product> findAllWithFilter(String filter, Pageable pageable);

    boolean existsByIdAndDeletedAtIsNull(Long id);

    Optional<Product> findByIdAndDeletedAtIsNull(Long productId);

    @EntityGraph(attributePaths = "collections")
    Optional<Product> findOneWithCollectionsById(Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Product c SET c.productImageId = :productImageId WHERE c.id = :productId")
    void updateImageUrl(@Param("productImageId") String productImageId, @Param("productId") Long productId);

    List<Product> findAllByDeletedAtIsNull();

    List<Product> findAllByQuantityGreaterThanOrAllowOrdersTrue(int quantity);

}
