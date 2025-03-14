package org.example.dto.game;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.example.entities.Game;

@Data
public class GameImageDto {
    private Integer id;
    private String imageUrl;
    private int priority;
//    private Integer gameId;
}
