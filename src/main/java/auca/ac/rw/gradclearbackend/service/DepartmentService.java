package auca.ac.rw.gradclearbackend.service;

import auca.ac.rw.gradclearbackend.model.Department;
import auca.ac.rw.gradclearbackend.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    public Optional<Department> find(UUID id) {
        return departmentRepository.findById(id);
    }

    public Optional<Department> update(UUID id, Department updated) {
        Optional<Department> existing = departmentRepository.findById(id);

        if (existing.isPresent()) {
            Department found = existing.get();

            found.setName(updated.getName());

            return Optional.of(departmentRepository.save(found));
        }

        return Optional.empty();
    }

    public boolean delete(UUID id) {
        Optional<Department> existing = departmentRepository.findById(id);

        if (existing.isPresent()) {
            departmentRepository.delete(existing.get());
            return true;
        }

        return false;
    }
}
