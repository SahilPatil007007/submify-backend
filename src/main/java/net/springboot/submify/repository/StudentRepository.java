package net.springboot.submify.repository;

import net.springboot.submify.dto.StudentSubmissionDTO;
import net.springboot.submify.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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


    @Query(value = """
    SELECT
            s.roll_no AS rollNo,
            subj.name AS subjectName,
            COALESCE(sub.status, false) AS submissionStatus,
            sub.remark AS remark,
            s.finalize_by_coordinator AS finalized
        FROM students s
        JOIN divisions d ON s.division_id = d.division
        JOIN subject_divisions sd ON sd.division_id = d.division
        JOIN subjects subj ON sd.subject_id = subj.subject_code
        LEFT JOIN submissions sub
            ON sub.student_id = s.student_id AND sub.subject_id = subj.subject_code
        WHERE d.coordinator_id = :coordinatorId
          AND subj.semester % 2 = CASE
              WHEN EXTRACT(MONTH FROM CURRENT_DATE) BETWEEN 6 AND 12 THEN 1
              ELSE 0
          END
        ORDER BY s.roll_no, subj.subject_code
""", nativeQuery = true)
    List<Object[]> getSubmissionStatusForCoordinator(@Param("coordinatorId") String coordinatorId);


    Optional<Student> findByRollNo(String rollNo);
}