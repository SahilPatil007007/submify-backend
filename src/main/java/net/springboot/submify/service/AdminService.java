package net.springboot.submify.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.springboot.submify.repository.AllowedEmailRepository;
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
import java.time.LocalDateTime;
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
    private final SubjectRepository subjectRepository;

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

        Student student = studentRepository.findById(studentDTO.getStudentId())
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
    public void updateTeacher(TeacherDTO teacherDTO) {

        Teacher teacher = teacherRepository.findById(teacherDTO.getId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        teacher.setName(teacherDTO.getName());
        teacher.setEmail(teacherDTO.getEmail());
        if (teacherDTO.getPassword() != null && !teacherDTO.getPassword().isBlank()) {
            teacher.setPassword(passwordEncoder.encode(teacherDTO.getPassword()));
        }

        // Clear existing roles
        teacher.getRoles().clear();

        // Add new roles
        for (String s : teacherDTO.getRoles()) {
            Role role = new Role();
            role.setTeacher(teacher);

            if (s.equals("CLASS_COORDINATOR")) {
                role.setRoleType(RoleType.CLASS_COORDINATOR);
            } else if (s.equals("ADMIN")) {
                role.setRoleType(RoleType.ADMIN);
            }else if(s.equals("TEACHER")){
                role.setRoleType(RoleType.TEACHER);
            }

            teacher.getRoles().add(role);
        }

        // Save the teacher (cascade will handle roles)
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
                            .createdAt(LocalDateTime.now())
                            .build();

                    allowedEmailRepository.save(entry);
                    count++;
                }
            }
        }

        return count;
    }


    public int uploadDivisionsFromCSV(MultipartFile file) throws IOException{
        int count = 0;

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);

            for (CSVRecord record : records){
                String divisionId = record.get("division").trim();

                if(!divisionRepository.existsById(divisionId)){
                    Division division = Division.builder()
                            .division(divisionId)
                            .build();

                    divisionRepository.save(division);
                    count++;
                }
            }
        }
        return count;
    }


    @Transactional
    public void assignCoordinator(String divisionId, String teacherId){
        Division division = divisionRepository.findById(divisionId)
                .orElseThrow(() -> new RuntimeException("Division not found"));

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        division.setCoordinator(teacher);
        divisionRepository.save(division);
    }


    public int uploadBatchesFromCSV(MultipartFile file) throws IOException {
        int count = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(reader);

            for (CSVRecord record : records) {
                String batchName = record.get("batchName").trim();
                String divisionId = record.get("division").trim();

                if (!batchRepository.existsById(batchName)) {
                    Division division = divisionRepository.findById(divisionId)
                            .orElseThrow(() -> new RuntimeException("Division with ID " + divisionId + " not found"));

                    Batch batch = Batch.builder()
                            .batchName(batchName)
                            .division(division)
                            .build();

                    batchRepository.save(batch);
                    count++;
                }
            }
        }

        return count;
    }


    public int uploadSubjectsFromCSV(MultipartFile file) throws IOException {
        int count = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(reader);

            for (CSVRecord record : records) {
                int subjectCode = Integer.parseInt(record.get("subjectCode").trim());
                String subjectName = record.get("subjectName").trim();
                int semester = Integer.parseInt(record.get("semester").trim());

                if (!subjectRepository.existsById(subjectCode)) {
                    Subject subject = Subject.builder()
                            .subjectCode(subjectCode)
                            .subjectName(subjectName)
                            .semester(semester)
                            .build();

                    subjectRepository.save(subject);
                    count++;
                }
            }
        }

        return count;
    }


}
