package library.services.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import library.dto.NewBookDto;
import library.dto.OrderDto;
import library.dto.SearchCriteriaDto;
import library.entities.AuthorEntity;
import library.entities.BookEntity;
import library.entities.GenreEntity;
import library.enums.Operations;
import library.repositories.BookRepository;
import library.services.AuthorService;
import library.services.BookService;
import library.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final EntityManager entityManager;

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

    public Page<BookEntity> getAllCriteria(List<SearchCriteriaDto> criteriesDto, Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BookEntity> criteriaQuery = criteriaBuilder.createQuery(BookEntity.class);
        Root<BookEntity> root = criteriaQuery.from(BookEntity.class);

        Predicate[] predicates = new Predicate[0];

        if (Objects.nonNull(criteriesDto)) {

            predicates = new Predicate[criteriesDto.size()];

            List<Order> orders = new ArrayList<>();

            for (int i = 0; i < criteriesDto.size(); i++) {
                var criteria = criteriesDto.get(i);

                if (Objects.equals(criteria.getField(),"authors")) {
                    Join<AuthorEntity, BookEntity> bookAuthors = root.join("authors");
                    if (criteria.getOperation().equalsIgnoreCase(":")) {
                        predicates[i] = criteriaBuilder.equal(bookAuthors.get("name"), criteria.getValue());
                    } else if (criteria.getOperation().equals("like")) {
                        predicates[i] = criteriaBuilder.like(bookAuthors.get("name"), "%" + criteria.getValue() + "%");
                    } else {
                        throw new RuntimeException(
                                String.format(
                                        "Not supported operation %s for field %s and value %s",
                                        criteria.getOperation(), criteria.getField(), criteria.getValue()
                                )
                        );
                    }
                } else if (criteria.getField().equals("genres")) {
                    Join<GenreEntity, BookEntity> bookAuthors = root.join("genres");
                    if (criteria.getOperation().equals(":")) {
                        predicates[i] = criteriaBuilder.equal(bookAuthors.get("name"), criteria.getValue());
                    } else if (criteria.getOperation().equals("like")) {
                        predicates[i] = criteriaBuilder.like(bookAuthors.get("name"), "%" + criteria.getValue() + "%");
                    } else {
                        throw new RuntimeException(
                                String.format(
                                        "Not supported operation %s for field %s and value %s",
                                        criteria.getOperation(), criteria.getField(), criteria.getValue()
                                )
                        );
                    }
                } else {
                    switch (criteria.getOperation()) {

                        case ":" -> predicates[i] = criteriaBuilder.equal(
                                root.get(criteria.getField()), criteria.getValue()
                        );

                        case "like" -> predicates[i] = criteriaBuilder.like(
                                root.get(criteria.getField()), "%" + criteria.getValue() + "%"
                        );

                        case "<" -> predicates[i] = criteriaBuilder.lessThan(
                                root.get(criteria.getField()), criteria.getValue()
                        );

                        case "<=" -> predicates[i] = criteriaBuilder.lessThanOrEqualTo(
                                root.get(criteria.getField()), criteria.getValue()
                        );

                        case ">" -> predicates[i] = criteriaBuilder.greaterThan(
                                root.get(criteria.getField()), criteria.getValue()
                        );

                        case ">=" -> predicates[i] = criteriaBuilder.greaterThanOrEqualTo(
                                root.get(criteria.getField()), criteria.getValue()
                        );
                    }
                }

                if (Objects.nonNull(criteria.getDirection())) {
                    if (criteria.getDirection().equals(Sort.Direction.ASC)) {
                        orders.add(
                                criteriaBuilder.asc(
                                        root.get(criteria.getField())
                                )
                        );
                    } else {
                        orders.add(
                                criteriaBuilder.desc(
                                        root.get(criteria.getField())
                                )
                        );
                    }
                }

            }
            if (!orders.isEmpty()) {
                criteriaQuery.orderBy(orders);
            }

            criteriaQuery.select(root)
                    .where(predicates);
        }

        List<BookEntity> pagedBookEntity = paginateQuery(entityManager.createQuery(criteriaQuery), pageable).getResultList();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<BookEntity> booksCountRoot = countQuery.from(BookEntity.class);

        countQuery.select(criteriaBuilder.count(booksCountRoot));
        var lotalCount = entityManager.createQuery(countQuery).getSingleResult();
        return new PageImpl<>(pagedBookEntity, pageable, lotalCount);
    }

    private  <T> TypedQuery<T> paginateQuery(TypedQuery<T> query, Pageable pageable) {
        if (pageable.isPaged()) {
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }
        return query;
    }
}
