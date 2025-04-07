package net.springboot.submify.controller;

import lombok.RequiredArgsConstructor;
import net.springboot.submify.dto.StudentSubmissionDTO;
import net.springboot.submify.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teacher/students")
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/submissions")
    public ResponseEntity<List<StudentSubmissionDTO>> getStudentSubmissions(
            @RequestParam int subjectId,
            @RequestParam String divisionId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String teacher =authentication.getName();
        List<StudentSubmissionDTO> students = studentService.getStudentDetails(teacher, subjectId, divisionId);
        return ResponseEntity.ok(students);
    }
}
