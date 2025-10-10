package cda.bibliotheque.controller.Genre;

import java.io.IOException;

import cda.bibliotheque.App;
import cda.bibliotheque.dao.GenreDAO;
import cda.bibliotheque.model.Genre;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class EditGenreController {

    @FXML
    private TextField inputLabel;

    private final GenreDAO genreDAO = new GenreDAO();
    private ObjectProperty<Genre> genre = new SimpleObjectProperty<>();

    @FXML
    public void initialize(){
        genre.addListener((obs, oldValue, newValue) -> {
            inputLabel.setText(newValue.getLabel());
        });
    }

    @FXML
    void submit(ActionEvent event) throws IOException {
        Genre genre = this.genre.get();
        genre.setLabel(inputLabel.getText());
        genreDAO.updateGenre(genre);
        App.setRoot("genres/genres");
    }

    @FXML
    void switchToGenres(ActionEvent event) throws IOException {
        App.setRoot("genres/genres");
    }

    public void setGenre(Genre genre ){
        this.genre.set(genre);
    }

}
