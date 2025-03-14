package org.example.mapper;

import org.example.dto.game.GameDto;
import org.example.entities.Game;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IGameMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "genre.name", target = "genreName")
    @Mapping(source = "genre.id", target = "genreId")
    @Mapping(source = "releaseDate", target = "releaseDate",
    dateFormat = "yyyy-MM-dd")
    GameDto toDto(Game game);
    List<GameDto> toDto(List<Game> games);
}
