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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeacherFormService {

    public final SubjectDivisionRepository subjectDivisionRepository;
    public final DivisionRepository divisionRepository;
    public final SubjectRepository subjectRepository;
    public final TeacherRepository teacherRepository;

    public boolean createEntry(TeacherFormDto teacherFormDto, String teacherId){
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
}