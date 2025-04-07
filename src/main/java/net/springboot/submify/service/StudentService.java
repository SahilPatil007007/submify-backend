package net.springboot.submify.service;

import lombok.RequiredArgsConstructor;
import net.springboot.submify.dto.StudentSubmissionDTO;
import net.springboot.submify.repository.StudentRepository;
import net.springboot.submify.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public List<StudentSubmissionDTO> getStudentDetails(String teacher, int subjectId, String divisionId) {
        String teacherId = teacherRepository.findByEmail(teacher).getId();
        System.out.println(teacherId);
        return studentRepository.findStudentDetails(teacherId, subjectId, divisionId);
    }
}