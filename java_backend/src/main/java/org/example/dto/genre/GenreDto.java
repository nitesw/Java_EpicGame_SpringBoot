package org.example.dto.genre;

import lombok.Data;

@Data
public class GenreDto {
    private Integer id;
    private String name;
    private String imageUrl;
    private String description;
}
