package library.services;

import library.dto.NewBookDto;
import library.dto.OrderDto;
import library.entities.BookEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {
    BookEntity addNewBook(NewBookDto newBookDto);
    Page<BookEntity> getAll(
            List<OrderDto> orderDtos,
            Integer pageNumber,
            Integer pageSize
    );
}
