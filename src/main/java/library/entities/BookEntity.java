package library.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SequenceBookId"
    )
    @SequenceGenerator(
            name = "SequenceBookId",
            sequenceName = "books_id_seq",
            allocationSize = 1,
            initialValue = 5
    )
    private Integer bookId;

    @Column(name = "title")
    private String bookTitle;

    @ManyToMany
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<AuthorEntity> authors;
}
