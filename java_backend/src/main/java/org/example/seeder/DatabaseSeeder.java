package org.example.seeder;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.entities.Game;
import org.example.entities.Genre;
import org.example.entities.User;
import org.example.repository.IGameRepository;
import org.example.repository.IGenreRepository;
import org.example.repository.IUserRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {
    private final IUserRepository userRepository;
    private final IGenreRepository genreRepository;
    private final IGameRepository gameRepository;

    @PostConstruct
    public void seed() {
        seedUsers();
        seedGenres();
        seedGames();
    }

    private void seedUsers() {
        if (userRepository.count() == 0) {
            List<User> users = List.of(
                    createUser("admin", "admin@abc.com", "passwordadmin"),
                    createUser("user", "user@abc.com", "passworduser"),
                    createUser("user2", "user2@abc.com", "passworduser2")
            );
            userRepository.saveAll(users);
        }
    }

    private void seedGenres() {
        if (genreRepository.count() == 0) {
            List<Genre> genres = List.of(
                    createGenre("Action", "https://cdn0.iconfinder.com/data/icons/game-genre-outline/64/Game_Genre_Action-512.png"),
                    createGenre("Strategy", "https://cdn0.iconfinder.com/data/icons/game-genre-outline/64/Game_Genre_Strategy-512.png"),
                    createGenre("FPS", "https://cdn1.iconfinder.com/data/icons/video-movie/24/action-512.png")
            );
            genreRepository.saveAll(genres);
        }
    }

    private void seedGames() {
        if (gameRepository.count() == 0) {
            Genre genre = genreRepository.findAll().get(0);

            List<Game> games = List.of(
                    createGame("Cyberpunk 2077", "CD Projekt Red", "CD Projekt", LocalDate.of(2020, 12, 10), new BigDecimal("59.99"), false, genre),
                    createGame("Age of Empires IV", "Relic Entertainment", "Xbox Game Studios", LocalDate.of(2021, 10, 28), new BigDecimal("49.99"), false, genre),
                    createGame("Call of Duty: Modern Warfare", "Infinity Ward", "Activision", LocalDate.of(2019, 10, 25), new BigDecimal("39.99"), false, genre)
            );
            gameRepository.saveAll(games);
        }
    }

    private User createUser(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword_hash(password);
        return user;
    }

    private Genre createGenre(String name, String imageUrl) {
        Genre genre = new Genre();
        genre.setName(name);
        genre.setImageUrl(imageUrl);
        return genre;
    }

    private Game createGame(String title, String developer, String publisher, LocalDate releaseDate, BigDecimal price, boolean isFree, Genre genre) {
        Game game = new Game();
        game.setTitle(title);
        game.setDeveloper(developer);
        game.setPublisher(publisher);
        game.setRelease_date(releaseDate);
        game.setPrice(price);
        game.setFree(isFree);
        game.setGenre(genre);
        return game;
    }
}
