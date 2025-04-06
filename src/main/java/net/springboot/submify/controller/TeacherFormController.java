package net.springboot.submify.controller;

import lombok.RequiredArgsConstructor;
import net.springboot.submify.dto.TeacherFormDto;
import net.springboot.submify.service.TeacherFormService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TeacherFormController {

    public final TeacherFormService teacherFormService;

    @PostMapping("/api/teacher/form/{teacherId}")
    public ResponseEntity<String> handleSubmission(@RequestBody TeacherFormDto teacherFormDto,@PathVariable String teacherId){
        boolean submit = teacherFormService.createEntry(teacherFormDto, teacherId);

        if(submit){
            return new ResponseEntity<>("Changes saved",HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
