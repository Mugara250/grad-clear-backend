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

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Officer officer) {
        officerService.save(officer);
        return new ResponseEntity<>("Officer saved successfully", HttpStatus.CREATED);
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

    @GetMapping("/by-clearance-type")
    public ResponseEntity<?> findAllByClearanceType(@RequestParam String name) {
        return new ResponseEntity<>(officerService.findAllByClearanceTypeName(name), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Officer updated) {

        Optional<Officer> result = officerService.update(id, updated);

        if (result.isPresent()) {
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>("Officer not found", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{officerId}/clearance-types/{clearanceTypeId}")
    public ResponseEntity<?> addClearanceType(@PathVariable UUID officerId,
                                              @PathVariable UUID clearanceTypeId){
        String result = officerService.addClearanceType(officerId, clearanceTypeId);

        if (result.equals("The officer with that id does not exist")
                || result.equals("That clearance type does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        if (result.equals("The officer already has that clearance type")) {
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
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
