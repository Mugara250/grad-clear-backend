package auca.ac.rw.gradclearbackend.repository;

import auca.ac.rw.gradclearbackend.model.Officer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OfficerRepository extends JpaRepository<Officer, UUID> {
}
