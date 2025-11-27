package application.usecases;

import application.repositories.SupportTicketQueue;
import domain.entities.SupportTicket;
import domain.exceptions.BusinessException;

public class AtenderTicketUseCase {
    private final SupportTicketQueue ticketQueue;

    public AtenderTicketUseCase(SupportTicketQueue ticketQueue) {
        this.ticketQueue = ticketQueue;
    }

    public SupportTicket execute() throws BusinessException {
        if (ticketQueue.isEmpty()) {
            throw new BusinessException("Não há tickets na fila de atendimento");
        }
        return ticketQueue.nextTicket();
    }
}
