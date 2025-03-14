package org.example.dto.game;

import lombok.Data;
import org.example.entities.Genre;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class EditGameDto {
    private int id;
    private String title;
    private String developer;
    private String publisher;
    private LocalDate releaseDate;
    private BigDecimal price;
    private boolean isFree;
    private int genreId;
    private List<MultipartFile> images;
}