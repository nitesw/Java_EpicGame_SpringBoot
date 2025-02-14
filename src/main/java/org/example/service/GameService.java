package org.example.service;

import org.example.dto.game.CreateGameDto;
import org.example.dto.game.EditGameDto;
import org.example.entities.Game;
import org.example.entities.Genre;
import org.example.repository.IGameRepository;
import org.example.repository.IGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {
    @Autowired
    private IGameRepository gameRepository;

    @Autowired
    private IGenreRepository genreRepository;

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Game getGameById(int id) {
        return gameRepository.findById(id).orElse(null);
    }

    public Game createGame(CreateGameDto gameDto) {
        Game game = new Game();
        game.setTitle(gameDto.getTitle());
        game.setDeveloper(gameDto.getDeveloper());
        game.setPublisher(gameDto.getPublisher());
        game.setRelease_date(gameDto.getRelease_date());
        game.setPrice(gameDto.getPrice());
        game.setFree(gameDto.isFree());

        if (gameDto.getGenre() != null) {
            game.setGenre(gameDto.getGenre());
        }

        return gameRepository.save(game);
    }

    public Game updateGame(EditGameDto updatedGame) {
        Game game = gameRepository.findById(updatedGame.getId())
                .orElse(null);
        if (game == null) {
            return null;
        }
        if (updatedGame.getTitle() != null && !updatedGame.getTitle().isBlank()) {
            game.setTitle(updatedGame.getTitle());
        }
        if (updatedGame.getDeveloper() != null && !updatedGame.getDeveloper().isBlank()) {
            game.setDeveloper(updatedGame.getDeveloper());
        }
        if (updatedGame.getPublisher() != null && !updatedGame.getPublisher().isBlank()) {
            game.setPublisher(updatedGame.getPublisher());
        }
        if (updatedGame.getRelease_date() != null) {
            game.setRelease_date(updatedGame.getRelease_date());
        }
        if (updatedGame.getPrice() != null) {
            game.setPrice(updatedGame.getPrice());
        }
        game.setFree(updatedGame.isFree());

        if (updatedGame.getGenre() != null) {
            game.setGenre(updatedGame.getGenre());
        }
        return gameRepository.save(game);
    }

    public void deleteGame(int id) {
        if (!gameRepository.existsById(id)) {
            return;
        }
        gameRepository.deleteById(id);
    }
}
