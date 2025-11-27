package infrastructure.persistence;

import application.repositories.EnrollmentRepository;
import domain.entities.Course;
import domain.entities.Enrollment;
import domain.entities.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EnrollmentRepositoryEmMemoria implements EnrollmentRepository {
    private final List<Enrollment> enrollments = new ArrayList<>();

    @Override
    public void save(Enrollment enrollment) {
        // Se a matrícula já existe, atualizar
        Optional<Enrollment> existing = findByStudentAndCourse(
                enrollment.getStudent(), 
                enrollment.getCourse()
        );
        
        if (existing.isEmpty()) {
            enrollments.add(enrollment);
        }
        // Se já existe, não precisa fazer nada pois é a mesma ref.
    }

    @Override
    public void remove(Enrollment enrollment) {
        enrollments.remove(enrollment);
    }

    @Override
    public List<Enrollment> findByStudent(Student student) {
        return enrollments.stream()
                .filter(e -> e.getStudent().equals(student))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Enrollment> findByStudentAndCourse(Student student, Course course) {
        return enrollments.stream()
                .filter(e -> e.getStudent().getEmail().equals(student.getEmail()) &&
                           e.getCourse().getTitle().equals(course.getTitle()))
                .findFirst();
    }

    @Override
    public int countActiveByStudent(Student student) {
        return (int) enrollments.stream()
                .filter(e -> e.getStudent().getEmail().equals(student.getEmail()))
                .filter(e -> e.getCourse().isActive())
                .count();
    }

    @Override
    public List<Enrollment> findAll() {
        return new ArrayList<>(enrollments);
    }
}
