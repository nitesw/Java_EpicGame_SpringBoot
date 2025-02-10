package org.example.seeder;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.entities.User;
import org.example.repository.IUserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {
    private final IUserRepository userRepository;

    @PostConstruct
    public void seed() {
        if (userRepository.count() == 0) {
            List<User> users = List.of(
                    createUser("admin", "admin@abc.com", "passwordadmin"),
                    createUser("user", "user@abc.com", "passworduser"),
                    createUser("user2", "user2@abc.com", "passworduser2")
            );
            userRepository.saveAll(users);
        }
    }

    private User createUser(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword_hash(password);
        return user;
    }
}