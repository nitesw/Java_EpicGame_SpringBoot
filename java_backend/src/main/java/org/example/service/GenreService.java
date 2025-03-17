package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dto.genre.CreateGenreDto;
import org.example.dto.genre.EditGenreDto;
import org.example.dto.genre.GenreDto;
import org.example.entities.Genre;
import org.example.mapper.IGenreMapper;
import org.example.repository.IGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GenreService {
    private final IGenreRepository genreRepository;
    private final IGenreMapper genreMapper;
    private final FileService fileService;

    public List<GenreDto> getAllGenres() {
        return genreMapper.toDto(genreRepository.findAll());
    }

    public GenreDto getGenreById(int id) {
        var entity = genreRepository.findById(id).orElse(null);
        return genreMapper.toDto(entity);
    }

    public Genre createGenre(CreateGenreDto genreDto) {
        Genre genre = new Genre();
        genre.setName(genreDto.getName());
        var imageUrl = fileService.load(genreDto.getImage());
        genre.setImageUrl(imageUrl);
        genre.setDescription(genreDto.getDescription());
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
        if (updatedGenre.getDescription() != null && !updatedGenre.getDescription().isBlank()) {
            genre.setDescription(updatedGenre.getDescription());
        }
        if (updatedGenre.getImage() != null && !updatedGenre.getImage().isEmpty()) {
            fileService.remove(genre.getImageUrl());
            var imageUrl = fileService.load(updatedGenre.getImage());
            genre.setImageUrl(imageUrl);
        }
        return genreRepository.save(genre);
    }

    public void deleteGenre(int id) {
        var genreOptional = genreRepository.findById(id);
        if (genreOptional.isEmpty()) {
            return;
        }
        Genre genre = genreOptional.get();
        fileService.remove(genre.getImageUrl());
        genreRepository.deleteById(id);
    }
}
