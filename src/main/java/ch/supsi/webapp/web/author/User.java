package ch.supsi.webapp.web.author;

import ch.supsi.webapp.web.item.Item;
import ch.supsi.webapp.web.role.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor
@Entity
public class User {

    @Id
    private String username;

    private String password;

    public User(String username, String password, Role role) {
        BCryptPasswordEncoder cypher = new BCryptPasswordEncoder();
        this.username = username;
        this.password = cypher.encode(password);
        this.role = role;
    }

    public User(String username) {
        this.username = username;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    Role role;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    List<Item> confrontabili;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    List<Item> favoriti;
}

