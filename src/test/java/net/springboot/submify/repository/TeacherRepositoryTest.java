package net.springboot.submify.repository;

import net.springboot.submify.entity.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@DataJpaTest
class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    public void saveTeacher() {
        Teacher teacher = Teacher.builder()
                .id("C2K221295")
                .name("Sahil")
                .email("ssp@gmail.com")
                .password("w31983d22!3")
                .build();

        teacherRepository.save(teacher);
    }
}