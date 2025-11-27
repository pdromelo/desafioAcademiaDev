package application.usecases;

import application.repositories.UserRepository;
import domain.entities.Student;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GerarRelatorioAlunosPorPlanoUseCase {
    private final UserRepository userRepository;

    public GerarRelatorioAlunosPorPlanoUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Map<String, List<Student>> execute() {
        return userRepository.findAllStudents().stream()
                .collect(Collectors.groupingBy(
                        student -> student.getSubscriptionPlan().getPlanName()
                ));
    }
}
