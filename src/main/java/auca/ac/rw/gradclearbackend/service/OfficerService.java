package auca.ac.rw.gradclearbackend.service;

import auca.ac.rw.gradclearbackend.model.ClearanceType;
import auca.ac.rw.gradclearbackend.model.Department;
import auca.ac.rw.gradclearbackend.model.Officer;
import auca.ac.rw.gradclearbackend.repository.ClearanceTypeRepository;
import auca.ac.rw.gradclearbackend.repository.DepartmentRepository;
import auca.ac.rw.gradclearbackend.repository.OfficerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OfficerService {

    private final OfficerRepository officerRepository;
    private final ClearanceTypeRepository clearanceTypeRepository;
    private final DepartmentRepository departmentRepository;

    public Officer save(Officer officer) {
        return officerRepository.save(officer);
    }

    public List<Officer> findAll() {
        return officerRepository.findAll();
    }

    public Optional<Officer> find(UUID id) {
        return officerRepository.findById(id);
    }

    public List<Officer> findAllByClearanceTypeName(String name) {
        return officerRepository.findAllByClearanceTypeName(name);
    }

    public boolean existsById(UUID officerId) {
        return officerRepository.existsById(officerId);
    }

    public Optional<Officer> update(UUID id, Officer updated) {
        Optional<Officer> existing = officerRepository.findById(id);

        if (existing.isPresent()) {
            Officer found = existing.get();

            found.setFirstName(updated.getFirstName());
            found.setLastName(updated.getLastName());
            found.setEmail(updated.getEmail());

            return Optional.of(officerRepository.save(found));
        }

        return Optional.empty();
    }

    public boolean delete(UUID id) {
        Optional<Officer> existing = officerRepository.findById(id);

        if (existing.isPresent()) {
            officerRepository.delete(existing.get());
            return true;
        }

        return false;
    }

    public String addClearanceType(UUID officerId, UUID clearanceTypeId) {
        Optional<Officer> existingOfficer = officerRepository.findById(officerId);

        if (existingOfficer.isEmpty()) {
            return "The officer with that id does not exist";
        }

        Optional<ClearanceType> existingClearanceType = clearanceTypeRepository.findById(clearanceTypeId);

        if (existingClearanceType.isEmpty()) {
            return "That clearance type does not exist";
        }

        Officer officer = existingOfficer.get();
        ClearanceType clearanceType = existingClearanceType.get();

        boolean alreadyAssigned = officer.getClearanceTypes()
                .stream()
                .anyMatch(ct -> ct.getId().equals(clearanceTypeId));

        if (alreadyAssigned) {
            return "The officer already has that clearance type";
        }

        officer.getClearanceTypes().add(clearanceType);

        officerRepository.save(officer);

        return "Clearance type added to the officer";
    }

    public String changeDepartment(UUID officerId, Integer deptNumber) {
        Optional<Officer> existingOfficer = officerRepository.findById(officerId);

        if (existingOfficer.isEmpty()) {
            return "The officer with that id does not exist";
        }

        Optional<Department> existingDepartment = departmentRepository.findByDeptNumber(deptNumber);

        if (existingDepartment.isEmpty()) {
            return "The department with that deptNumber does not exist";
        }

        Officer officer = existingOfficer.get();
        Department department = existingDepartment.get();

        if (officerRepository.existsByDepartmentAndIdNot(department, officerId)) {
            return "That department is taken by another officer";
        }

        officer.setDepartment(department);

        officerRepository.save(officer);

        return "Department changed successfully";
    }
}
