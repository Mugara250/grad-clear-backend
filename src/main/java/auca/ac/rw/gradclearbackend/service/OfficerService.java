package auca.ac.rw.gradclearbackend.service;

import auca.ac.rw.gradclearbackend.model.Officer;
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

    public Officer save(Officer officer) {
        return officerRepository.save(officer);
    }

    public List<Officer> findAll() {
        return officerRepository.findAll();
    }

    public Optional<Officer> find(UUID id) {
        return officerRepository.findById(id);
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
}
