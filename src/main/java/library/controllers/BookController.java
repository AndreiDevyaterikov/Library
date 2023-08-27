package library.controllers;

import jakarta.annotation.Nullable;
import library.dto.BookDto;
import library.dto.SearchCriteriaDto;
import library.entities.BookEntity;
import library.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/add")
    public BookEntity addNewBook(@RequestBody BookDto bookDto) {
        return bookService.addNewBook(bookDto);
    }

    @PostMapping("/all")
    public Page<BookEntity> getAll(
            @Nullable @RequestBody List<SearchCriteriaDto> criteriesDto,
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize
    ) {
        return bookService.getAll(criteriesDto, pageNumber, pageSize);
    }

    @PatchMapping
    public BookEntity editBook(@RequestParam Integer bookId,
                               @RequestBody BookDto book) {
        return bookService.editBook(bookId, book);
    }
}
