package net.springboot.submify.repository;

import net.springboot.submify.entity.Role;
import net.springboot.submify.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    List<Role>  findByTeacherId(Teacher teacherId);
}
