package infrastructure.ui;

import domain.entities.*;
import domain.enums.DifficultyLevel;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ConsoleView {
    
    public void displayMainMenu() {
        System.out.println("\n========================================");
        System.out.println("   ACADEMIADEV - Plataforma de Cursos");
        System.out.println("========================================");
        System.out.println("1. Login");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }
    
    public void displayAdminMenu() {
        System.out.println("\n========================================");
        System.out.println("         MENU ADMINISTRADOR");
        System.out.println("========================================");
        System.out.println("1. Gerenciar Status de Cursos");
        System.out.println("2. Gerenciar Planos de Alunos");
        System.out.println("3. Atender Tickets de Suporte");
        System.out.println("4. Gerar Relatórios e Análises");
        System.out.println("5. Exportar Dados (CSV)");
        System.out.println("6. Consultar Catálogo de Cursos");
        System.out.println("7. Abrir Ticket de Suporte");
        System.out.println("0. Logout");
        System.out.print("Escolha uma opção: ");
    }
    
    public void displayStudentMenu() {
        System.out.println("\n========================================");
        System.out.println("           MENU ALUNO");
        System.out.println("========================================");
        System.out.println("1. Matricular-se em Curso");
        System.out.println("2. Consultar Minhas Matrículas");
        System.out.println("3. Atualizar Progresso");
        System.out.println("4. Cancelar Matrícula");
        System.out.println("5. Consultar Catálogo de Cursos");
        System.out.println("6. Abrir Ticket de Suporte");
        System.out.println("0. Logout");
        System.out.print("Escolha uma opção: ");
    }
    
    public void displayReportsMenu() {
        System.out.println("\n========================================");
        System.out.println("      RELATÓRIOS E ANÁLISES");
        System.out.println("========================================");
        System.out.println("1. Cursos por Nível de Dificuldade");
        System.out.println("2. Instrutores Únicos de Cursos Ativos");
        System.out.println("3. Alunos Agrupados por Plano");
        System.out.println("4. Média Geral de Progresso");
        System.out.println("5. Aluno com Mais Matrículas");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");
    }
    
    public void displayCourses(List<Course> courses) {
        if (courses.isEmpty()) {
            System.out.println("\nNenhum curso encontrado.");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("           LISTA DE CURSOS");
        System.out.println("========================================");
        for (Course course : courses) {
            System.out.println("\nTítulo: " + course.getTitle());
            System.out.println("Descrição: " + course.getDescription());
            System.out.println("Instrutor: " + course.getInstructorName());
            System.out.println("Duração: " + course.getDurationInHours() + " horas");
            System.out.println("Nível: " + course.getDifficultyLevel());
            System.out.println("Status: " + course.getStatus());
            System.out.println("----------------------------------------");
        }
    }
    
    public void displayEnrollments(List<Enrollment> enrollments) {
        if (enrollments.isEmpty()) {
            System.out.println("\nVocê não está matriculado em nenhum curso.");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("         MINHAS MATRÍCULAS");
        System.out.println("========================================");
        for (Enrollment enrollment : enrollments) {
            System.out.println("\nCurso: " + enrollment.getCourse().getTitle());
            System.out.println("Progresso: " + String.format("%.2f", enrollment.getProgress()) + "%");
            System.out.println("Status do Curso: " + enrollment.getCourse().getStatus());
            System.out.println("----------------------------------------");
        }
    }
    
    public void displayTicket(SupportTicket ticket) {
        System.out.println("\n========================================");
        System.out.println("         TICKET DE SUPORTE");
        System.out.println("========================================");
        System.out.println("Usuário: " + ticket.getUser().getName() + " (" + ticket.getUser().getEmail() + ")");
        System.out.println("Título: " + ticket.getTitle());
        System.out.println("Mensagem: " + ticket.getMessage());
        System.out.println("========================================");
    }
    
    public void displayInstructors(Set<String> instructors) {
        System.out.println("\n========================================");
        System.out.println("    INSTRUTORES DE CURSOS ATIVOS");
        System.out.println("========================================");
        if (instructors.isEmpty()) {
            System.out.println("Nenhum instrutor encontrado.");
        } else {
            for (String instructor : instructors) {
                System.out.println("- " + instructor);
            }
        }
    }
    
    public void displayStudentsByPlan(Map<String, List<Student>> studentsByPlan) {
        System.out.println("\n========================================");
        System.out.println("    ALUNOS AGRUPADOS POR PLANO");
        System.out.println("========================================");
        for (Map.Entry<String, List<Student>> entry : studentsByPlan.entrySet()) {
            System.out.println("\n" + entry.getKey() + ":");
            for (Student student : entry.getValue()) {
                System.out.println("  - " + student.getName() + " (" + student.getEmail() + ")");
            }
        }
    }
    
    public void displayAverageProgress(double average) {
        System.out.println("\n========================================");
        System.out.println("      MÉDIA GERAL DE PROGRESSO");
        System.out.println("========================================");
        System.out.println("Média: " + String.format("%.2f", average) + "%");
    }
    
    public void displayTopStudent(Optional<Student> studentOpt) {
        System.out.println("\n========================================");
        System.out.println("    ALUNO COM MAIS MATRÍCULAS");
        System.out.println("========================================");
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            System.out.println("Aluno: " + student.getName());
            System.out.println("Email: " + student.getEmail());
            System.out.println("Plano: " + student.getSubscriptionPlan().getPlanName());
        } else {
            System.out.println("Nenhum aluno encontrado.");
        }
    }
    
    public void displayCSV(String csv) {
        System.out.println("\n========================================");
        System.out.println("         EXPORTAÇÃO CSV");
        System.out.println("========================================");
        System.out.println(csv);
    }
    
    public void displaySuccess(String message) {
        System.out.println("\n✓ " + message);
    }
    
    public void displayError(String message) {
        System.out.println("\n✗ ERRO: " + message);
    }
    
    public void displayInfo(String message) {
        System.out.println("\n" + message);
    }
}
