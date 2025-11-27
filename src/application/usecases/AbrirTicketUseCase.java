package application.usecases;

import application.repositories.SupportTicketQueue;
import domain.entities.SupportTicket;
import domain.entities.User;

public class AbrirTicketUseCase {
    private final SupportTicketQueue ticketQueue;

    public AbrirTicketUseCase(SupportTicketQueue ticketQueue) {
        this.ticketQueue = ticketQueue;
    }

    public void execute(User user, String title, String message) {
        SupportTicket ticket = new SupportTicket(user, title, message);
        ticketQueue.addTicket(ticket);
    }
}
