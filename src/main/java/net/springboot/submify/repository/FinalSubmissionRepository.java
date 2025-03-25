package net.springboot.submify.repository;

import net.springboot.submify.entity.FinalSubmission;
import net.springboot.submify.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinalSubmissionRepository extends JpaRepository<FinalSubmission, Student> {
}
