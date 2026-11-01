package auca.ac.rw.gradclearbackend.controller;

import auca.ac.rw.gradclearbackend.model.ClearanceRequest;
import auca.ac.rw.gradclearbackend.model.ClearanceStatus;
import auca.ac.rw.gradclearbackend.service.ClearanceRequestService;
import auca.ac.rw.gradclearbackend.service.OfficerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clearance-requests")
public class ClearanceRequestController {

    private final ClearanceRequestService clearanceRequestService;
    private final OfficerService officerService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ClearanceRequest clearanceRequest) {
        String result = clearanceRequestService.save(clearanceRequest);

        if (result.equals("Clearance request date cannot be in the past") || result.equals("Officer is required")) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        if (result.equals("Officer is already booked on that date")) {
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(clearanceRequestService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable UUID id) {

        Optional<ClearanceRequest> student = clearanceRequestService.find(id);

        if (student.isPresent()) {
            return new ResponseEntity<>(student.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Clearance request type not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by-status")
    public ResponseEntity<?> findAllByStatus(@RequestParam ClearanceStatus status) {
        return new ResponseEntity<>(clearanceRequestService.findAllByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/between")
    public ResponseEntity<?> findAllBetween(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return new ResponseEntity<>(clearanceRequestService.
                findWithinDateRange(startDate, endDate),
                HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody ClearanceRequest updated) {
        Optional<ClearanceRequest> result = clearanceRequestService.update(id, updated);

        if (result.isEmpty()) {
            return new ResponseEntity<>("The clearance request with that id does not exist",
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Clearance request updated successfully", HttpStatus.OK);
    }



    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable UUID id, @RequestParam ClearanceStatus status) {
        Optional<ClearanceRequest> existing = clearanceRequestService.find(id);

        if (existing.isEmpty()) {
            return new ResponseEntity<>("The clearance request with that id does not exist",
                    HttpStatus.NOT_FOUND);
        }

        ClearanceStatus current = existing.get().getStatus();

        if (!clearanceRequestService.isValidStatusTransition(current, status)) {
            return new ResponseEntity<>("Cannot change status from " + current + " to " + status,
                    HttpStatus.BAD_REQUEST);
        }

        Optional<ClearanceRequest> result = clearanceRequestService.updateStatus(id, status);

        if (result.isEmpty()) {
            return new ResponseEntity<>("Cannot change status from " + current + " to " + status,
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Clearance request status updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        String result = clearanceRequestService.delete(id);

        if (result.equals("The clearance request with that id does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        if (result.equals("That clearance request has clearance documents, delete them first")) {
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

}
