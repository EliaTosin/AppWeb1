package spring.demo.local.category;

import java.util.Objects;

public class Category {

//    @Id
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public Category() {}

    public String getName() {
        return name;
    }
}

