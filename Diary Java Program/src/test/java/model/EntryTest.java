package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

class EntryTest {
    
    @Test
    void testConstructorAndGetters() {
        // Arrange
        String expectedTitle = "Test Entry";
        LocalDate expectedDate = LocalDate.now();
        int expectedId = 123;

        // Act
        Entry entry = new Entry(expectedTitle, expectedDate, expectedId);

        // Assert
        assertEquals(expectedId, entry.getId(), "ID should match the constructor argument");
        assertEquals(expectedTitle, entry.getTitle(), "Title should match the constructor argument");
        assertEquals(expectedDate, entry.getDate(), "Date should match the constructor argument");
    }

    @Test
    void testDifferentValues() {
        // Arrange
        String title = "Another Entry";
        LocalDate date = LocalDate.of(2025, 5, 10);
        int id = 456;

        // Act
        Entry entry = new Entry(title, date, id);

        // Assert
        assertEquals(id, entry.getId());
        assertEquals(title, entry.getTitle());
        assertEquals(date, entry.getDate());
    }

    @Test
    void testEdgeCases() {
        // Arrange
        String emptyTitle = "";
        LocalDate pastDate = LocalDate.of(2024, 1, 1);
        int zeroId = 0;
        int negativeId = -1;

        // Act
        Entry entry1 = new Entry(emptyTitle, pastDate, zeroId);
        Entry entry2 = new Entry("Entry with Negative ID", LocalDate.now(), negativeId);

        // Assert
        assertEquals(zeroId, entry1.getId());
        assertEquals(emptyTitle, entry1.getTitle());
        assertEquals(pastDate, entry1.getDate());

        assertEquals(negativeId, entry2.getId());
        assertEquals("Entry with Negative ID", entry2.getTitle());
        assertEquals(LocalDate.now(), entry2.getDate());
    }
}