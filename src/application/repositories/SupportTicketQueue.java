package application.repositories;

import domain.entities.SupportTicket;

public interface SupportTicketQueue {
    void addTicket(SupportTicket ticket);
    SupportTicket nextTicket();
    boolean isEmpty();
    int size();
}
