package net.springboot.submify.controller;

import lombok.RequiredArgsConstructor;
import net.springboot.submify.dto.StudentPivotSubmissionDTO;
import net.springboot.submify.dto.StudentSubmissionDTO;
import net.springboot.submify.dto.StudentUpdateDTO;
import net.springboot.submify.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/pivot-submission")
    public ResponseEntity<List<StudentPivotSubmissionDTO>> getPivotSubmissions(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String teacher =authentication.getName();

        List<StudentPivotSubmissionDTO> data = studentService.getPivotSubmissionStatus(teacher);
        return ResponseEntity.ok(data);
    }

    @PutMapping("/{rollNo}/finalize")
    public ResponseEntity<?> updateFinalizeStatus(@PathVariable String rollNo,
                                                  @RequestBody Map<String, Boolean> payload){
        boolean newStatus = payload.get("finalized");
        studentService.updateFinalizeStatus(rollNo, newStatus);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/update-student")
    public ResponseEntity<?> updateStudent(@RequestBody StudentUpdateDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String teacher =authentication.getName();

        studentService.updateStudentRecord(dto, teacher);
        return ResponseEntity.ok("Student data updated");
    }
}
