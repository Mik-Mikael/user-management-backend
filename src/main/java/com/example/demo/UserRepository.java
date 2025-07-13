package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Marks this as a Spring Data repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // JpaRepository<EntityType, PrimaryKeyType>
    // You can add custom query methods here if needed, e.g.:
    // Optional<User> findByUsername(String username);

    @Query("SELECT u FROM UserEntity u WHERE " +
            "CAST(u.id AS string) LIKE %:term% OR " +
            "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(u.phoneNumber) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(u.idCard) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(u.role) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(u.position) LIKE LOWER(CONCAT('%', :term, '%'))")
    List<UserEntity> searchByTerm(@Param("term") String term);

    boolean existsByIdCard(String idCard);
    boolean existsByIdCardAndIdNot(String idCard, Long id);

    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);

    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);
}