package net.springboot.submify.service;

import lombok.RequiredArgsConstructor;
import net.springboot.submify.entity.SubjectDivision;
import net.springboot.submify.repository.SubjectDivisionRepository;
import net.springboot.submify.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomePageService {

    private final SubjectDivisionRepository subjectDivisionRepository;
    private final TeacherRepository teacherRepository;

    public List<SubjectDivision> getSubjectDivisionsByTeacher(String teacher) {
        String teacherId = teacherRepository.findByEmail(teacher).getId();
        return subjectDivisionRepository.findByTeacherId(teacherId);
    }
}