package cda.bibliotheque.model;

public class Genre {

    private int id;
    private String label;

    public Genre(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public Genre(String label) {
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString(){
        return this.label;
    }

}
