package net.springboot.submify.service;

import lombok.RequiredArgsConstructor;
import net.springboot.submify.dto.TeacherFormDto;
import net.springboot.submify.entity.Division;
import net.springboot.submify.entity.Subject;
import net.springboot.submify.entity.SubjectDivision;
import net.springboot.submify.entity.Teacher;
import net.springboot.submify.repository.DivisionRepository;
import net.springboot.submify.repository.SubjectDivisionRepository;
import net.springboot.submify.repository.SubjectRepository;
import net.springboot.submify.repository.TeacherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherFormService {

    private final SubjectDivisionRepository subjectDivisionRepository;
    private final DivisionRepository divisionRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;

    public boolean createEntry(TeacherFormDto teacherFormDto, String teacher){
        String teacherId = teacherRepository.findByEmail(teacher).getId();
        String divId = teacherFormDto.getDiv();
        String sub = teacherFormDto.getSub();

        if(divId != null && sub != null){
            Optional<Division> divisionOpt = divisionRepository.findById(divId);
            Optional<Subject> subjectOpt = subjectRepository.findBySubjectName(sub);
            Optional<Teacher> teacherOpt = teacherRepository.findById(teacherId);

            if (divisionOpt.isPresent() && subjectOpt.isPresent() && teacherOpt.isPresent()){
                SubjectDivision subjectDivision = SubjectDivision.builder()
                        .division(divisionOpt.get())
                        .subject(subjectOpt.get())
                        .teacher(teacherOpt.get())
                        .build();

                subjectDivisionRepository.save(subjectDivision);
                return true;
            }
        }
        return false;
    }

    public ResponseEntity<?> getSubs(int sem){
        try {
            List<Subject> subject = subjectRepository.findAllBySemester(sem);

            if(subject != null) {
                List<String> subjectNames = subject.stream()
                        .map(Subject::getSubjectName)
                        .collect(Collectors.toList());
                return new ResponseEntity<>(subjectNames, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }
}