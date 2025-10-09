package cda.bibliotheque.controller.Author;

import java.io.IOException;

import cda.bibliotheque.App;
import cda.bibliotheque.dao.AuthorDAO;
import cda.bibliotheque.model.Author;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class EditAuthorController {

    private final ObjectProperty<Author> author = new SimpleObjectProperty<>();
    private final AuthorDAO authorDAO = new AuthorDAO();

    @FXML
    private DatePicker inputBornDate;

    @FXML
    private TextField inputFirstName;

    @FXML
    private TextField inputLastName;

    @FXML
    void submit(ActionEvent event) throws IOException {
        Author newAuthor = author.get();
        newAuthor.setBorn_at(inputBornDate.getValue());
        newAuthor.setFirstname(inputFirstName.getText());
        newAuthor.setLastname(inputLastName.getText());
        authorDAO.updateAuthor(newAuthor);
        App.setRoot("authors/authors");
    }

    @FXML
    public void initialize() {
        author.addListener((obs, oldAuthor, newAuthor) -> {
            if (newAuthor != null) {
                inputBornDate.setValue(newAuthor.getBorn_at());
                inputFirstName.setText(newAuthor.getFirstname());
                inputLastName.setText(newAuthor.getLastname());
            }
        });
    }

    public EditAuthorController() {

    }

    public void setAuthor(Author author) {
        this.author.set(author);
    }

    @FXML
    private void switchToAuthors() throws IOException {
        App.setRoot("authors/authors");
    }

}
