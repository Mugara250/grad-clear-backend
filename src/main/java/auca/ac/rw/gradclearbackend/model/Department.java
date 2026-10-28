package auca.ac.rw.gradclearbackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "department_number", nullable = false, unique = true)
    private Integer deptNumber;

    @Column(nullable = false)
    private String name;
}
