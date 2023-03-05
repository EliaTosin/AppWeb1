package spring.demo.local.item;

import spring.demo.local.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ItemRepository {
    private HashMap<Integer, Item> items;
    private int ids = 0;

    public ItemRepository() {
        items = new HashMap<>();
    }

    public Optional<Item> findById(int id) {
        if (items.get(id) != null) {
            return Optional.of(items.get(id));
        }
        return Optional.empty();
    }

    public int getSize() {
        return items.values().size();
    }

    public Item save(Item i, boolean isNew) {
        if (isNew) {
            ids++;
            i.setId(ids);
        }
        items.put(i.getId(), i);
        return i;
    }

    public List<Item> findAll() {
        return new ArrayList<>(items.values());
    }

    public List<Item> findAllByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String filter, String filter1) {
        ArrayList<Item> filtered = new ArrayList<>();
        for (Item i : items.values()) {
            if (i.getTitle().toLowerCase().contains(filter.toLowerCase()) ||
                    i.getDescription().toLowerCase().contains(filter1.toLowerCase())) {
                filtered.add(i);
            }
        }

        return filtered;
    }

    public boolean existsById(int id) {
        return items.containsKey(id);
    }

    public void deleteById(int id) {
        items.remove(id);
    }
}
