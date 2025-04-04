package model;
import java.time.LocalDate;

public class TextEntry extends Entry {
    private String text;
    public TextEntry(String title, String text, LocalDate date, int id) {
        super(title, date, id);
        this.text = text;
    }
    public String getText() {
        return this.text;
    }
}
