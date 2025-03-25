package net.springboot.submify.service;

import net.springboot.submify.dto.StudentSubmissionDTO;
import net.springboot.submify.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<StudentSubmissionDTO> getStudentDetails(String teacherId, int subjectId, String divisionId) {
        return studentRepository.findStudentDetails(teacherId, subjectId, divisionId);
    }
}