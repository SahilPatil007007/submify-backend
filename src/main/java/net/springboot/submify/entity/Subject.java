package net.springboot.submify.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    private int subjectCode;

    @Column(name = "name",nullable = false)
    private String subjectName;

    @Column(nullable = false)
    private int semester;
}
