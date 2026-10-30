package auca.ac.rw.gradclearbackend.service;

import auca.ac.rw.gradclearbackend.model.Student;
import auca.ac.rw.gradclearbackend.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Optional<Student> find(UUID id) {
        return studentRepository.findById(id);
    }

    public List<Student> findAllByLastName(String lastName) {
        return studentRepository.findAllByLastName(lastName);
    }
    public Optional<Student> update(UUID id, Student updated) {
        Optional<Student> existing = studentRepository.findById(id);

        if (existing.isPresent()) {
            Student found = existing.get();

            found.setFirstName(updated.getFirstName());
            found.setLastName(updated.getLastName());
            found.setEmail(updated.getEmail());
            found.setPhone(updated.getPhone());

            return Optional.of(studentRepository.save(found));
        }

        return Optional.empty();
    }

    public boolean delete(UUID id) {
        Optional<Student> existing = studentRepository.findById(id);

        if (existing.isPresent()) {
            studentRepository.delete(existing.get());
            return true;
        }

        return false;
    }
}
