package net.springboot.submify.service;

import net.springboot.submify.entity.Teacher;
import net.springboot.submify.repository.TeacherRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeacherDetailsService implements UserDetailsService {

    private final TeacherRepository teacherRepository;

    public TeacherDetailsService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Teacher teacher = teacherRepository.findByEmail(email);
        if (teacher == null) {
            throw new UsernameNotFoundException("Teacher not found");
        }

        Set<GrantedAuthority> authorities = teacher.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleType().name()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                teacher.getEmail(),
                teacher.getPassword(),
                authorities
        );
    }
}