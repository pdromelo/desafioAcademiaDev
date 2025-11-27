package application.usecases;

import application.repositories.EnrollmentRepository;
import application.repositories.UserRepository;
import domain.entities.Enrollment;
import domain.entities.Student;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class IdentificarAlunoComMaisMatriculasUseCase {
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;

    public IdentificarAlunoComMaisMatriculasUseCase(EnrollmentRepository enrollmentRepository,
                                                     UserRepository userRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
    }

    public Optional<Student> execute() {
        List<Enrollment> allEnrollments = enrollmentRepository.findAll();
        
        if (allEnrollments.isEmpty()) {
            return Optional.empty();
        }
        
        // Agrupar matrículas por aluno e contar
        Map<Student, Long> enrollmentCountByStudent = allEnrollments.stream()
                .collect(Collectors.groupingBy(
                        Enrollment::getStudent,
                        Collectors.counting()
                ));
        
        // Encontrar o aluno com mais matrículas
        return enrollmentCountByStudent.entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey);
    }
}
