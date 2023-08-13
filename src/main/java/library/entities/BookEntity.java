package library.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "count")
    private Integer count;

    @ManyToMany
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<AuthorEntity> authors;

    @ManyToMany
    @JoinTable(
            name = "book_genres",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<GenreEntity> genres;
}
