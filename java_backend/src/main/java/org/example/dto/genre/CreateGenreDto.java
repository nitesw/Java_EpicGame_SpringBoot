package org.example.dto.genre;

import lombok.Data;

@Data
public class CreateGenreDto {
    private String name;
    private String imageUrl;
    private String description;
}
