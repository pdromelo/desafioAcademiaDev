package application.usecases;

import application.repositories.CourseRepository;
import application.repositories.EnrollmentRepository;
import domain.entities.Course;
import domain.entities.Enrollment;
import domain.entities.Student;
import domain.exceptions.BusinessException;

public class CancelarMatriculaUseCase {
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public CancelarMatriculaUseCase(CourseRepository courseRepository,
                                    EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public void execute(Student student, String courseTitle) throws BusinessException {
        Course course = courseRepository.findByTitle(courseTitle)
                .orElseThrow(() -> new BusinessException("Curso não encontrado: " + courseTitle));
        
        Enrollment enrollment = enrollmentRepository.findByStudentAndCourse(student, course)
                .orElseThrow(() -> new BusinessException("Aluno não está matriculado neste curso"));
        
        enrollmentRepository.remove(enrollment);
    }
}
