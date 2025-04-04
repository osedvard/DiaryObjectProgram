package model;
import java.time.LocalDate;

public interface EntryInterface {
    public int getId();
    public LocalDate getDate();
    public String getTitle();
}