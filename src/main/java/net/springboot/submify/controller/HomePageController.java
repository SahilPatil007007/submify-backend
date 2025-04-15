package net.springboot.submify.controller;

import lombok.RequiredArgsConstructor;
import net.springboot.submify.entity.Division;
import net.springboot.submify.entity.SubjectDivision;
import net.springboot.submify.service.HomePageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomePageController {

    private final HomePageService homePageService;

    @GetMapping("/api/teacher")
    public ResponseEntity<List<SubjectDivision>> getSubjectDivision() {
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        String teacher =authentication.getName();
        List<SubjectDivision> subjectDivisions = homePageService.getSubjectDivisionsByTeacher(teacher);
        return ResponseEntity.ok(subjectDivisions);
    }

    @GetMapping("/api/teacher/coordinator")
    public ResponseEntity<Division> getCoordinator(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String teacher = authentication.getName();
        Division division = homePageService.getDivisionCoordinator(teacher);

        if(division != null){
            return new ResponseEntity<>(division,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}