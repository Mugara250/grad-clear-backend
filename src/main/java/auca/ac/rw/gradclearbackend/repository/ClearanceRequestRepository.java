package auca.ac.rw.gradclearbackend.repository;

import auca.ac.rw.gradclearbackend.model.ClearanceRequest;
import auca.ac.rw.gradclearbackend.model.ClearanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClearanceRequestRepository extends JpaRepository<ClearanceRequest, UUID> {
    List<ClearanceRequest> findAllByStatus(ClearanceStatus status);
}
