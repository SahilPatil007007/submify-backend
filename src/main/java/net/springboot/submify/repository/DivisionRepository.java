package net.springboot.submify.repository;

import net.springboot.submify.entity.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DivisionRepository extends JpaRepository<Division, String> {

    Division findByCoordinator_Id(String coordinator_Id);
}