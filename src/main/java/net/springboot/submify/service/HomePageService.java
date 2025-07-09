package net.springboot.submify.service;

import lombok.RequiredArgsConstructor;
import net.springboot.submify.entity.Division;
import net.springboot.submify.entity.SubjectDivision;
import net.springboot.submify.repository.DivisionRepository;
import net.springboot.submify.repository.SubjectDivisionRepository;
import net.springboot.submify.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomePageService {

    private final SubjectDivisionRepository subjectDivisionRepository;
    private final TeacherRepository teacherRepository;
    private final DivisionRepository divisionRepository;

    public List<SubjectDivision> getSubjectDivisionsByTeacher(String teacherEmail) {
        String teacherId = teacherRepository.findByEmail(teacherEmail).getId();
        List<SubjectDivision> all = subjectDivisionRepository.findByTeacherId(teacherId);

        int currentMonth = LocalDateTime.now().getMonthValue();
        boolean isOddSemester = currentMonth >= 6;

        return all.stream()
                .filter(sd -> {
                    int sem = sd.getSubject().getSemester();
                    return isOddSemester ? (sem % 2 == 1) : (sem % 2 == 0);
                })
                .toList();
    }

    public Division getDivisionCoordinator(String teacher){
        String teacherId = teacherRepository.findByEmail(teacher).getId();
        return divisionRepository.findByCoordinator_Id(teacherId);
    }
}