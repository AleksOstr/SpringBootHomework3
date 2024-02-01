package ru.geekbrains.springboothomework3.security;

import jakarta.annotation.PostConstruct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);

    @PostConstruct
    private void posConstruct() {
        save(new User("admin", "admin", List.of(new Role("admin"), new Role("reader"))));
        save(new User("reader", "reader", List.of(new Role("reader"))));
        save(new User("user", "user", List.of(new Role("user"))));
    }
}
