package net.springboot.submify.repository;

import net.springboot.submify.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchRepository extends JpaRepository<Batch, String> {
}
