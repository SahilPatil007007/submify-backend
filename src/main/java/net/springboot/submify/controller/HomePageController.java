package net.springboot.submify.controller;

import lombok.RequiredArgsConstructor;
import net.springboot.submify.entity.SubjectDivision;
import net.springboot.submify.service.HomePageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomePageController {

    private final HomePageService subjectDivisionService;

    @GetMapping("/api/teacher/{teacherId}")
    public ResponseEntity<List<SubjectDivision>> getSubjectDivision(@PathVariable String teacherId) {

        List<SubjectDivision> subjectDivisions = subjectDivisionService.getSubjectDivisionsByTeacher(teacherId);
        return ResponseEntity.ok(subjectDivisions);
    }

}