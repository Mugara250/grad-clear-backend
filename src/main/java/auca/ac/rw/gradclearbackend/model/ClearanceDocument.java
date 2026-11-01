package auca.ac.rw.gradclearbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clearance_document")
public class ClearanceDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "document_name", nullable = false)
    private String documentName;

    @Column(nullable = false)
    private String remarks;

    @Column(nullable = false)
    private Integer copies;

    @Column(name = "issued_date", nullable = false)
    private LocalDate issuedDate;

    @ManyToOne
    @JoinColumn(name = "clearance_request_id")
    private ClearanceRequest clearanceRequest;

}
