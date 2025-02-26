package org.example.mapper;

import org.example.dto.genre.GenreDto;
import org.example.entities.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IGenreMapper {
    @Mapping(target = "id", source = "id")
    GenreDto toDto(Genre genre);
    List<GenreDto> toDto(List<Genre> genres);
}
