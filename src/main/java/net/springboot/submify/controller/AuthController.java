package net.springboot.submify.controller;

import net.springboot.submify.entity.Teacher;
import net.springboot.submify.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public Optional<Teacher> signup(@RequestBody Teacher teacher) {
        if(teacher == null){
            return null;
        }
        return authService.signup(teacher);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Teacher teacher) {
        return authService.login(teacher);
    }
}
