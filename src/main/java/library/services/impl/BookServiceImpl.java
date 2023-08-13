package library.services.impl;

import library.dto.NewBookDto;
import library.entities.AuthorEntity;
import library.entities.BookEntity;
import library.repositories.BookRepository;
import library.services.AuthorService;
import library.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;

    @Override
    public BookEntity addNewBook(NewBookDto newBookDto) {

        var existBookOpt = bookRepository.findByTitle(newBookDto.getTitle());

        if (existBookOpt.isPresent()) {
            var existBook = existBookOpt.get();
            existBook.setCount(existBook.getCount() + newBookDto.getCount());
            bookRepository.save(existBook);
            return existBook;
        } else {
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
                    .build();
            bookRepository.save(newBookEntity);

            return newBookEntity;
        }
    }
}
