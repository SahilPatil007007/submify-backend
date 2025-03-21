    package net.springboot.submify.repository;


    import net.springboot.submify.entity.Teacher;
    import org.springframework.data.jpa.repository.JpaRepository;

    public interface TeacherRepository extends JpaRepository<Teacher,String> {

        Teacher findByEmail(String email);
    }
