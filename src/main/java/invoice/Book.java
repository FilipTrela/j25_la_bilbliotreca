package invoice;


import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Book implements IBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private LocalDate yearWritten;
    @Column(nullable = false)
    private int numberOfPage;
    @Formula(value = "(year(now()) - year(yearWritten))")
    private int howOld;
    @Column(nullable = false)
    private int numberOfAvailableCopies;
    @Formula(value = "(select count(bl.id) from booklent bl WHERE bl.book_id = id and bl.dateReturned is null)")
    private Integer numberIfBorrowedCopies;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "books", fetch = FetchType.EAGER)
    private Set<Author> authors;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    private Set<BookLent> currentLents;

    public Book(String title, LocalDate yearWritten, int numberOfPage, int numberOfAvailableCopies) {
        this.title = title;
        this.yearWritten = yearWritten;
        this.numberOfPage = numberOfPage;
        this.numberOfAvailableCopies = numberOfAvailableCopies;
    }
}
