package domen;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fullName;
    private String login;
    private String pass;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Book> books;

    public User(String fullName, String login, String pass) {
        this.fullName = fullName;
        this.login = login;
        this.pass = pass;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}

