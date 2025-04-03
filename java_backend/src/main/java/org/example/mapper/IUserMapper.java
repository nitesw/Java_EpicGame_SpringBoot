package org.example.mapper;

import org.example.dto.user.UserDto;
import org.example.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    @Mapping(source = "id", target = "id")
    UserDto toDto(UserEntity user);
    List<UserDto> toDto(List<UserEntity> users);
}
