package net.springboot.submify.service;

import lombok.RequiredArgsConstructor;
import net.springboot.submify.entity.Division;
import net.springboot.submify.entity.SubjectDivision;
import net.springboot.submify.repository.DivisionRepository;
import net.springboot.submify.repository.SubjectDivisionRepository;
import net.springboot.submify.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomePageService {

    private final SubjectDivisionRepository subjectDivisionRepository;
    private final TeacherRepository teacherRepository;
    private final DivisionRepository divisionRepository;

    public List<SubjectDivision> getSubjectDivisionsByTeacher(String teacher) {
        String teacherId = teacherRepository.findByEmail(teacher).getId();
        return subjectDivisionRepository.findByTeacherId(teacherId);
    }

    public Division getDivisionCoordinator(String teacher){
        String teacherId = teacherRepository.findByEmail(teacher).getId();
        return divisionRepository.findByCoordinator_Id(teacherId);
    }
}