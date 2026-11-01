package auca.ac.rw.gradclearbackend.repository;

import auca.ac.rw.gradclearbackend.model.ClearanceDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClearanceDocumentRepository extends JpaRepository<ClearanceDocument, UUID> {
}
