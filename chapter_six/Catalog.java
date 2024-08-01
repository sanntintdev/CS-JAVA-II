package chapter_six;

import java.util.ArrayList;
import java.util.List;

public class Catalog<T extends LibraryItem<?>> {
    private List<T> items;

    public Catalog() {
        this.items = new ArrayList<>();
    }

    public void addItem(T item) {
        items.add(item);
        System.out.println("Item added successfully: " + item);
    }

    public void removeItem(T item) {
        if (items.remove(item)) {
            System.out.println("Item removed successfully: " + item);
        } else {
            throw new IllegalArgumentException("Item not found in the catalog.");
        }
    }

    public T getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        } else {
            throw new IndexOutOfBoundsException("Invalid index.");
        }
    }

    public void displayCatalog() {
        if (items.isEmpty()) {
            System.out.println("The catalog is empty.");
        } else {
            System.out.println("Catalog contents:");
            for (int i = 0; i < items.size(); i++) {
                System.out.println((i + 1) + ". " + items.get(i));
            }
        }
    }

    public int getSize() {
        return items.size();
    }
}
