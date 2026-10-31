package auca.ac.rw.gradclearbackend.repository;

import auca.ac.rw.gradclearbackend.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {
    @Query("""
    SELECT d
    FROM Department d
    WHERE NOT EXISTS (
        SELECT o
        FROM Officer o
        WHERE o.department = d
    )
    ORDER BY d.deptNumber ASC
""")
    List<Department> findDepartmentsWithoutOfficers();
}
