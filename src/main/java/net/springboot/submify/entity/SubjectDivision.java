package net.springboot.submify.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "subject_divisions")
public class SubjectDivision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "subjectCode", nullable = false)
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "division_id", referencedColumnName = "division", nullable = false)
    private Division division;  // Many subjectDivisions belong to one division

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id", nullable = false)
    private Teacher teacher;  // Many subjectDivisions belong to one teacher
}
