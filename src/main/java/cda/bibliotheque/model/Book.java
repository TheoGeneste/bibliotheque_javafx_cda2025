package cda.bibliotheque.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Book {

    private int id;
    private String title;
    private LocalDate releaseDate;
    private boolean isAvailable;
    private List<Author> authors = new ArrayList<>();

    public Book() {

    }

    public Book(int id, String title, LocalDate releaseDate, boolean isAvailable) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.isAvailable = isAvailable;
    }

    public Book(String title, LocalDate releaseDate, boolean isAvailable) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.isAvailable = isAvailable;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public Date getReleaseDate_Date() {
        return Date.valueOf(releaseDate);
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String toStringAuthors() {
        String authorsString = "";
        for (Author a : authors) {
            authorsString = authorsString + a.toString();
        }
        return authorsString;
    }

}
