package main;

import application.repositories.*;
import infrastructure.persistence.*;
import infrastructure.ui.ConsoleController;

public class Main {
    public static void main(String[] args) {
        
        // Criar instâncias dos repositórios (Infrastructure Layer)
        CourseRepository courseRepository = new CourseRepositoryEmMemoria();
        UserRepository userRepository = new UserRepositoryEmMemoria();
        EnrollmentRepository enrollmentRepository = new EnrollmentRepositoryEmMemoria();
        SupportTicketQueue ticketQueue = new SupportTicketQueueEmMemoria();
        
        // Popular dados
        InitialData.populate(courseRepository, userRepository, enrollmentRepository);
        
        // Criar controlador
        ConsoleController controller = new ConsoleController(
            courseRepository,
            userRepository,
            enrollmentRepository,
            ticketQueue
        );
        
        // Iniciar aplicação
        controller.start();
    }
}
