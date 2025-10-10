package cda.bibliotheque.controller.Book;

import java.io.IOException;
import java.util.List;

import cda.bibliotheque.App;
import cda.bibliotheque.dao.AuthorDAO;
import cda.bibliotheque.dao.BookDAO;
import cda.bibliotheque.dao.EditorDAO;
import cda.bibliotheque.dao.GenreDAO;
import cda.bibliotheque.model.Author;
import cda.bibliotheque.model.Book;
import cda.bibliotheque.model.Editor;
import cda.bibliotheque.model.Genre;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

public class CreateBookController {

    @FXML
    private CheckBox inputAvailable;

    @FXML
    private TextField inputTitle;

    @FXML
    private DatePicker inputReleaseDate;

    @FXML
    private ListView<Author> inputAuthors;

    @FXML
    private ListView<Genre> inputGenres;

    @FXML
    private ListView<Editor> inputEditors;

    private final BookDAO bookDAO = new BookDAO();
    private final AuthorDAO authorDAO = new AuthorDAO();
    private final GenreDAO genreDAO = new GenreDAO();
    private final EditorDAO editorDAO = new EditorDAO();


    @FXML   
    public void initialize() {
        List<Author> authors = authorDAO.getAllAuthors();
        inputAuthors.getItems().setAll(authors);
        inputAuthors.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        List<Genre> genres = genreDAO.getAllGenre();
        inputGenres.getItems().setAll(genres);
        inputGenres.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        List<Editor> editors = editorDAO.getAllEditor();
        inputEditors.getItems().setAll(editors);
        inputEditors.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    void submit(ActionEvent event) throws IOException {
        Book book = new Book(inputTitle.getText(), inputReleaseDate.getValue(), inputAvailable.isSelected(), inputAuthors.getSelectionModel().getSelectedItems(), inputGenres.getSelectionModel().getSelectedItems(), inputEditors.getSelectionModel().getSelectedItems());
        bookDAO.addBook(book);
        App.setRoot("books/books");
    }

    @FXML
    void switchToBooks(ActionEvent event) throws IOException {
        App.setRoot("books/books");

    }

}
