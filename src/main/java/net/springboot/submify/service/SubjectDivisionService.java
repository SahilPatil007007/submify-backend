package net.springboot.submify.service;

import lombok.RequiredArgsConstructor;
import net.springboot.submify.entity.SubjectDivision;
import net.springboot.submify.repository.SubjectDivisionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectDivisionService {

    private final SubjectDivisionRepository subjectDivisionRepository;

    public List<SubjectDivision> getSubjectDivisionsByTeacher(String teacherId) {
        return subjectDivisionRepository.findByTeacherId(teacherId);
    }
}
