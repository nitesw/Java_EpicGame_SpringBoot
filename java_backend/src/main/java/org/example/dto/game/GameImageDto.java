package org.example.dto.game;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entities.Game;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameImageDto {
    private Integer id;
    private String imageUrl;
    private int priority;
}
