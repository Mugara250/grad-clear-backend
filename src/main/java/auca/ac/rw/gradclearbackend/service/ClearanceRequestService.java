package auca.ac.rw.gradclearbackend.service;

import auca.ac.rw.gradclearbackend.model.ClearanceRequest;
import auca.ac.rw.gradclearbackend.model.ClearanceStatus;
import auca.ac.rw.gradclearbackend.repository.ClearanceRequestRepository;
import auca.ac.rw.gradclearbackend.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ClearanceRequestService {

    private final ClearanceRequestRepository clearanceRequestRepository;
    private final StudentRepository studentRepository;

    public String save(ClearanceRequest clearanceRequest) {
        if (clearanceRequest.getRequestDate() == null || isDateInPast(clearanceRequest.getRequestDate())) {
            return "Clearance request date cannot be in the past";
        }

        if (clearanceRequest.getOfficer() == null || clearanceRequest.getOfficer().getId() == null) {
            return "Officer is required";
        }

        if (isOfficerBooked(clearanceRequest)) {
            return "Officer is already booked on that date";
        }
        clearanceRequestRepository.save(clearanceRequest);

        return "Clearance request saved successfully";
    }

    public List<ClearanceRequest> findAll() {
        return clearanceRequestRepository.findAll();
    }

    public Optional<ClearanceRequest> find(UUID id) {
        return clearanceRequestRepository.findById(id);
    }

    public List<ClearanceRequest> findAllByStatus(ClearanceStatus status) {
        return clearanceRequestRepository.findAllByStatus(status);
    }

    public Optional<ClearanceRequest> update(UUID id, ClearanceRequest updated) {
        Optional<ClearanceRequest> existing = clearanceRequestRepository.findById(id);

        if (existing.isPresent()) {
            ClearanceRequest found = existing.get();

            if (!isValidStatusTransition(found.getStatus(), updated.getStatus())) {
                return Optional.empty();
            }

            found.setStatus(updated.getStatus());

            return Optional.of(clearanceRequestRepository.save(found));
        }

        return Optional.empty();
    }

    public boolean isValidStatusTransition(ClearanceStatus current, ClearanceStatus next) {
        if (current == null || next == null) {
            return false;
        }

        return switch (current) {
            case PENDING -> next == ClearanceStatus.UNDER_REVIEW || next == ClearanceStatus.REJECTED;
            case UNDER_REVIEW -> next == ClearanceStatus.APPROVED || next == ClearanceStatus.REJECTED;
            case APPROVED, REJECTED -> false;
        };
    }

    public boolean delete(UUID id) {
        Optional<ClearanceRequest> existing = clearanceRequestRepository.findById(id);

        if (existing.isPresent()) {
            clearanceRequestRepository.delete(existing.get());
            return true;
        }

        return false;
    }

    public List<ClearanceRequest> findWithinDateRange(LocalDate startDate, LocalDate endDate) {
        return clearanceRequestRepository.findAllByRequestDateBetween(startDate, endDate);
    }

    public boolean isDateInPast(LocalDate requestDate) {
        return requestDate != null && requestDate.isBefore(LocalDate.now());
    }

    public boolean isOfficerBooked(ClearanceRequest request) {
        return clearanceRequestRepository.existsByOfficerIdAndRequestDateAndStatusNot(
                request.getOfficer().getId(),
                request.getRequestDate(),
                ClearanceStatus.REJECTED
        );
    }
}
