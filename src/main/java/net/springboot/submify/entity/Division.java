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

    @OneToOne
    @JoinColumn(name = "coordinator_id", referencedColumnName = "id")
    private Teacher coordinator;
}
