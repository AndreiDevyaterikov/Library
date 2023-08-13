package library.services;

import library.dto.NewBookDto;
import library.entities.BookEntity;

public interface BookService {
    BookEntity addNewBook(NewBookDto newBookDto);
}
