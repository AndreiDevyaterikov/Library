package library.repositories;

import library.entities.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository  extends JpaRepository<AuthorEntity, Integer> {
    Optional<AuthorEntity> findByName(String name);
}
