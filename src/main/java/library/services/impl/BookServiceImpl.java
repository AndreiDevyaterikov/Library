package library.services.impl;

import library.dto.NewBookDto;
import library.dto.OrderDto;
import library.entities.AuthorEntity;
import library.entities.BookEntity;
import library.entities.GenreEntity;
import library.repositories.BookRepository;
import library.services.AuthorService;
import library.services.BookService;
import library.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    public BookEntity addNewBook(NewBookDto newBookDto) {

        var existBookOpt = bookRepository.findByTitle(newBookDto.getTitle());

        if (existBookOpt.isPresent()) {
            var existBook = existBookOpt.get();
            existBook.setCount(existBook.getCount() + newBookDto.getCount());
            bookRepository.save(existBook);
            return existBook;
        } else {
            var genres = newBookDto.getGenres()
                    .stream()
                    .map(genre -> {
                        var existGenreOpt = genreService.findByName(genre);
                        if (existGenreOpt.isPresent()) {
                            return existGenreOpt.get();
                        } else {
                            var newGenreEntity = GenreEntity.builder()
                                    .name(genre)
                                    .build();
                            genreService.saveGenre(newGenreEntity);
                            return newGenreEntity;
                        }
                    }).toList();

            var authors = newBookDto.getAuthors()
                    .stream()
                    .map(author -> {
                        var existAuthorOpt = authorService.findByName(author);
                        if (existAuthorOpt.isPresent()) {
                            return existAuthorOpt.get();
                        } else {
                            var newAuthorEntity = AuthorEntity.builder()
                                    .name(author)
                                    .build();
                            authorService.saveAuthor(newAuthorEntity);
                            return newAuthorEntity;
                        }
                    }).toList();

            var newBookEntity = BookEntity.builder()
                    .title(newBookDto.getTitle())
                    .count(newBookDto.getCount())
                    .authors(authors)
                    .genres(genres)
                    .build();
            bookRepository.save(newBookEntity);

            return newBookEntity;
        }
    }

    @Override
    public Page<BookEntity> getAll(List<OrderDto> ordersDto, Integer pageNumber, Integer pageSize) {

        Pageable page;

        if (Objects.isNull(ordersDto) || ordersDto.isEmpty()) {
            page = PageRequest.of(pageNumber - 1, pageSize);
        } else {
            var sortOrders = ordersDto
                    .stream()
                    .map(
                            orderDto -> new Sort.Order(
                                    orderDto.getDirection(), orderDto.getField()
                            )
                    ).
                    toList();
            page = PageRequest.of(pageNumber - 1, pageSize, Sort.by(sortOrders));
        }
        return bookRepository.findAll(page);
    }
}
