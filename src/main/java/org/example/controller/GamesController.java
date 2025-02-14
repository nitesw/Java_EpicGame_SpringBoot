package org.example.controller;

import org.example.dto.game.CreateGameDto;
import org.example.dto.game.EditGameDto;
import org.example.entities.Game;
import org.example.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GamesController {
    @Autowired
    private GameService service;

    @GetMapping
    public List<Game> getAllGames() {
        return service.getAllGames();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable int id) {
        Game game = service.getGameById(id);
        if (game == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(game);
    }

    @PostMapping
    public Game createGame(@RequestBody CreateGameDto gameDto) {
        return service.createGame(gameDto);
    }

    @PutMapping
    public Game updateGame(@RequestBody EditGameDto updatedGame) {
        return service.updateGame(updatedGame);
    }

    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable int id) {
        service.deleteGame(id);
    }
}
