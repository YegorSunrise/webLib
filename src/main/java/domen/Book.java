package domen;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id","user"})
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "bookUser", nullable = false)
    private User user;

    public Book(String title, LocalDateTime date, User user) {
        this.title = title;
        this.date = date;
        this.user = user;
    }
}
