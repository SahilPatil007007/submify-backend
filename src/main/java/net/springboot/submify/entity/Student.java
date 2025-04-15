package net.springboot.submify.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "students")
public class Student {

    @Id
    private String studentId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String rollNo;

    @ManyToOne
    @JoinColumn(name = "division_id", referencedColumnName = "division", nullable = false)
    private Division division;

    @ManyToOne
    @JoinColumn(name = "batch_id", referencedColumnName = "batchName", nullable = false)
    private Batch batchName;

    private boolean finalizeByCoordinator = false;
}
