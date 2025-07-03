package net.springboot.submify.controller;

import lombok.RequiredArgsConstructor;
import net.springboot.submify.dto.AllowedEmailRepository;
import net.springboot.submify.dto.StudentDTO;
import net.springboot.submify.dto.TeacherDTO;
import net.springboot.submify.entity.AllowedEmail;
import net.springboot.submify.entity.Student;
import net.springboot.submify.entity.Teacher;
import net.springboot.submify.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final AllowedEmailRepository allowedEmailRepository;

    @GetMapping("/fill-student")
    public ResponseEntity<?> uploadCSV(@RequestParam("file") MultipartFile file){
        try{
            adminService.saveStudentsFromCSV(file);
            return ResponseEntity.ok("Upload Successfully");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload Fail");
        }
    }

    @GetMapping("/find-student")
    public ResponseEntity<?> findStudent(@RequestParam String studentId){
        try {
            Student s = adminService.findStudentService(studentId);
            if(s != null){
                return ResponseEntity.ok(s);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID not found");
        }
    }

    @GetMapping("/find-teacher")
    public ResponseEntity<?> getTeacher(@RequestParam String teacherId){
        try {
            Teacher t = adminService.findTeacher(teacherId);
            if(t != null){
                return ResponseEntity.ok(t);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID not found");
        }
    }

    @PutMapping("/update-student")
    public ResponseEntity<?> setStudent(@RequestBody StudentDTO studentDTO){
        try {
            adminService.updateStudent(studentDTO);
            return ResponseEntity.ok("Student Updated Successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update-teacher")
    public ResponseEntity<?> setTeacher(@RequestBody TeacherDTO teacherDTO){
        try {
            adminService.updateTeacher(teacherDTO);
            return ResponseEntity.ok("Teacher Updated Successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/allow-email")
    public ResponseEntity<?> allowEmail(@RequestParam String email) {
        if (allowedEmailRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body("Email already allowed.");
        }

        AllowedEmail entry = AllowedEmail.builder()
                .email(email.toLowerCase())
                .addedBy("admin@yourapp.com")
                .build();

        allowedEmailRepository.save(entry);
        return ResponseEntity.ok("Email added to allowed list.");
    }

    @PostMapping("/upload-allowed-emails")
    public ResponseEntity<?> uploadAllowedEmails(@RequestParam("file") MultipartFile file) {
        try {
            int count = adminService.uploadAllowedEmailsFromCSV(file);
            return ResponseEntity.ok(count + " emails added to allow list.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process CSV: " + e.getMessage());
        }
    }


}