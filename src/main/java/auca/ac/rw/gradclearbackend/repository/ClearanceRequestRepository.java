package auca.ac.rw.gradclearbackend.repository;

import auca.ac.rw.gradclearbackend.model.ClearanceRequest;
import auca.ac.rw.gradclearbackend.model.ClearanceStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Modifying
    @Transactional
    @Query("""
    UPDATE ClearanceRequest cr
    SET cr.status = :rejected
    WHERE cr.officer.id = :officerId
    AND cr.requestDate = :date
    AND cr.status <> :approved
""")
    int cancelRequestForOfficerOnDate(
            @Param("officerId") UUID officerId,
            @Param("date") LocalDate date,
            @Param("rejected") ClearanceStatus rejected,
            @Param("approved") ClearanceStatus approved
    );
}
