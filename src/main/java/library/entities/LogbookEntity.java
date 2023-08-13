package library.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "logbook")
public class LogbookEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SequenceLogId"
    )
    @SequenceGenerator(
            name = "SequenceLogId",
            sequenceName = "logs_id_seq",
            allocationSize = 1,
            initialValue = 5
    )
    private Integer id;

    @Column(name = "reader")
    private String reader;

    @Column(name = "issue_date")
    private Timestamp issueDate;

    @Column(name = "return_date")
    private Timestamp returnDate;

    @OneToOne
    @JoinColumn(name = "book_id")
    private BookEntity book;

    @Column(name = "status")
    private String status;
}
