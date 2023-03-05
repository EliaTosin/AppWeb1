package spring.demo.local.author;

import ch.supsi.webapp.web.role.Role;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class User {
    private String username;

    private String password;

    private Role role;

    public User(String username, String password, Role role) {
        BCryptPasswordEncoder cypher = new BCryptPasswordEncoder();
        this.username = username;
        this.password = cypher.encode(password);
        this.role = role;
    }

    public User() {}

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }
}

