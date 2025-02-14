package org.example.controller;

import org.example.dto.genre.CreateGenreDto;
import org.example.dto.genre.EditGenreDto;
import org.example.entities.Genre;
import org.example.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
    @Autowired
    private GenreService genreService;

    @GetMapping
    public List<Genre> getAllGenres() {
        return genreService.getAllGenres();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable int id) {
        Genre genre = genreService.getGenreById(id);
        if (genre == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(genre);
    }

    @PostMapping
    public Genre createGenre(@RequestBody CreateGenreDto genreDto) {
        return genreService.createGenre(genreDto);
    }

    @PutMapping
    public ResponseEntity<Genre> updateGenre(@RequestBody EditGenreDto updatedGenre) {
        Genre genre = genreService.updateGenre(updatedGenre);
        if (genre == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(genre);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable int id) {
        genreService.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }
}
