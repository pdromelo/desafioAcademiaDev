package application.usecases;

import application.repositories.CourseRepository;
import domain.entities.Course;
import domain.enums.CourseStatus;

import java.util.Set;
import java.util.stream.Collectors;

public class GerarRelatorioInstrutoresUnicosUseCase {
    private final CourseRepository courseRepository;

    public GerarRelatorioInstrutoresUnicosUseCase(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Set<String> execute() {
        return courseRepository.findAll().stream()
                .filter(Course::isActive)
                .map(Course::getInstructorName)
                .collect(Collectors.toSet());
    }
}
