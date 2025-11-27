package infrastructure.ui;

import application.repositories.*;
import application.usecases.*;
import domain.entities.*;
import domain.enums.CourseStatus;
import domain.enums.DifficultyLevel;
import domain.exceptions.BusinessException;
import infrastructure.utils.GenericCsvExporter;

import java.util.*;

public class ConsoleController {
    private final Scanner scanner;
    private final ConsoleView view;
    
    // Repositories
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final SupportTicketQueue ticketQueue;
    
    // Use Cases
    private final AtivarInativarCursoUseCase ativarInativarCursoUseCase;
    private final AlterarPlanoAlunoUseCase alterarPlanoAlunoUseCase;
    private final MatricularAlunoUseCase matricularAlunoUseCase;
    private final CancelarMatriculaUseCase cancelarMatriculaUseCase;
    private final AtualizarProgressoUseCase atualizarProgressoUseCase;
    private final ConsultarMatriculasUseCase consultarMatriculasUseCase;
    private final ConsultarCatalogoCursosUseCase consultarCatalogoCursosUseCase;
    private final AbrirTicketUseCase abrirTicketUseCase;
    private final AtenderTicketUseCase atenderTicketUseCase;
    private final GerarRelatorioCursosPorNivelUseCase gerarRelatorioCursosPorNivelUseCase;
    private final GerarRelatorioInstrutoresUnicosUseCase gerarRelatorioInstrutoresUnicosUseCase;
    private final GerarRelatorioAlunosPorPlanoUseCase gerarRelatorioAlunosPorPlanoUseCase;
    private final CalcularMediaProgressoUseCase calcularMediaProgressoUseCase;
    private final IdentificarAlunoComMaisMatriculasUseCase identificarAlunoComMaisMatriculasUseCase;
    
    // Utils
    private final GenericCsvExporter csvExporter;
    
    // Current user
    private User currentUser;
    
    public ConsoleController(CourseRepository courseRepository,
                           UserRepository userRepository,
                           EnrollmentRepository enrollmentRepository,
                           SupportTicketQueue ticketQueue) {
        this.scanner = new Scanner(System.in);
        this.view = new ConsoleView();
        
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.ticketQueue = ticketQueue;
        
        // Initialize use cases
        this.ativarInativarCursoUseCase = new AtivarInativarCursoUseCase(courseRepository);
        this.alterarPlanoAlunoUseCase = new AlterarPlanoAlunoUseCase(userRepository);
        this.matricularAlunoUseCase = new MatricularAlunoUseCase(courseRepository, enrollmentRepository);
        this.cancelarMatriculaUseCase = new CancelarMatriculaUseCase(courseRepository, enrollmentRepository);
        this.atualizarProgressoUseCase = new AtualizarProgressoUseCase(courseRepository, enrollmentRepository);
        this.consultarMatriculasUseCase = new ConsultarMatriculasUseCase(enrollmentRepository);
        this.consultarCatalogoCursosUseCase = new ConsultarCatalogoCursosUseCase(courseRepository);
        this.abrirTicketUseCase = new AbrirTicketUseCase(ticketQueue);
        this.atenderTicketUseCase = new AtenderTicketUseCase(ticketQueue);
        this.gerarRelatorioCursosPorNivelUseCase = new GerarRelatorioCursosPorNivelUseCase(courseRepository);
        this.gerarRelatorioInstrutoresUnicosUseCase = new GerarRelatorioInstrutoresUnicosUseCase(courseRepository);
        this.gerarRelatorioAlunosPorPlanoUseCase = new GerarRelatorioAlunosPorPlanoUseCase(userRepository);
        this.calcularMediaProgressoUseCase = new CalcularMediaProgressoUseCase(enrollmentRepository);
        this.identificarAlunoComMaisMatriculasUseCase = new IdentificarAlunoComMaisMatriculasUseCase(enrollmentRepository, userRepository);
        
        this.csvExporter = new GenericCsvExporter();
    }
    
    public void start() {
        boolean running = true;
        
        while (running) {
            view.displayMainMenu();
            int option = readInt();
            
            switch (option) {
                case 1:
                    handleLogin();
                    break;
                case 0:
                    view.displayInfo("Encerrando aplicação...");
                    running = false;
                    break;
                default:
                    view.displayError("Opção inválida!");
            }
        }
        
        scanner.close();
    }
    
    private void handleLogin() {
        System.out.print("\nDigite seu email: ");
        String email = scanner.nextLine().trim();
        
        Optional<User> userOpt = userRepository.findByEmail(email);
        
        if (userOpt.isEmpty()) {
            view.displayError("Usuário não encontrado!");
            return;
        }
        
        currentUser = userOpt.get();
        view.displaySuccess("Login realizado com sucesso! Bem-vindo(a), " + currentUser.getName());
        
        if (currentUser.isAdmin()) {
            handleAdminMenu();
        } else {
            handleStudentMenu();
        }
    }
    
    private void handleAdminMenu() {
        boolean inMenu = true;
        
        while (inMenu) {
            view.displayAdminMenu();
            int option = readInt();
            
            switch (option) {
                case 1:
                    handleManageCourseStatus();
                    break;
                case 2:
                    handleManageStudentPlan();
                    break;
                case 3:
                    handleProcessTicket();
                    break;
                case 4:
                    handleReportsMenu();
                    break;
                case 5:
                    handleExportData();
                    break;
                case 6:
                    handleViewCatalog();
                    break;
                case 7:
                    handleOpenTicket();
                    break;
                case 0:
                    currentUser = null;
                    view.displayInfo("Logout realizado.");
                    inMenu = false;
                    break;
                default:
                    view.displayError("Opção inválida!");
            }
        }
    }
    
    private void handleStudentMenu() {
        boolean inMenu = true;
        
        while (inMenu) {
            view.displayStudentMenu();
            int option = readInt();
            
            switch (option) {
                case 1:
                    handleEnrollInCourse();
                    break;
                case 2:
                    handleViewEnrollments();
                    break;
                case 3:
                    handleUpdateProgress();
                    break;
                case 4:
                    handleCancelEnrollment();
                    break;
                case 5:
                    handleViewCatalog();
                    break;
                case 6:
                    handleOpenTicket();
                    break;
                case 0:
                    currentUser = null;
                    view.displayInfo("Logout realizado.");
                    inMenu = false;
                    break;
                default:
                    view.displayError("Opção inválida!");
            }
        }
    }
    
    private void handleManageCourseStatus() {
        System.out.print("\nDigite o título do curso: ");
        String title = scanner.nextLine().trim();
        
        System.out.println("1. Ativar");
        System.out.println("2. Inativar");
        System.out.print("Escolha: ");
        int choice = readInt();
        
        CourseStatus newStatus = (choice == 1) ? CourseStatus.ACTIVE : CourseStatus.INACTIVE;
        
        try {
            ativarInativarCursoUseCase.execute(title, newStatus);
            view.displaySuccess("Status do curso atualizado!");
        } catch (BusinessException e) {
            view.displayError(e.getMessage());
        }
    }
    
    private void handleManageStudentPlan() {
        System.out.print("\nDigite o email do aluno: ");
        String email = scanner.nextLine().trim();
        
        System.out.println("1. BasicPlan");
        System.out.println("2. PremiumPlan");
        System.out.print("Escolha o novo plano: ");
        int choice = readInt();
        
        SubscriptionPlan newPlan = (choice == 1) ? new BasicPlan() : new PremiumPlan();
        
        try {
            alterarPlanoAlunoUseCase.execute(email, newPlan);
            view.displaySuccess("Plano do aluno atualizado!");
        } catch (BusinessException e) {
            view.displayError(e.getMessage());
        }
    }
    
    private void handleProcessTicket() {
        try {
            SupportTicket ticket = atenderTicketUseCase.execute();
            view.displayTicket(ticket);
            view.displaySuccess("Ticket processado com sucesso!");
        } catch (BusinessException e) {
            view.displayError(e.getMessage());
        }
    }
    
    private void handleReportsMenu() {
        boolean inMenu = true;
        
        while (inMenu) {
            view.displayReportsMenu();
            int option = readInt();
            
            switch (option) {
                case 1:
                    handleCoursesByLevel();
                    break;
                case 2:
                    handleUniqueInstructors();
                    break;
                case 3:
                    handleStudentsByPlan();
                    break;
                case 4:
                    handleAverageProgress();
                    break;
                case 5:
                    handleTopStudent();
                    break;
                case 0:
                    inMenu = false;
                    break;
                default:
                    view.displayError("Opção inválida!");
            }
        }
    }
    
    private void handleCoursesByLevel() {
        System.out.println("\n1. BEGINNER");
        System.out.println("2. INTERMEDIATE");
        System.out.println("3. ADVANCED");
        System.out.print("Escolha o nível: ");
        int choice = readInt();
        
        DifficultyLevel level;
        switch (choice) {
            case 1: level = DifficultyLevel.BEGINNER; break;
            case 2: level = DifficultyLevel.INTERMEDIATE; break;
            case 3: level = DifficultyLevel.ADVANCED; break;
            default:
                view.displayError("Opção inválida!");
                return;
        }
        
        List<Course> courses = gerarRelatorioCursosPorNivelUseCase.execute(level);
        view.displayCourses(courses);
    }
    
    private void handleUniqueInstructors() {
        Set<String> instructors = gerarRelatorioInstrutoresUnicosUseCase.execute();
        view.displayInstructors(instructors);
    }
    
    private void handleStudentsByPlan() {
        Map<String, List<Student>> studentsByPlan = gerarRelatorioAlunosPorPlanoUseCase.execute();
        view.displayStudentsByPlan(studentsByPlan);
    }
    
    private void handleAverageProgress() {
        double average = calcularMediaProgressoUseCase.execute();
        view.displayAverageProgress(average);
    }
    
    private void handleTopStudent() {
        Optional<Student> student = identificarAlunoComMaisMatriculasUseCase.execute();
        view.displayTopStudent(student);
    }
    
    private void handleExportData() {
        System.out.println("\n1. Exportar Cursos");
        System.out.println("2. Exportar Alunos");
        System.out.print("Escolha: ");
        int choice = readInt();
        
        if (choice == 1) {
            List<Course> courses = courseRepository.findAll();
            System.out.println("\nCampos disponíveis: title, description, instructorName, durationInHours, difficultyLevel, status");
            System.out.print("Digite os campos desejados separados por vírgula: ");
            String fieldsInput = scanner.nextLine().trim();
            List<String> fields = Arrays.asList(fieldsInput.split(","));
            
            String csv = csvExporter.exportToCSV(courses, fields);
            view.displayCSV(csv);
        } else if (choice == 2) {
            List<Student> students = userRepository.findAllStudents();
            System.out.println("\nCampos disponíveis: name, email");
            System.out.print("Digite os campos desejados separados por vírgula: ");
            String fieldsInput = scanner.nextLine().trim();
            List<String> fields = Arrays.asList(fieldsInput.split(","));
            
            String csv = csvExporter.exportToCSV(students, fields);
            view.displayCSV(csv);
        } else {
            view.displayError("Opção inválida!");
        }
    }
    
    private void handleEnrollInCourse() {
        System.out.print("\nDigite o título do curso: ");
        String title = scanner.nextLine().trim();
        
        try {
            matricularAlunoUseCase.execute((Student) currentUser, title);
            view.displaySuccess("Matrícula realizada com sucesso!");
        } catch (BusinessException e) {
            view.displayError(e.getMessage());
        }
    }
    
    private void handleViewEnrollments() {
        List<Enrollment> enrollments = consultarMatriculasUseCase.execute((Student) currentUser);
        view.displayEnrollments(enrollments);
    }
    
    private void handleUpdateProgress() {
        System.out.print("\nDigite o título do curso: ");
        String title = scanner.nextLine().trim();
        
        System.out.print("Digite o novo progresso (0-100): ");
        double progress = readDouble();
        
        try {
            atualizarProgressoUseCase.execute((Student) currentUser, title, progress);
            view.displaySuccess("Progresso atualizado!");
        } catch (BusinessException e) {
            view.displayError(e.getMessage());
        }
    }
    
    private void handleCancelEnrollment() {
        System.out.print("\nDigite o título do curso: ");
        String title = scanner.nextLine().trim();
        
        try {
            cancelarMatriculaUseCase.execute((Student) currentUser, title);
            view.displaySuccess("Matrícula cancelada!");
        } catch (BusinessException e) {
            view.displayError(e.getMessage());
        }
    }
    
    private void handleViewCatalog() {
        List<Course> courses = consultarCatalogoCursosUseCase.execute();
        view.displayCourses(courses);
    }
    
    private void handleOpenTicket() {
        System.out.print("\nTítulo do ticket: ");
        String title = scanner.nextLine().trim();
        
        System.out.print("Mensagem: ");
        String message = scanner.nextLine().trim();
        
        abrirTicketUseCase.execute(currentUser, title, message);
        view.displaySuccess("Ticket aberto! Você é o número " + ticketQueue.size() + " na fila.");
    }
    
    private int readInt() {
        try {
            String input = scanner.nextLine().trim();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private double readDouble() {
        try {
            String input = scanner.nextLine().trim();
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
