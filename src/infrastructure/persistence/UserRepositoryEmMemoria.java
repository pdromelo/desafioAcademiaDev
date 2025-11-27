package infrastructure.persistence;

import application.repositories.UserRepository;
import domain.entities.Student;
import domain.entities.User;

import java.util.*;
import java.util.stream.Collectors;

public class UserRepositoryEmMemoria implements UserRepository {
    private final Map<String, User> users = new HashMap<>();

    @Override
    public void save(User user) {
        users.put(user.getEmail(), user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(users.get(email));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public List<Student> findAllStudents() {
        return users.values().stream()
                .filter(user -> user instanceof Student)
                .map(user -> (Student) user)
                .collect(Collectors.toList());
    }
}
