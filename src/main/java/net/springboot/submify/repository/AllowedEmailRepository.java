package net.springboot.submify.repository;

import net.springboot.submify.entity.AllowedEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AllowedEmailRepository extends JpaRepository<AllowedEmail, UUID> {
    boolean existsByEmail(String email);
}