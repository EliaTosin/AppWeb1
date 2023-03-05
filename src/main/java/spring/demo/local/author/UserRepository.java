package spring.demo.local.author;

import spring.demo.local.author.User;

import java.util.*;

public class UserRepository {

    private HashMap<String, User> users;

    public UserRepository() {
        users = new HashMap<>();
    }

    public Optional<User> findById(String username) {
        if (users.get(username) != null) {
            return Optional.of(users.get(username));
        }
        return Optional.empty();
    }

    public int getSize() {
        return users.values().size();
    }

    public void save(User u) {
        users.put(u.getUsername(), u);
    }

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }
}
