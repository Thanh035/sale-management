package com.example.myapp.repositories;

import com.example.myapp.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime);

    Page<User> findAllByIdNotNullAndActivatedIsTrue(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.fullname LIKE %:filter% OR u.email LIKE %:filter% OR u.phoneNumber LIKE %:filter%")
    Page<User> findAllWithFilter(String filter, Pageable pageable);

    Optional<User> findOneByEmailIgnoreCase(String email);

    Optional<User> findOneByLogin(String login);

    @EntityGraph(attributePaths = "roles")
    Optional<User> findOneWithRolesByEmailIgnoreCase(String email);

    @EntityGraph(attributePaths = "roles")
    Optional<User> findOneWithRolesByLogin(String login);

    Optional<User> findOneByResetKey(String resetKey);

    boolean existsUserById(Long id);

    @EntityGraph(attributePaths = "roles")
    Optional<User> findOneWithRolesById(Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE User c SET c.profileImageId = ?1 WHERE c.id = ?2")
    int updateProfileImageId(String profileImageId, Long userId);

}
