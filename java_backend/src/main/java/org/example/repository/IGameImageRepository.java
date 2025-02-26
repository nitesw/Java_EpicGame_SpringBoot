package org.example.repository;

import org.example.entities.GameImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGameImageRepository extends JpaRepository<GameImage, Integer> {
}
