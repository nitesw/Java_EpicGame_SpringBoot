package org.example.mapper;

import org.example.dto.game.GameDto;
import org.example.dto.game.GameImageDto;
import org.example.entities.Game;
import org.example.entities.GameImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface IGameMapper {
    default List<GameImageDto> toStr(List<GameImage> gameImages) {
        return gameImages == null
                ? Collections.emptyList()
                : gameImages.stream()
                .sorted(Comparator.comparing(GameImage::getPriority))
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private GameImageDto toDto(GameImage gameImage) {
        return new GameImageDto(gameImage.getId(), gameImage.getImageUrl(), gameImage.getPriority());
    }

    @Mapping(source = "id", target = "id")
    @Mapping(source = "genre.name", target = "genreName")
    @Mapping(source = "genre.id", target = "genreId")
    @Mapping(source = "releaseDate", target = "releaseDate",
    dateFormat = "yyyy-MM-dd")
    GameDto toDto(Game game);
    List<GameDto> toDto(List<Game> games);
}
