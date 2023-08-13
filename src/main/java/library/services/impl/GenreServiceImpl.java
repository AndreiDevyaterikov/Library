package library.services.impl;

import library.entities.GenreEntity;
import library.repositories.GenreRepository;
import library.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public Optional<GenreEntity> findByName(String name) {
        return genreRepository.findByName(name);
    }

    @Override
    public void saveGenre(GenreEntity genreEntity) {
        genreRepository.save(genreEntity);
    }
}
