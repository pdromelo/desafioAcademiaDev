package application.usecases;

import application.repositories.CourseRepository;
import application.repositories.EnrollmentRepository;
import domain.entities.Course;
import domain.entities.Enrollment;
import domain.entities.Student;
import domain.exceptions.BusinessException;

public class AtualizarProgressoUseCase {
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public AtualizarProgressoUseCase(CourseRepository courseRepository,
                                     EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public void execute(Student student, String courseTitle, double newProgress) throws BusinessException {
        if (newProgress < 0 || newProgress > 100) {
            throw new BusinessException("Progresso deve estar entre 0 e 100");
        }
        
        Course course = courseRepository.findByTitle(courseTitle)
                .orElseThrow(() -> new BusinessException("Curso não encontrado: " + courseTitle));
        
        Enrollment enrollment = enrollmentRepository.findByStudentAndCourse(student, course)
                .orElseThrow(() -> new BusinessException("Aluno não está matriculado neste curso"));
        
        enrollment.setProgress(newProgress);
        enrollmentRepository.save(enrollment);
    }
}
