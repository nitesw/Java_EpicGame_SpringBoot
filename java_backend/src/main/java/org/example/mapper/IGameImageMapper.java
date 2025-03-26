package org.example.mapper;

import org.example.dto.game.GameImageDto;
import org.example.entities.GameImage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IGameImageMapper {
    GameImageDto toDto(GameImage image);
    List<GameImageDto> toDto(Iterable<GameImage> images);
}
