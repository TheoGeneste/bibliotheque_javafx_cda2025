package cda.bibliotheque.controller.Book;

import java.io.IOException;

import cda.bibliotheque.App;
import cda.bibliotheque.dao.BookDAO;
import cda.bibliotheque.model.Author;
import cda.bibliotheque.model.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
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

    private final BookDAO bookDAO = new BookDAO();


    @FXML
    void submit(ActionEvent event) throws IOException {
        Book book = new Book(inputTitle.getText(), inputReleaseDate.getValue(), inputAvailable.isSelected());
        bookDAO.addBook(book);
        App.setRoot("books/books");
    }

    @FXML
    void switchToBooks(ActionEvent event) throws IOException {
        App.setRoot("books/books");

    }

}
