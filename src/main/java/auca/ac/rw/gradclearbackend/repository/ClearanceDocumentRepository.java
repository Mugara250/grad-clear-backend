package auca.ac.rw.gradclearbackend.repository;

import auca.ac.rw.gradclearbackend.model.ClearanceDocument;
import auca.ac.rw.gradclearbackend.model.ClearanceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ClearanceDocumentRepository extends JpaRepository<ClearanceDocument, UUID> {

    @Query("""
    SELECT d
    FROM ClearanceDocument d
    WHERE d.clearanceRequest.student.regNumber = :regNumber
    ORDER BY d.issuedDate DESC
""")
    List<ClearanceDocument> findClearanceDocumentsByRegNumber(@Param("regNumber") String regNumber);

    boolean existsByClearanceRequest(ClearanceRequest clearanceRequest);
}
