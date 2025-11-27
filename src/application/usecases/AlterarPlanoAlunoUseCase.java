package application.usecases;

import application.repositories.UserRepository;
import domain.entities.Student;
import domain.entities.SubscriptionPlan;
import domain.entities.User;
import domain.exceptions.BusinessException;

public class AlterarPlanoAlunoUseCase {
    private final UserRepository userRepository;

    public AlterarPlanoAlunoUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(String studentEmail, SubscriptionPlan newPlan) throws BusinessException {
        User user = userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado: " + studentEmail));
        
        if (!(user instanceof Student)) {
            throw new BusinessException("O usuário não é um aluno: " + studentEmail);
        }
        
        Student student = (Student) user;
        student.setSubscriptionPlan(newPlan);
        userRepository.save(student);
    }
}
