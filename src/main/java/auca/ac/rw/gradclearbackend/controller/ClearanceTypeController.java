package auca.ac.rw.gradclearbackend.controller;

import auca.ac.rw.gradclearbackend.model.ClearanceType;
import auca.ac.rw.gradclearbackend.service.ClearanceTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clearance-types")
public class ClearanceTypeController {

    private final ClearanceTypeService clearanceTypeService;

    @GetMapping("/save")
    public ResponseEntity<?> save(@RequestBody ClearanceType clearanceType) {
        return new ResponseEntity<>(clearanceType, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(clearanceTypeService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable UUID id) {

        Optional<ClearanceType> clearanceType = clearanceTypeService.find(id);

        if (clearanceType.isPresent()) {
            return new ResponseEntity<>(clearanceType.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Clearance type not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody ClearanceType updated) {

        Optional<ClearanceType> result = clearanceTypeService.update(id, updated);

        if (result.isPresent()) {
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>("Clearance type not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        boolean deleted = clearanceTypeService.delete(id);

        if (deleted) {
            return ResponseEntity.noContent().build();
        }

        return new ResponseEntity<>("Clearance type not found", HttpStatus.NOT_FOUND);
    }

}
