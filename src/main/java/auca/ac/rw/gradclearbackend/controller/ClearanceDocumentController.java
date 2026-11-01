package auca.ac.rw.gradclearbackend.controller;

import auca.ac.rw.gradclearbackend.model.ClearanceDocument;
import auca.ac.rw.gradclearbackend.repository.ClearanceDocumentRepository;
import auca.ac.rw.gradclearbackend.service.ClearanceDocumentService;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.spi.Resolution;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clearance-documents")
public class ClearanceDocumentController {

    private final ClearanceDocumentService clearanceDocumentService;

    @PostMapping("/save")
    public ResponseEntity<?> save(
            @RequestParam UUID clearanceRequestId,
            @RequestBody ClearanceDocument document
            ){
        String result = clearanceDocumentService.save(clearanceRequestId, document);

        if (result.equals("The clearance request with that id does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        if (result.equals("Clearance documents are only for APPROVED clearance requests")) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(clearanceDocumentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/student/{regNumber}")
    public ResponseEntity<?> getDocumentsByStudentRegNumber(@PathVariable String regNumber) {
        List<ClearanceDocument> documents = clearanceDocumentService.getDocumentsByStudentRegNumber(regNumber);
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        boolean deleted = clearanceDocumentService.delete(id);

        if (!deleted) {
            return new ResponseEntity<>("The clearance document with that id does not exist",
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Clearance document deleted successfully",
                HttpStatus.OK);
    }
}
