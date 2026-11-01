package auca.ac.rw.gradclearbackend.repository;

import auca.ac.rw.gradclearbackend.model.ClearanceRequest;
import auca.ac.rw.gradclearbackend.model.ClearanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ClearanceRequestRepository extends JpaRepository<ClearanceRequest, UUID> {
    List<ClearanceRequest> findAllByStatus(ClearanceStatus status);

    List<ClearanceRequest> findAllByRequestDateBetween(LocalDate requestDateAfter, LocalDate requestDateBefore);

    boolean existsByOfficerIdAndRequestDateAndStatusNot(
            UUID officerId, LocalDate requestDate, ClearanceStatus status);

    @Query("""
    SELECT cr.status, COUNT(cr)
    FROM ClearanceRequest cr
    GROUP BY cr.status
""")
    List<Object[]> countClearanceRequestsByStatus();
}
