package org.example.dto.genre;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateGenreDto {
    private String name;
    private MultipartFile image = null;
    private String description;
}
