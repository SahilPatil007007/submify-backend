package net.springboot.submify.service;

import net.springboot.submify.dto.LoginResponse;
import net.springboot.submify.entity.Role;
import net.springboot.submify.entity.Teacher;
import net.springboot.submify.enums.RoleType;
import net.springboot.submify.repository.RoleRepository;
import net.springboot.submify.repository.TeacherRepository;
import net.springboot.submify.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final TeacherRepository teacherRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(TeacherRepository teacherRepository, RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.teacherRepository = teacherRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public Optional<Teacher> signup(Teacher teacher){
        if (teacherRepository.findByEmail(teacher.getEmail()) != null) {
            throw new RuntimeException("Email already exists!");
        }

        // Encode the password before saving
        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));

        // Save the teacher first
        Teacher savedTeacher = teacherRepository.save(teacher);

        // Set the default role
        Role defaultRole = new Role();
        defaultRole.setRoleType(RoleType.TEACHER);
        defaultRole.setTeacher(savedTeacher);
        roleRepository.save(defaultRole);

        Set<Role> roles = new HashSet<>();
        roles.add(defaultRole);
        savedTeacher.setRoles(roles);
        return Optional.of(teacherRepository.save(savedTeacher));
    }

    public ResponseEntity<?> login(Teacher teacher) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            teacher.getEmail(), teacher.getPassword()
                    )
            );
            UserDetails userDetails;
            userDetails = (UserDetails) authenticate.getPrincipal();
            String token;
            token = jwtUtil.generateToken(userDetails.getUsername());

            // Fetch the actual teacher entity
            Teacher loggedInTeacher = teacherRepository.findByEmail(userDetails.getUsername());

            // Avoid sending password in response (optional but recommended)
            loggedInTeacher.setPassword(null);

            LoginResponse loginResponse = new LoginResponse(loggedInTeacher, token);
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Login unSuccessful", HttpStatus.BAD_REQUEST);
        }
    }
}
