package org.example.service;

import org.example.dto.genre.CreateGenreDto;
import org.example.dto.genre.EditGenreDto;
import org.example.entities.Genre;
import org.example.repository.IGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    @Autowired
    private IGenreRepository genreRepository;

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre getGenreById(int id) {
        return genreRepository.findById(id).orElse(null);
    }

    public Genre createGenre(CreateGenreDto genreDto) {
        Genre genre = new Genre();
        genre.setName(genreDto.getName());
        return genreRepository.save(genre);
    }

    public Genre updateGenre(EditGenreDto updatedGenre) {
        Genre genre = genreRepository.findById(updatedGenre.getId())
                .orElse(null);
        if (genre == null) {
            return null;
        }
        if (updatedGenre.getName() != null && !updatedGenre.getName().isBlank()) {
            genre.setName(updatedGenre.getName());
        }
        return genreRepository.save(genre);
    }

    public void deleteGenre(int id) {
        if (!genreRepository.existsById(id)) {
            return;
        }
        genreRepository.deleteById(id);
    }
}
