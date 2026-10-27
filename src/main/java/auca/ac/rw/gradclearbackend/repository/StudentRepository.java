package auca.ac.rw.gradclearbackend.repository;

import auca.ac.rw.gradclearbackend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
}
