package auca.ac.rw.gradclearbackend.controller;

import auca.ac.rw.gradclearbackend.model.ClearanceRequest;
import auca.ac.rw.gradclearbackend.model.ClearanceStatus;
import auca.ac.rw.gradclearbackend.service.ClearanceRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clearance-requests")
public class ClearanceRequestController {

    private final ClearanceRequestService clearanceRequestService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ClearanceRequest clearanceRequest) {
        clearanceRequestService.save(clearanceRequest);
        return new ResponseEntity<>("Clearance request saved successfully", HttpStatus.CREATED);
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

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody ClearanceRequest updated) {

        Optional<ClearanceRequest> result = clearanceRequestService.update(id, updated);

        if (result.isPresent()) {
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>("Clearance request not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        boolean deleted = clearanceRequestService.delete(id);

        if (deleted) {
            return ResponseEntity.noContent().build();
        }

        return new ResponseEntity<>("Clearance request not found", HttpStatus.NOT_FOUND);
    }

}
