package cda.bibliotheque.model;

public class Client {
    private int id;
    private String firstname;
    private String lastname;
    private String email;

    public Client(int id, String firstname, String lastname, String email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public Client(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public int getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Override
    public String toString(){
        return this.firstname + " " + this.lastname;
    }
}
