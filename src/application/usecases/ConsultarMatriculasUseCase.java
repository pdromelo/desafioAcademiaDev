package application.usecases;

import application.repositories.EnrollmentRepository;
import domain.entities.Enrollment;
import domain.entities.Student;

import java.util.List;

public class ConsultarMatriculasUseCase {
    private final EnrollmentRepository enrollmentRepository;

    public ConsultarMatriculasUseCase(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<Enrollment> execute(Student student) {
        return enrollmentRepository.findByStudent(student);
    }
}
