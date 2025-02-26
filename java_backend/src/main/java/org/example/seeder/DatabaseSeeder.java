package org.example.seeder;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.entities.Game;
import org.example.entities.GameImage;
import org.example.entities.Genre;
import org.example.entities.User;
import org.example.repository.IGameImageRepository;
import org.example.repository.IGameRepository;
import org.example.repository.IGenreRepository;
import org.example.repository.IUserRepository;
import org.example.service.FileService;
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
    private final IGameImageRepository gameImageRepository;
    private final FileService fileService;

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
            var action = fileService.load("https://cdn0.iconfinder.com/data/icons/game-genre-outline/64/Game_Genre_Action-512.png");
            var strategy = fileService.load("https://cdn0.iconfinder.com/data/icons/game-genre-outline/64/Game_Genre_Strategy-512.png");
            var fps = fileService.load("https://cdn1.iconfinder.com/data/icons/video-movie/24/action-512.png");

            List<Genre> genres = List.of(
                    createGenre("Action", action, "Description of Action genre."),
                    createGenre("Strategy", strategy, "Description of Strategy genre."),
                    createGenre("FPS", fps, "Description of FPS genre.")
            );
            genreRepository.saveAll(genres);
        }
    }

    private void seedGames() {
        if (gameRepository.count() == 0) {
            List<Game> games = List.of(
                    createGame("Cyberpunk 2077", "CD Projekt Red", "CD Projekt", LocalDate.of(2020, 12, 10), new BigDecimal("59.99"), false, genreRepository.findAll().get(0)),
                    createGame("Age of Empires IV", "Relic Entertainment", "Xbox Game Studios", LocalDate.of(2021, 10, 28), new BigDecimal("49.99"), false, genreRepository.findAll().get(1)),
                    createGame("Call of Duty: Modern Warfare", "Infinity Ward", "Activision", LocalDate.of(2019, 10, 25), new BigDecimal("39.99"), false, genreRepository.findAll().get(2))
            );

            gameRepository.saveAll(games);

            var cyberpunk1 = fileService.load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTvBpMCXkraGga7Pqwhz-cqOVqQq-B6WU2bJg&s");
            var cyberpunk2 = fileService.load("https://cdn.alza.sk/Foto/ImgGalery/Image/cyberpunk-2077-tipy-cover.jpg");
            seedGameImages(games.get(0), List.of(
                    cyberpunk1,
                    cyberpunk2)
            );

            var ageofempires1 = fileService.load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTuAG8lAf-TEPeidFBdbyvyPsk9Extn0LJoPQ&s");
            var ageofempires2 = fileService.load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQRUGj52J2zDHqYlEruxArulGA6Wiogz_YCtg&s");
            seedGameImages(games.get(1), List.of(
                    ageofempires1,
                    ageofempires2)
            );

            var cod1 = fileService.load("https://image.api.playstation.com/vulcan/img/cfn/1130791_COqLRw6IGlDVHxyV8aqC9_YaF0sCN8IbOlVhzJ6sWm5tlpKTjN8npK2vA_mUJUdyQjP4-U4rEnk7cScmlvoLzXi7.png");
            seedGameImages(games.get(2), List.of(
                    cod1)
            );
        }
    }

    private void seedGameImages(Game game, List<String> imageUrls) {
        List<GameImage> images = imageUrls.stream()
                .map(url -> createGameImage(url, game))
                .toList();
        gameImageRepository.saveAll(images);
    }

    private GameImage createGameImage(String imageUrl, Game game) {
        GameImage image = new GameImage();
        image.setImageUrl(imageUrl);
        image.setPriority(1);
        image.setGame(game);
        return image;
    }

    private User createUser(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword_hash(password);
        return user;
    }

    private Genre createGenre(String name, String imageUrl, String description) {
        Genre genre = new Genre();
        genre.setName(name);
        genre.setImageUrl(imageUrl);
        genre.setDescription(description);
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
