package org.example.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_game_images")
public class GameImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 255, nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private int priority;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    @JsonManagedReference
    private Game game;
}
