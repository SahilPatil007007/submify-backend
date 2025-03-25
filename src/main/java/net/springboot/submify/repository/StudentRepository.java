package net.springboot.submify.repository;

import net.springboot.submify.dto.StudentSubmissionDTO;
import net.springboot.submify.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    @Query("""
        SELECT new net.springboot.submify.dto.StudentSubmissionDTO(
            s.rollNo ,s.name, ut.ut1, ut.ut2, sub.remark, sub.status
        )
        FROM Student s
        JOIN UTMark ut ON s.studentId = ut.studentId.studentId
            AND ut.subject.subjectCode = :subjectId
        JOIN Submission sub ON s.studentId = sub.student.studentId
            AND sub.subject.subjectCode = :subjectId
        WHERE s.division.division = :divisionId
        AND sub.teacher.id = :teacherId
    """)
    List<StudentSubmissionDTO> findStudentDetails(
            @Param("teacherId") String teacherId,
            @Param("subjectId") int subjectId,
            @Param("divisionId") String divisionId
    );



}
