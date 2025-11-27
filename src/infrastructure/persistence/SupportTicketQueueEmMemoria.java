package infrastructure.persistence;

import application.repositories.SupportTicketQueue;
import domain.entities.SupportTicket;

import java.util.ArrayDeque;
import java.util.Queue;

public class SupportTicketQueueEmMemoria implements SupportTicketQueue {
    private final Queue<SupportTicket> queue = new ArrayDeque<>();

    @Override
    public void addTicket(SupportTicket ticket) {
        queue.add(ticket);
    }

    @Override
    public SupportTicket nextTicket() {
        return queue.poll();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public int size() {
        return queue.size();
    }
}
