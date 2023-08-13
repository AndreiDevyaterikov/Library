package library.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authors")
public class AuthorEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SequenceAuthorId"
    )
    @SequenceGenerator(
            name = "SequenceAuthorId",
            sequenceName = "authors_id_seq",
            allocationSize = 1,
            initialValue = 5
    )
    @Column(name = "id")
    private Integer authorId;

    @Column(name = "name")
    private String name;
}
