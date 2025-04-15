package net.springboot.submify.dto;

import lombok.*;
import net.springboot.submify.entity.Teacher;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private Teacher user;
    private String token;
}
