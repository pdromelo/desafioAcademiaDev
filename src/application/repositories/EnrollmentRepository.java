package application.repositories;

import domain.entities.Course;
import domain.entities.Enrollment;
import domain.entities.Student;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository {
    void save(Enrollment enrollment);
    void remove(Enrollment enrollment);
    List<Enrollment> findByStudent(Student student);
    Optional<Enrollment> findByStudentAndCourse(Student student, Course course);
    int countActiveByStudent(Student student);
    List<Enrollment> findAll();
}
