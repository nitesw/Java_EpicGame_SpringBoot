package org.example.controller;

import org.example.entities.Genre;
import org.example.repository.IGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
    @Autowired
    private IGenreRepository repository;

    @GetMapping
    public List<Genre> getAllGenres() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable int id) {
        Genre genre = repository.findById(id).orElse(null);
        if (genre == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(genre);
    }

    @PostMapping
    public Genre createGenre(@RequestBody Genre genre) {
        return repository.save(genre);
    }

    @PutMapping("/{id}")
    public Genre updateGenre(@PathVariable int id, @RequestBody Genre updatedGenre) {
        return repository.findById(id)
                .map(genre -> {
                    if (updatedGenre.getName() != null) {
                        genre.setName(updatedGenre.getName());
                    }
                    return repository.save(genre);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteGenre(@PathVariable int id){
        repository.deleteById(id);
    }
}
