package auca.ac.rw.gradclearbackend.service;

import auca.ac.rw.gradclearbackend.model.ClearanceRequest;
import auca.ac.rw.gradclearbackend.repository.ClearanceRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ClearanceRequestService {

    private final ClearanceRequestRepository clearanceRequestRepository;

    public ClearanceRequest save(ClearanceRequest clearanceRequest) {
        return clearanceRequestRepository.save(clearanceRequest);
    }

    public List<ClearanceRequest> findAll() {
        return clearanceRequestRepository.findAll();
    }

    public Optional<ClearanceRequest> find(UUID id) {
        return clearanceRequestRepository.findById(id);
    }

    public Optional<ClearanceRequest> update(UUID id, ClearanceRequest updated) {
        Optional<ClearanceRequest> existing = clearanceRequestRepository.findById(id);

        if (existing.isPresent()) {
            ClearanceRequest found = existing.get();

            found.setStatus(updated.getStatus());

            return Optional.of(clearanceRequestRepository.save(found));
        }

        return Optional.empty();
    }

    public boolean delete(UUID id) {
        Optional<ClearanceRequest> existing = clearanceRequestRepository.findById(id);

        if (existing.isPresent()) {
            clearanceRequestRepository.delete(existing.get());
            return true;
        }

        return false;
    }}
