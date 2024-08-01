package chapter_six;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LibraryCatalogTest {
    private Catalog<LibraryItem<String>> catalog;

    @BeforeEach
    void setUp() {
        catalog = new Catalog<>();
    }

    @Test
    void testAddItem() {
        LibraryItem<String> item = new LibraryItem<>("Test Book", "Test Author", "TEST001");
        catalog.addItem(item);
        assertEquals(1, catalog.getSize());
        assertEquals(item, catalog.getItem(0));
    }

    @Test
    void testRemoveItem() {
        LibraryItem<String> item = new LibraryItem<>("Test Book", "Test Author", "TEST001");
        catalog.addItem(item);
        catalog.removeItem(item);
        assertEquals(0, catalog.getSize());
    }

    @Test
    void testRemoveNonExistentItem() {
        LibraryItem<String> item = new LibraryItem<>("Test Book", "Test Author", "TEST001");
        assertThrows(IllegalArgumentException.class, () -> catalog.removeItem(item));
    }

    @Test
    void testGetItemOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> catalog.getItem(0));
    }
}