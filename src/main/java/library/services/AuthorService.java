package library.services;

import library.entities.AuthorEntity;

import java.util.Optional;

public interface AuthorService {
    Optional<AuthorEntity> findByName(String name);
    void saveAuthor(AuthorEntity authorEntity);
}
