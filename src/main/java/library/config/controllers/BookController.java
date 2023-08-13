package library.config.controllers;

import library.dto.NewBookDto;
import library.entities.BookEntity;
import library.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public BookEntity addNewBook(@RequestBody NewBookDto newBookDto) {
        return bookService.addNewBook(newBookDto);
    }
}
