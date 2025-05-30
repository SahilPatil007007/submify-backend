package net.springboot.submify.repository;


import net.springboot.submify.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,String> {

    Teacher findByEmail(String email);
}
