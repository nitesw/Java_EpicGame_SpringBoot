package org.example.dto.game;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class GameDto {
    private Integer id;
    private String title;
    private String developer;
    private String publisher;
    private String releaseDate;
    private BigDecimal price;
    private boolean isFree;
    private String genreName;
    private Integer genreId;
    private List<GameImageDto> images;
}
