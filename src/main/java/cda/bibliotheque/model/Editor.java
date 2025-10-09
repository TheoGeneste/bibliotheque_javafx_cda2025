package cda.bibliotheque.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Editor {

    private int id;
    private String label;
    private LocalDate createdAt;
    private List<Book> booksDistribute = new ArrayList<>();

    public Editor(int id, String label, LocalDate createdAt) {
        this.id = id;
        this.label = label;
        this.createdAt = createdAt;
    }

    public Editor(String label, LocalDate createdAt) {
        this.id = id;
        this.label = label;
        this.createdAt = createdAt;
    }

    public Editor(int id, String label, Date createdAt, List<Book> books) {
        this.id = id;
        this.label = label;
        this.createdAt = createdAt.toLocalDate();
        this.booksDistribute = books;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }
    public Date getCreatedAt_DATE() {
        return Date.valueOf(createdAt);
    }

    public List<Book> getBooksDistribute() {
        return booksDistribute;
    }

    public void setBooksDistribute(List<Book> booksDistribute) {
        this.booksDistribute = booksDistribute;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
