package auca.ac.rw.gradclearbackend.repository;

import auca.ac.rw.gradclearbackend.model.Department;
import auca.ac.rw.gradclearbackend.model.Officer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OfficerRepository extends JpaRepository<Officer, UUID> {
    @Query("""
    SELECT o 
    FROM Officer o
    JOIN o.clearanceTypes ct
    WHERE LOWER(ct.name) = LOWER(:name) 
""")
    List<Officer> findAllByClearanceTypeName(@Param("name") String name);

    boolean existsByDepartmentAndIdNot(Department department, UUID id);

}
