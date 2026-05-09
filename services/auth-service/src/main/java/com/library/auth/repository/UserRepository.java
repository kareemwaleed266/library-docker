package com.library.auth.repository;

import com.library.auth.entity.User;
import com.library.auth.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);

    @Query("""
            select count(u)
            from User u
            where u.role.name = :roleName
              and u.isActive = true
            """)
    long countActiveUsersByRole(@Param("roleName") RoleName roleName);
}