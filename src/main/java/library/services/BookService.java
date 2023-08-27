package library.services;

import library.dto.BookDto;
import library.dto.SearchCriteriaDto;
import library.entities.BookEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {

    BookEntity addNewBook(BookDto bookDto);
    Page<BookEntity> getAll(
            List<SearchCriteriaDto> criteriesDto,
            Integer pageNumber,
            Integer pageSize
    );
    BookEntity editBook(Integer bookId, BookDto selectedBook);
}
