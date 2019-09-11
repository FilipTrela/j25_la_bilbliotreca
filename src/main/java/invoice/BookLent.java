package invoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookLent implements IBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ToString.Exclude
    @ManyToOne
    private Reader client;

    @ToString.Exclude
    @ManyToOne
    private Book book;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime dateLent;

    private LocalDateTime dateReturned;

    public BookLent(Reader client, Book book) {
        this.client = client;
        this.book = book;
    }
}
