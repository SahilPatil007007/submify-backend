package net.springboot.submify.repository;

import net.springboot.submify.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    @Query("""
        SELECT s FROM Submission s
        WHERE s.student.studentId = :studentId AND s.subject.subjectCode = :subjectId AND s.teacher.id = :teacherId
    """)
    Optional<Submission> findByStudentAndSubjectAndTeacher(
            @Param("studentId") String studentId,
            @Param("subjectId") int subjectId,
            @Param("teacherId") String teacherId
    );
}
