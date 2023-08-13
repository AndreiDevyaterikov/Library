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
@Table(name = "genres")
public class GenreEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SequenceGenreId"
    )
    @SequenceGenerator(
            name = "SequenceGenreId",
            sequenceName = "genres_id_seq",
            allocationSize = 1,
            initialValue = 5
    )
    private Integer id;

    @Column(name = "name")
    private String name;
}
