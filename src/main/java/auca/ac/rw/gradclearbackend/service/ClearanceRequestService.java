package auca.ac.rw.gradclearbackend.service;

import auca.ac.rw.gradclearbackend.model.ClearanceRequest;
import auca.ac.rw.gradclearbackend.model.ClearanceStatus;
import auca.ac.rw.gradclearbackend.repository.ClearanceDocumentRepository;
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
    private final ClearanceDocumentRepository clearanceDocumentRepository;

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

        if (existing.isEmpty()) {
            return Optional.empty();
        }

        ClearanceRequest found = existing.get();

        found.setRequestDate(updated.getRequestDate());
        found.setReason(updated.getReason());
        found.setStudent(updated.getStudent());
        found.setOfficer(updated.getOfficer());

        return Optional.of(clearanceRequestRepository.save(found));
    }

    public Optional<ClearanceRequest> updateStatus(UUID id, ClearanceStatus status) {
        Optional<ClearanceRequest> existing = clearanceRequestRepository.findById(id);

        if (existing.isEmpty()) {
            return Optional.empty();
        }

        ClearanceRequest request = existing.get();

        if (!isValidStatusTransition(request.getStatus(), status)) {
            return Optional.empty();
        }

        request.setStatus(status);

        return Optional.of(clearanceRequestRepository.save(request));
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

    public String delete(UUID id) {
        Optional<ClearanceRequest> existing = clearanceRequestRepository.findById(id);

        if (existing.isEmpty()) {
            return "The clearance request with that id does not exist";
        }

        ClearanceRequest clearanceRequest = existing.get();

        if (clearanceDocumentRepository.existsByClearanceRequest(clearanceRequest)) {
            return "The clearance request has clearance documents, delete them first";
        }

        clearanceRequestRepository.delete(clearanceRequest);

        return "Clearance request deleted successfully";
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
