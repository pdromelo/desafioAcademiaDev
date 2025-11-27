package main;

import application.repositories.*;
import domain.entities.*;
import domain.enums.CourseStatus;
import domain.enums.DifficultyLevel;

public class InitialData {
    
    public static void populate(CourseRepository courseRepository,
                               UserRepository userRepository,
                               EnrollmentRepository enrollmentRepository) {
        // Criar cursos
        Course course1 = new Course(
            "Java Básico",
            "Introdução à programação Java",
            "Prof. Carlos Silva",
            40,
            DifficultyLevel.BEGINNER,
            CourseStatus.ACTIVE
        );
        
        Course course2 = new Course(
            "Spring Boot Avançado",
            "Desenvolvimento de APIs RESTful com Spring Boot",
            "Prof. Ana Santos",
            60,
            DifficultyLevel.ADVANCED,
            CourseStatus.ACTIVE
        );
        
        Course course3 = new Course(
            "Python para Data Science",
            "Análise de dados com Python e Pandas",
            "Prof. João Oliveira",
            50,
            DifficultyLevel.INTERMEDIATE,
            CourseStatus.ACTIVE
        );
        
        Course course4 = new Course(
            "JavaScript Moderno",
            "ES6+ e frameworks modernos",
            "Prof. Maria Costa",
            45,
            DifficultyLevel.INTERMEDIATE,
            CourseStatus.ACTIVE
        );
        
        Course course5 = new Course(
            "Arquitetura de Software",
            "Padrões de design e Clean Architecture",
            "Prof. Carlos Silva",
            80,
            DifficultyLevel.ADVANCED,
            CourseStatus.ACTIVE
        );
        
        Course course6 = new Course(
            "Git e GitHub",
            "Controle de versão para iniciantes",
            "Prof. Pedro Lima",
            20,
            DifficultyLevel.BEGINNER,
            CourseStatus.INACTIVE
        );
        
        courseRepository.save(course1);
        courseRepository.save(course2);
        courseRepository.save(course3);
        courseRepository.save(course4);
        courseRepository.save(course5);
        courseRepository.save(course6);
        
        // Criar usuários
        Admin admin1 = new Admin("Admin Master", "admin@academiadev.com");
        Admin admin2 = new Admin("Admin Suporte", "suporte@academiadev.com");
        
        Student student1 = new Student("João Silva", "joao@email.com", new BasicPlan());
        Student student2 = new Student("Maria Santos", "maria@email.com", new PremiumPlan());
        Student student3 = new Student("Pedro Costa", "pedro@email.com", new BasicPlan());
        Student student4 = new Student("Ana Oliveira", "ana@email.com", new PremiumPlan());
        Student student5 = new Student("Lucas Ferreira", "lucas@email.com", new BasicPlan());
        
        userRepository.save(admin1);
        userRepository.save(admin2);
        userRepository.save(student1);
        userRepository.save(student2);
        userRepository.save(student3);
        userRepository.save(student4);
        userRepository.save(student5);
        
        // Criar matrículas
        Enrollment enrollment1 = new Enrollment(student1, course1);
        enrollment1.setProgress(75.0);
        enrollmentRepository.save(enrollment1);
        
        Enrollment enrollment2 = new Enrollment(student1, course3);
        enrollment2.setProgress(30.0);
        enrollmentRepository.save(enrollment2);
        
        Enrollment enrollment3 = new Enrollment(student2, course1);
        enrollment3.setProgress(100.0);
        enrollmentRepository.save(enrollment3);
        
        Enrollment enrollment4 = new Enrollment(student2, course2);
        enrollment4.setProgress(50.0);
        enrollmentRepository.save(enrollment4);
        
        Enrollment enrollment5 = new Enrollment(student2, course3);
        enrollment5.setProgress(80.0);
        enrollmentRepository.save(enrollment5);
        
        Enrollment enrollment6 = new Enrollment(student2, course4);
        enrollment6.setProgress(20.0);
        enrollmentRepository.save(enrollment6);
        
        Enrollment enrollment7 = new Enrollment(student3, course1);
        enrollment7.setProgress(45.0);
        enrollmentRepository.save(enrollment7);
        
        Enrollment enrollment8 = new Enrollment(student4, course5);
        enrollment8.setProgress(90.0);
        enrollmentRepository.save(enrollment8);
        
        Enrollment enrollment9 = new Enrollment(student4, course2);
        enrollment9.setProgress(60.0);
        enrollmentRepository.save(enrollment9);
        
        System.out.println("========================================");
        System.out.println("  Dados iniciais carregados com sucesso!");
        System.out.println("========================================");
        System.out.println("Cursos cadastrados: 6");
        System.out.println("Usuários cadastrados: 7 (2 admins, 5 alunos)");
        System.out.println("Matrículas criadas: 9");
        System.out.println("\nUsuários disponíveis para login:");
        System.out.println("Admins:");
        System.out.println("  - admin@academiadev.com");
        System.out.println("  - suporte@academiadev.com");
        System.out.println("Alunos:");
        System.out.println("  - joao@email.com (BasicPlan)");
        System.out.println("  - maria@email.com (PremiumPlan)");
        System.out.println("  - pedro@email.com (BasicPlan)");
        System.out.println("  - ana@email.com (PremiumPlan)");
        System.out.println("  - lucas@email.com (BasicPlan)");
        System.out.println("========================================\n");
    }
}
