package domain.entities;

public class SupportTicket {
    private final User user;
    private final String title;
    private final String message;

    public SupportTicket(User user, String title, String message) {
        this.user = user;
        this.title = title;
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "SupportTicket{" +
                "user=" + user.getName() + " (" + user.getEmail() + ")" +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
