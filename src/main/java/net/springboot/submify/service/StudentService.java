package net.springboot.submify.service;

import lombok.RequiredArgsConstructor;
import net.springboot.submify.dto.StudentPivotSubmissionDTO;
import net.springboot.submify.dto.StudentSubmissionDTO;
import net.springboot.submify.dto.StudentUpdateDTO;
import net.springboot.submify.dto.SubmissionStatusDTO;
import net.springboot.submify.entity.*;
import net.springboot.submify.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final UTMarkRepository utMarkRepository;
    private final SubmissionRepository submissionRepository;
    private final SubjectRepository subjectRepository;

    public List<StudentSubmissionDTO> getStudentDetails(String teacher, int subjectId, String divisionId) {
        String teacherId = teacherRepository.findByEmail(teacher).getId();
        return studentRepository.findStudentDetails(teacherId, subjectId, divisionId);
    }


    public List<StudentPivotSubmissionDTO> getPivotSubmissionStatus(String teacher){
        String teacherId = teacherRepository.findByEmail(teacher).getId();
        List<Object[]> rawData = studentRepository.getSubmissionStatusForCoordinator(teacherId);

        Map<String, StudentPivotSubmissionDTO> studentMap = new LinkedHashMap<>();

        for (Object[] row : rawData){
            String rollNo = (String) row[0];
            String subject = (String) row[1];
            boolean status = (Boolean) row[2];
            String remark = (String) row[3];
            boolean finalized = (Boolean) row[4];

            studentMap.computeIfAbsent(rollNo, k ->
                new StudentPivotSubmissionDTO(k, new LinkedHashMap<>(), finalized)
            ).getSubjectStatuses().put(subject, new SubmissionStatusDTO(status, remark));
        }

        return new ArrayList<>(studentMap.values());
    }


    public void updateFinalizeStatus(String rollNo, boolean status) {
        Student student = studentRepository.findByRollNo(rollNo)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setFinalizeByCoordinator(status);
        studentRepository.save(student);
    }

    public void updateStudentRecord(StudentUpdateDTO dto, String teacher) {

        String teacherId = teacherRepository.findByEmail(teacher).getId();
        Teacher teacher1 = teacherRepository.findByEmail(teacher);

        Student student = studentRepository.findByRollNo(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        // Update UT Marks
        UTMark mark = utMarkRepository.findByStudentIdAndSubject(student, subject)
                .orElse(new UTMark()); // Create new if not exists

        mark.setStudentId(student);
        mark.setSubject(subject);
        mark.setUt1(dto.getUt1());
        mark.setUt2(dto.getUt2());
        utMarkRepository.save(mark);

        // Update Submission
        Submission sub = submissionRepository.findByStudentAndSubjectAndTeacher(
                student.getStudentId(), dto.getSubjectId(), teacherId
        ).orElse(new Submission());

        sub.setStudent(student);
        sub.setSubject(subject);
        sub.setTeacher(teacher1);
        sub.setRemark(dto.getRemark());
        sub.setStatus(dto.isStatus());
        sub.setLastEdited(LocalDateTime.now());

        submissionRepository.save(sub);
    }
}