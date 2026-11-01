package auca.ac.rw.gradclearbackend.repository;

import auca.ac.rw.gradclearbackend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    List<Student> findAllByLastName(String lastName);

    @Query("""
    SELECT DISTINCT cr.student
    FROM ClearanceRequest cr
    WHERE cr.officer.id = :officerId
""")
    List<Student> findStudentsByOfficerId(@Param("officerId") UUID officerId);
}
