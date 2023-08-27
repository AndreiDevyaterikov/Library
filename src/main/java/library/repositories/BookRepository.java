package library.repositories;

import library.entities.BookEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends PagingAndSortingRepository<BookEntity, Integer> {
    Optional<BookEntity> findByTitle(String title);
    Optional<BookEntity> findById(Integer id);
    void save(BookEntity bookEntity);
}
