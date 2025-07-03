package net.springboot.submify.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.springboot.submify.dto.AllowedEmailRepository;
import net.springboot.submify.dto.StudentDTO;
import net.springboot.submify.dto.TeacherDTO;
import net.springboot.submify.entity.*;
import net.springboot.submify.enums.RoleType;
import net.springboot.submify.repository.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final StudentRepository studentRepository;
    private final DivisionRepository divisionRepository;
    private final BatchRepository batchRepository;
    private final TeacherRepository teacherRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void saveStudentsFromCSV(MultipartFile file) throws IOException{
        try(Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(reader);


            for(CSVRecord record : records){
                String div = record.get("Division");
                Division d = divisionRepository.findById(div)
                        .orElseThrow(() -> new RuntimeException("Division with ID " + div + " not found"));

                String batch = record.get("Batch");
                Batch b = batchRepository.findById(batch)
                        .orElseThrow(() -> new RuntimeException("Batch name + " + batch + " not found"));

                Student student = Student.builder()
                        .studentId(record.get("ID"))
                        .rollNo(record.get("Roll No"))
                        .name(record.get("Name"))
                        .division(d)
                        .batchName(b)
                        .build();

                studentRepository.save(student);
            }
        }
    }


    public Student findStudentService(String sId){
        return studentRepository.findById(sId)
                .orElseThrow(() -> new RuntimeException("Student with ID " + sId + " not found"));
    }


    public Teacher findTeacher(String tId){
        return teacherRepository.findById(tId)
                .orElseThrow(() -> new RuntimeException("Teacher with ID " + tId + " not found"));
    }


    @Transactional
    public void updateStudent(StudentDTO studentDTO){

        Student student = studentRepository.findByRollNo(studentDTO.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Division division = divisionRepository.findById(studentDTO.getDivision())
                .orElseThrow(() -> new RuntimeException("Incorrect Division"));

        Batch batch = batchRepository.findById(studentDTO.getBatchName())
                        .orElseThrow(() -> new RuntimeException("Incorrect Batch Name"));

        student.setStudentId(studentDTO.getStudentId());
        student.setName(studentDTO.getName());
        student.setRollNo(studentDTO.getRollNo());
        student.setDivision(division);
        student.setBatchName(batch);

        studentRepository.save(student);
    }

    @Transactional
    public void updateTeacher(TeacherDTO teacherDTO){

        Teacher teacher = teacherRepository.findById(teacherDTO.getId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        teacher.setName(teacherDTO.getName());
        teacher.setId(teacherDTO.getId());
        teacher.setEmail(teacherDTO.getEmail());
        teacher.setPassword(passwordEncoder.encode(teacherDTO.getPassword()));

        Set<Role> roles = new HashSet<>();

        teacher.getRoles().clear();

        for(String s : teacherDTO.getRoles()) {
            Role role = new Role();

            role.setTeacher(teacher);
            if (s.equals("CLASS_COORDINATOR")) {
                role.setRoleType(RoleType.CLASS_COORDINATOR);
            } else if (s.equals("ADMIN")) {
                role.setRoleType(RoleType.ADMIN);
            }
            teacher.getRoles().add(role);
        }

        teacher.setRoles(roles);
        teacherRepository.save(teacher);
    }

    @Autowired
    private AllowedEmailRepository allowedEmailRepository;

    public int uploadAllowedEmailsFromCSV(MultipartFile file) throws IOException {
        int count = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(reader);

            for (CSVRecord record : records) {
                String email = record.get("email").trim().toLowerCase();

                if (!email.isEmpty() && !allowedEmailRepository.existsByEmail(email)) {
                    AllowedEmail entry = AllowedEmail.builder()
                            .email(email)
                            .addedBy("admin") // or current admin username if available
                            .build();

                    allowedEmailRepository.save(entry);
                    count++;
                }
            }
        }

        return count;
    }

}
