package net.springboot.submify.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "unit_test_marks")
public class UTMark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "studentId", nullable = false)
    private Student studentId;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "subjectCode", nullable = false)
    private Subject subject;

    private int ut2 = 0;
    private int ut1 = 0;
}
