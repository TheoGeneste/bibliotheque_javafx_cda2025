package cda.bibliotheque.controller.Book;

import java.io.IOException;
import java.util.List;

import cda.bibliotheque.App;
import cda.bibliotheque.dao.AuthorDAO;
import cda.bibliotheque.dao.BookDAO;
import cda.bibliotheque.model.Author;
import cda.bibliotheque.model.Book;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

public class EditBookController {

    private ObjectProperty<Book> book = new SimpleObjectProperty<>();

    @FXML
    private ListView<Author> inputAuthors;

    @FXML
    private CheckBox inputAvailable;

    @FXML
    private DatePicker inputReleaseDate;

    @FXML
    private TextField inputTitle;

    private final AuthorDAO authorDAO = new AuthorDAO();
    private final BookDAO bookDAO = new BookDAO();

    @FXML
    public void initialize() {
        List<Author> authors = authorDAO.getAllAuthors();
        inputAuthors.getItems().addAll(authors);
        inputAuthors.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.book.addListener((obs, oldValue, newValue) -> {
            // Sélectionne les auteurs du livre
            MultipleSelectionModel<Author> selectionModel = inputAuthors.getSelectionModel();
            selectionModel.clearSelection();
            for (Author author : newValue.getAuthors()) {
                int index = inputAuthors.getItems().indexOf(author);
                if (index >= 0) {
                    selectionModel.select(index);
                }
            }
            inputAvailable.setSelected(newValue.getIsAvailable());
            inputReleaseDate.setValue(newValue.getReleaseDate());
            inputTitle.setText(newValue.getTitle());
        });
    }

    @FXML
    void submit(ActionEvent event) throws IOException {
        Book bookEdited = this.book.get();
        bookEdited.setIsAvailable(inputAvailable.selectedProperty().getValue());
        bookEdited.setReleaseDate(inputReleaseDate.getValue());
        bookEdited.setTitle(inputTitle.getText());
        bookEdited.setAuthors(inputAuthors.getSelectionModel().getSelectedItems());
        bookDAO.updateBook(bookEdited);
        App.setRoot("books/books");
    }

    @FXML
    void switchToBooks(ActionEvent event) throws IOException {
        App.setRoot("books/books");
    }

    public void setBook(Book book) {
        this.book.set(book);
    }

}
