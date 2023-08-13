package library.services.impl;

import library.entities.AuthorEntity;
import library.repositories.AuthorRepository;
import library.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    @Override
    public Optional<AuthorEntity> findByName(String name) {
        return authorRepository.findByName(name);
    }

    @Override
    public void saveAuthor(AuthorEntity authorEntity) {
        authorRepository.save(authorEntity);
    }
}
