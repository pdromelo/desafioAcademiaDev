package application.usecases;

import application.repositories.CourseRepository;
import domain.entities.Course;
import domain.enums.CourseStatus;
import domain.exceptions.BusinessException;

public class AtivarInativarCursoUseCase {
    private final CourseRepository courseRepository;

    public AtivarInativarCursoUseCase(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public void execute(String courseTitle, CourseStatus newStatus) throws BusinessException {
        Course course = courseRepository.findByTitle(courseTitle)
                .orElseThrow(() -> new BusinessException("Curso não encontrado: " + courseTitle));
        
        course.setStatus(newStatus);
        courseRepository.save(course);
    }
}
