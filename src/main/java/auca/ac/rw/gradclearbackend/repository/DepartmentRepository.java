package auca.ac.rw.gradclearbackend.repository;

import auca.ac.rw.gradclearbackend.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {
}
