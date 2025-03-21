package net.springboot.submify.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "divisions")
public class Division {

    @Id
    private String division;

    @Column(nullable = false)
    private String year;

    @Column(nullable = false)
    private int semester;

    @OneToOne
    @JoinColumn(name = "coordinator_id", referencedColumnName = "id", nullable = false)
    private Teacher coordinator;
}
