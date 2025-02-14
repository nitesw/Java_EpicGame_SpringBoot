package org.example.dto.game;

import lombok.Data;
import org.example.entities.Genre;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateGameDto {
    private String title;

    private String developer;

    private String publisher;

    private LocalDate release_date;

    private BigDecimal price;

    private boolean isFree;

    private Genre genre;
}