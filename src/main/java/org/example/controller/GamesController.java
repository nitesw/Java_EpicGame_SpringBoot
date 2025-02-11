package org.example.controller;

import org.example.entities.Game;
import org.example.entities.Genre;
import org.example.repository.IGameRepository;
import org.example.repository.IGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GamesController {
    @Autowired
    private IGameRepository repository;
    @Autowired
    private IGenreRepository genreRepository;

    @GetMapping
    public List<Game> getAllGames() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable int id) {
        Game game = repository.findById(id).orElse(null);
        if (game == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(game);
    }

    @PostMapping
    public Game createGame(@RequestBody Game game) {
        return repository.save(game);
    }

    @PutMapping("/{id}")
    public Game updateGame(@PathVariable int id, @RequestBody Game updatedGame) {
        return repository.findById(id)
                .map(game -> {
                    if (updatedGame.getTitle() != null) {
                        game.setTitle(updatedGame.getTitle());
                    }
                    if (updatedGame.getDeveloper() != null) {
                        game.setDeveloper(updatedGame.getDeveloper());
                    }
                    if (updatedGame.getPublisher() != null) {
                        game.setPublisher(updatedGame.getPublisher());
                    }
                    if (updatedGame.getRelease_date() != null) {
                        game.setRelease_date(updatedGame.getRelease_date());
                    }
                    if (updatedGame.getPrice() != null) {
                        game.setPrice(updatedGame.getPrice());
                    }
                    game.setFree(updatedGame.isFree());
                    if (updatedGame.getGenre() != null && updatedGame.getGenre().getId() != 0) {
                        Genre genre = genreRepository.findById(updatedGame.getGenre().getId())
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found"));
                        game.setGenre(genre);
                    }
                    return repository.save(game);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable int id){
        repository.deleteById(id);
    }
}
