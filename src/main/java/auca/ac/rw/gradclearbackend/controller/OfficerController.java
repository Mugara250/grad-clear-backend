package auca.ac.rw.gradclearbackend.controller;

import auca.ac.rw.gradclearbackend.model.Officer;
import auca.ac.rw.gradclearbackend.service.OfficerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/officers")
public class OfficerController {

    private final OfficerService officerService;

    @GetMapping("/save")
    public ResponseEntity<?> save(@RequestBody Officer officer) {
        return new ResponseEntity<>(officer, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(officerService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable UUID id) {

        Optional<Officer> officer = officerService.find(id);

        if (officer.isPresent()) {
            return new ResponseEntity<>(officer.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Officer type not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Officer updated) {

        Optional<Officer> result = officerService.update(id, updated);

        if (result.isPresent()) {
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>("Officer not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        boolean deleted = officerService.delete(id);

        if (deleted) {
            return ResponseEntity.noContent().build();
        }

        return new ResponseEntity<>("Officer not found", HttpStatus.NOT_FOUND);
    }

}
