package application.usecases;

import application.repositories.CourseRepository;
import domain.entities.Course;
import domain.enums.CourseStatus;

import java.util.List;

public class ConsultarCatalogoCursosUseCase {
    private final CourseRepository courseRepository;

    public ConsultarCatalogoCursosUseCase(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> execute() {
        return courseRepository.findByStatus(CourseStatus.ACTIVE);
    }
}
