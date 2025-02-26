package org.example.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "tbl_games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 100, nullable = false)
    private String developer;

    @Column(length = 100, nullable = false)
    private String publisher;

    @Column(name="release_date")
    private LocalDate release_date;

    @Column(nullable = false, precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) CHECK (price >= 0)")
    private BigDecimal price;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE", name = "is_free")
    private boolean isFree;

    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = false)
    @JsonManagedReference
    private Genre genre;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<GameImage> images;
}