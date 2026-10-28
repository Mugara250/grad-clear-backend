package auca.ac.rw.gradclearbackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clearance_request")
public class ClearanceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "request_date")
    private LocalDate requestDate = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private ClearanceStatus status;

    private String reason;
}
