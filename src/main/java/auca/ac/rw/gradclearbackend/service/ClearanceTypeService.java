package auca.ac.rw.gradclearbackend.service;

import auca.ac.rw.gradclearbackend.model.ClearanceType;
import auca.ac.rw.gradclearbackend.repository.ClearanceTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ClearanceTypeService {

    private final ClearanceTypeRepository clearanceTypeRepository;

    public ClearanceType save(ClearanceType clearanceType) {
        return clearanceTypeRepository.save(clearanceType);
    }

    public List<ClearanceType> findAll() {
        return clearanceTypeRepository.findAll();
    }

    public Optional<ClearanceType> find(UUID id) {
        return clearanceTypeRepository.findById(id);
    }

    public Optional<ClearanceType> update(UUID id, ClearanceType updated) {
        Optional<ClearanceType> existing = clearanceTypeRepository.findById(id);

        if (existing.isPresent()) {
            ClearanceType found = existing.get();

            found.setName(updated.getName());

            return Optional.of(clearanceTypeRepository.save(found));
        }

        return Optional.empty();
    }

    public boolean delete(UUID id) {
        Optional<ClearanceType> existing = clearanceTypeRepository.findById(id);

        if (existing.isPresent()) {
            clearanceTypeRepository.delete(existing.get());
            return true;
        }

        return false;
    }

}
