package spring.demo.local.category;

import java.util.*;

public class CategoryRepository {
    private Set<Category> categories;

    public CategoryRepository() {
        categories = new HashSet<>();
    }


    public List<Category> findAll() {
        return new ArrayList<>(categories);
    }

    public void save(Category c) {
        categories.add(c);
    }

    public Optional<Category> findById(String str) {
        for (Category c : categories) {
            if (c.getName() == str) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }
}
