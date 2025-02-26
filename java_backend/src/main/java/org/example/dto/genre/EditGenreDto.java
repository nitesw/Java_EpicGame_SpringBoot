package org.example.dto.genre;

import lombok.Data;

@Data
public class EditGenreDto {
    private int id;

    private String name;

    private String imageUrl;

    private String description;
}
