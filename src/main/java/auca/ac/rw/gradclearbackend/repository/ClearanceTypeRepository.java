package auca.ac.rw.gradclearbackend.repository;

import auca.ac.rw.gradclearbackend.model.ClearanceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ClearanceTypeRepository extends JpaRepository<ClearanceType, UUID> {
    @Query("""
    SELECT ct
    FROM ClearanceType ct
    WHERE ct.officers IS EMPTY
""")
    public List<ClearanceType> findAllWithoutOfficers();
}
