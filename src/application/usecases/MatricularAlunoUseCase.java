package application.usecases;

import application.repositories.CourseRepository;
import application.repositories.EnrollmentRepository;
import domain.entities.Course;
import domain.entities.Enrollment;
import domain.entities.Student;
import domain.exceptions.EnrollmentException;

public class MatricularAlunoUseCase {
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public MatricularAlunoUseCase(CourseRepository courseRepository, 
                                  EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public void execute(Student student, String courseTitle) throws EnrollmentException {
        // Verificar se o curso existe
        Course course = courseRepository.findByTitle(courseTitle)
                .orElseThrow(() -> new EnrollmentException("Curso não encontrado: " + courseTitle));
        
        // Verificar se o curso está ativo
        if (!course.isActive()) {
            throw new EnrollmentException("O curso não está ativo: " + courseTitle);
        }
        
        // Verificar se o aluno já está matriculado no curso
        if (enrollmentRepository.findByStudentAndCourse(student, course).isPresent()) {
            throw new EnrollmentException("Aluno já está matriculado neste curso");
        }
        
        // Verificar se o plano do aluno permite mais matrículas
        int currentEnrollments = enrollmentRepository.countActiveByStudent(student);
        if (!student.canEnroll(currentEnrollments)) {
            throw new EnrollmentException("Plano de assinatura não permite mais matrículas. " +
                    "Máximo: " + student.getSubscriptionPlan().getMaxEnrollments());
        }
        
        // Criar e salvar a matrícula
        Enrollment enrollment = new Enrollment(student, course);
        enrollmentRepository.save(enrollment);
    }
}
