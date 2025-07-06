package net.springboot.submify.repository;

import net.springboot.submify.entity.Division;
import net.springboot.submify.entity.Subject;
import net.springboot.submify.entity.SubjectDivision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectDivisionRepository extends JpaRepository<SubjectDivision, Integer> {

    List<SubjectDivision> findByTeacherId(String teacherId);
    boolean existsByDivisionAndSubject(Division division, Subject subject);

}
