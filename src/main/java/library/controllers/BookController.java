package library.controllers;

import jakarta.annotation.Nullable;
import library.dto.NewBookDto;
import library.dto.OrderDto;
import library.dto.SearchCriteriaDto;
import library.entities.BookEntity;
import library.services.BookService;
import library.services.impl.BookServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookServiceImpl bookService;

    @PostMapping("/add")
    public BookEntity addNewBook(@RequestBody NewBookDto newBookDto) {
        return bookService.addNewBook(newBookDto);
    }

    @PostMapping("/all")
    public Page<BookEntity> getAll(
            @Nullable @RequestBody List<OrderDto> ordersDto,
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize
    ) {
        return bookService.getAll(ordersDto, pageNumber, pageSize);
    }

    @PostMapping("/criteria")
    public Page<BookEntity> getAllCriteria (
            @Nullable @RequestBody List<SearchCriteriaDto> criteriasDto,
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize
    ) {
        return bookService.getAllCriteria(criteriasDto, pageNumber, pageSize);
    }
}
