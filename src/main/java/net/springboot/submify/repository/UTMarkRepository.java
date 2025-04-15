package net.springboot.submify.repository;

import net.springboot.submify.entity.Student;
import net.springboot.submify.entity.Subject;
import net.springboot.submify.entity.UTMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UTMarkRepository extends JpaRepository<UTMark, Long> {
    Optional<UTMark> findByStudentIdAndSubject(Student student, Subject subject);
}
