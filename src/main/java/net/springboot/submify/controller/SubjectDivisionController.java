package net.springboot.submify.controller;

import lombok.RequiredArgsConstructor;
import net.springboot.submify.entity.SubjectDivision;
import net.springboot.submify.service.SubjectDivisionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SubjectDivisionController {

    private final SubjectDivisionService subjectDivisionService;

    @GetMapping("/{teacherId}")
    public ResponseEntity<List<SubjectDivision>> getSubjectDivision(@PathVariable String teacherId) {
        List<SubjectDivision> subjectDivisions = subjectDivisionService.getSubjectDivisionsByTeacher(teacherId);
        return ResponseEntity.ok(subjectDivisions);
    }

}