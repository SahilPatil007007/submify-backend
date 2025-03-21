package net.springboot.submify.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "batches")
public class Batch {

    @Id
    private String batchName;

    @ManyToOne
    @JoinColumn(name = "division_id", referencedColumnName = "division", nullable = false)
    private Division division;
}