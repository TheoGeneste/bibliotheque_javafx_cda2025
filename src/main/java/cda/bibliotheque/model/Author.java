package cda.bibliotheque.model;

import java.time.LocalDate;

public class Author {
    private int id;
    private String lastname;
    private String firstname;
    private LocalDate born_at;

    public Author() {
    }

    public Author(int id, String lastname, String firstname, LocalDate born_at) {
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.born_at = born_at;
    }

    public int getId() {
        return id;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public LocalDate getBorn_at() {
        return born_at;
    }

    public void setBorn_at(LocalDate born_at) {
        this.born_at = born_at;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
}
