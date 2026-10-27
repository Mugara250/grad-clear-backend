package auca.ac.rw.gradclearbackend.repository;

import auca.ac.rw.gradclearbackend.model.ClearanceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClearanceTypeRepository extends JpaRepository<ClearanceType, UUID> {
}
