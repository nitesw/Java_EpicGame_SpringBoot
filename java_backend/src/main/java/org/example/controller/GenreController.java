package org.example.controller;

import org.example.dto.genre.CreateGenreDto;
import org.example.dto.genre.EditGenreDto;
import org.example.dto.genre.GenreDto;
import org.example.entities.Genre;
import org.example.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
    @Autowired
    private GenreService genreService;

    @GetMapping
    public List<GenreDto> getAllGenres() {
        return genreService.getAllGenres();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDto> getGenreById(@PathVariable int id) {
        GenreDto genre = genreService.getGenreById(id);
        if (genre == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(genre);
    }

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public Genre createGenre(@ModelAttribute CreateGenreDto genreDto) {
        return genreService.createGenre(genreDto);
    }

    @PutMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Genre> updateGenre(@ModelAttribute EditGenreDto updatedGenre) {
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
