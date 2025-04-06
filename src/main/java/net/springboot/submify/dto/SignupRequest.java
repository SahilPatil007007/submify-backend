package net.springboot.submify.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    private String id;
    private String name;
    private String email;
    private String password;
    private Set<String> roles;
}
