package library.services;

import library.entities.GenreEntity;

import java.util.Optional;

public interface GenreService {
    Optional<GenreEntity> findByName(String name);
    void saveGenre(GenreEntity genreEntity);
}
