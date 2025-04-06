package net.springboot.submify.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import net.springboot.submify.enums.RoleType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incremented ID
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Teacher teacher;  // Many roles can belong to one teacher

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roleType;
}
