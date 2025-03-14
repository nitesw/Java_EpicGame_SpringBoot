package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dto.game.CreateGameDto;
import org.example.dto.game.EditGameDto;
import org.example.dto.game.GameDto;
import org.example.entities.Game;
import org.example.entities.GameImage;
import org.example.entities.Genre;
import org.example.mapper.IGameMapper;
import org.example.repository.IGameImageRepository;
import org.example.repository.IGameRepository;
import org.example.repository.IGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GameService {
    private final IGameRepository gameRepository;
    private final IGameMapper gameMapper;
    private final FileService fileService;
    private final IGameImageRepository gameImageRepository;

    public List<GameDto> getAllGames() {
        return gameMapper.toDto(gameRepository.findAll());
    }

    public GameDto getGameById(int id) {
        var entity = gameRepository.findById(id).orElse(null);
        return gameMapper.toDto(entity);
    }

    public boolean createGame(CreateGameDto gameDto) {
        Game entity = new Game();
        entity.setTitle(gameDto.getTitle());
        entity.setDeveloper(gameDto.getDeveloper());
        entity.setPublisher(gameDto.getPublisher());
        entity.setReleaseDate(gameDto.getReleaseDate());
        entity.setPrice(gameDto.getPrice());
        entity.setFree(gameDto.isFree());
        var genre = new Genre();
        genre.setId(gameDto.getGenreId());
        entity.setGenre(genre);

        gameRepository.save(entity);

        int priority = 1;
        for (var img : gameDto.getImages()) {
            var imageUrl = fileService.load(img);
            var tmpImg = new GameImage();
            tmpImg.setPriority(priority);
            tmpImg.setImageUrl(imageUrl);
            tmpImg.setGame(entity);
            gameImageRepository.save(tmpImg);
            priority++;
        }
        return true;
    }

    public boolean updateGame(EditGameDto updatedGame) {
        Game entity = gameRepository.findById(updatedGame.getId())
                .orElse(null);
        if (entity == null) {
            return false;
        }

        entity.setTitle(updatedGame.getTitle());
        entity.setDeveloper(updatedGame.getDeveloper());
        entity.setPublisher(updatedGame.getPublisher());
        entity.setReleaseDate(updatedGame.getReleaseDate());
        entity.setPrice(updatedGame.getPrice());
        entity.setFree(updatedGame.isFree());

        var genre = new Genre();
        genre.setId(updatedGame.getGenreId());
        entity.setGenre(genre);

        Map<String, GameImage> existingImages = entity.getImages().stream()
                .collect(Collectors.toMap(GameImage::getImageUrl, img -> img));

        List<GameImage> updatedImages = new ArrayList<>();

        for (int i = 0; i < updatedGame.getImages().size(); i++) {
            var img = updatedGame.getImages().get(i);

            if ("old-image".equals(img.getContentType())){
                var imageUrl = img.getOriginalFilename();
                if (existingImages.containsKey(imageUrl)) {
                    var oldImage = existingImages.get(imageUrl);
                    oldImage.setPriority(i);
                    gameImageRepository.save(oldImage);
                    updatedImages.add(oldImage);
                }
            } else {
                var imageUrl = fileService.load(img);
                var newImage = new GameImage();
                newImage.setImageUrl(imageUrl);
                newImage.setPriority(i);
                newImage.setGame(entity);
                gameImageRepository.save(newImage);
            }
        }

        List<Integer> removeIds = new ArrayList<>();
        for (var img : entity.getImages()) {
            if (!updatedImages.contains(img)){
                fileService.remove(img.getImageUrl());
                removeIds.add(img.getId());
            }
        }

        gameImageRepository.deleteAllByIdInBatch(removeIds);
        gameRepository.save(entity);
        return true;
    }

    public boolean deleteGame(int id) {
        var entity = gameRepository.findById(id).orElse(null);
        if(entity == null){
            return false;
        }
        var imgs = entity.getImages();
        for (var img : imgs) {
            fileService.remove(img.getImageUrl());
            gameImageRepository.delete(img);
        }
        gameRepository.deleteById(id);
        return true;
    }
}
