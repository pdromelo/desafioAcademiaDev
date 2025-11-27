package application.usecases;

import application.repositories.CourseRepository;
import domain.entities.Course;
import domain.enums.DifficultyLevel;

import java.util.List;
import java.util.stream.Collectors;

public class GerarRelatorioCursosPorNivelUseCase {
    private final CourseRepository courseRepository;

    public GerarRelatorioCursosPorNivelUseCase(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> execute(DifficultyLevel level) {
        return courseRepository.findAll().stream()
                .filter(course -> course.getDifficultyLevel() == level)
                .sorted((c1, c2) -> c1.getTitle().compareToIgnoreCase(c2.getTitle()))
                .collect(Collectors.toList());
    }
}
