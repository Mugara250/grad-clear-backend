package auca.ac.rw.gradclearbackend.service;

import auca.ac.rw.gradclearbackend.model.ClearanceDocument;
import auca.ac.rw.gradclearbackend.model.ClearanceRequest;
import auca.ac.rw.gradclearbackend.model.ClearanceStatus;
import auca.ac.rw.gradclearbackend.repository.ClearanceDocumentRepository;
import auca.ac.rw.gradclearbackend.repository.ClearanceRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClearanceDocumentService {

    private final ClearanceDocumentRepository clearanceDocumentRepository;
    private final ClearanceRequestRepository clearanceRequestRepository;

    public String save(UUID clearanceRequestId, ClearanceDocument document) {
        Optional<ClearanceRequest> existing = clearanceRequestRepository.findById(clearanceRequestId);

        if (existing.isEmpty()) {
            return "The clearance request with that id does not exist";
        }

        ClearanceRequest clearanceRequest = existing.get();

        if (clearanceRequest.getStatus() != ClearanceStatus.APPROVED) {
            return "Clearance documents are only for APPROVED clearance requests";
        }

        document.setIssuedDate(LocalDate.now());
        document.setClearanceRequest(clearanceRequest);

        clearanceDocumentRepository.save(document);

        return "Clearance document saved successfully";
    }

    public List<ClearanceDocument> findAll() {
        return clearanceDocumentRepository.findAll();
    }

    public List<ClearanceDocument> getDocumentsByStudentRegNumber(String regNumber) {
        return clearanceDocumentRepository.findClearanceDocumentsByRegNumber(regNumber);
    }
}
