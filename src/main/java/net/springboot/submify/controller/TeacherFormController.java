package net.springboot.submify.controller;

import lombok.RequiredArgsConstructor;
import net.springboot.submify.dto.TeacherFormDto;
import net.springboot.submify.service.TeacherFormService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TeacherFormController {

    public final TeacherFormService teacherFormService;

    @PostMapping("/api/teacher/form")
    public ResponseEntity<String> handleSubmission(@RequestBody TeacherFormDto teacherFormDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String teacher =authentication.getName();
        boolean submit = teacherFormService.createEntry(teacherFormDto, teacher);

        if(submit){
            return new ResponseEntity<>("Changes saved",HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/api/teacher/form/subs")
    public ResponseEntity<?> getSubs(@RequestBody Map<String, Integer> data){
        int sem = data.get("sem");
        return teacherFormService.getSubs(sem);
    }
}
