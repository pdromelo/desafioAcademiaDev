package infrastructure.persistence;

import application.repositories.CourseRepository;
import domain.entities.Course;
import domain.enums.CourseStatus;
import domain.enums.DifficultyLevel;

import java.util.*;
import java.util.stream.Collectors;

public class CourseRepositoryEmMemoria implements CourseRepository {
    private final Map<String, Course> courses = new HashMap<>();

    @Override
    public void save(Course course) {
        courses.put(course.getTitle(), course);
    }

    @Override
    public Optional<Course> findByTitle(String title) {
        return Optional.ofNullable(courses.get(title));
    }

    @Override
    public List<Course> findAll() {
        return new ArrayList<>(courses.values());
    }

    @Override
    public List<Course> findByDifficultyLevel(DifficultyLevel level) {
        return courses.values().stream()
                .filter(course -> course.getDifficultyLevel() == level)
                .collect(Collectors.toList());
    }

    @Override
    public List<Course> findByStatus(CourseStatus status) {
        return courses.values().stream()
                .filter(course -> course.getStatus() == status)
                .collect(Collectors.toList());
    }
}
