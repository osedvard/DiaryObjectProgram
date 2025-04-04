package model;

import java.time.LocalDate;

public class Entry implements EntryInterface{
    protected int id;
    protected String title;
    protected LocalDate date;
    public Entry(String title, LocalDate date, int id) {
        this.title = title;
        this.date = date;
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public LocalDate getDate() {
        return this.date;
    } 
}
