package auca.ac.rw.gradclearbackend.controller;

import auca.ac.rw.gradclearbackend.model.Department;
import auca.ac.rw.gradclearbackend.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Department department) {
        departmentService.save(department);
        return new ResponseEntity<>("Department saved successfully", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(departmentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable UUID id) {

        Optional<Department> department = departmentService.find(id);

        if (department.isPresent()) {
            return new ResponseEntity<>(department.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Department type not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Department updated) {

        Optional<Department> result = departmentService.update(id, updated);

        if (result.isPresent()) {
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>("Department not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        boolean deleted = departmentService.delete(id);

        if (deleted) {
            return ResponseEntity.noContent().build();
        }

        return new ResponseEntity<>("Department not found", HttpStatus.NOT_FOUND);
    }
}
