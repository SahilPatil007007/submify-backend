package net.springboot.submify.dto;

import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Data
public class TeacherDTO {
    private String id;
    private String name;
    private String email;
    private String password;
    private Set<String> roles = new HashSet<>();
}