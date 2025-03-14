package org.example.controller;

import org.example.dto.game.CreateGameDto;
import org.example.dto.game.EditGameDto;
import org.example.dto.game.GameDto;
import org.example.entities.Game;
import org.example.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api/games")
public class GameController {
    @Autowired
    private GameService gameService;

    @GetMapping
    public List<GameDto> getAllGames() {
        return gameService.getAllGames();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDto> getGameById(@PathVariable int id) {
        GameDto game = gameService.getGameById(id);
        if (game == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(game);
    }

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createGame(@ModelAttribute CreateGameDto gameDto) {
        var created = gameService.createGame(gameDto);
        if (!created) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateGame(@ModelAttribute EditGameDto updatedGame) {
        var updated = gameService.updateGame(updatedGame);
        if (!updated) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable int id) {
        return gameService.deleteGame(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}