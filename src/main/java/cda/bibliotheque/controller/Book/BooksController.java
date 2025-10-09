package cda.bibliotheque.controller.Book;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import cda.bibliotheque.App;
import cda.bibliotheque.dao.BookDAO;
import cda.bibliotheque.model.Book;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class BooksController {

    @FXML
    private TableView<Book> booksTable;

    @FXML
    private TableColumn<Book, Void> colActions;

    @FXML
    private TableColumn<Book, String> colAuthors;

    @FXML
    private TableColumn<Book, Boolean> colIsAvailable;

    @FXML
    private TableColumn<Book, LocalDate> colRealeaseDate;

    @FXML
    private TableColumn<Book, String> colTitle;

    private final BookDAO bookDAO = new BookDAO();
    private ObservableList<Book> books = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colTitle.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTitle()));
        colRealeaseDate.setCellValueFactory(cell -> new SimpleObjectProperty<LocalDate>(cell.getValue().getReleaseDate()));
        colIsAvailable.setCellValueFactory(cell -> new SimpleBooleanProperty(cell.getValue().getIsAvailable()));
        colAuthors.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().toStringAuthors()));
        colActions.setCellFactory(cell -> new TableCell<>() {
            Button editButton = new Button("Modifier");
            Button deleteButton = new Button("Supprimer");
            VBox vbox = new VBox(10, editButton, deleteButton);

            {
                editButton.setOnAction(event -> {
                    int index = getIndex();
                    Book bookToEdit = booksTable.getItems().get(index);
                    try {
                        FXMLLoader loader = new FXMLLoader(App.class.getResource("books/edit-book.fxml"));
                        Parent parent = loader.load();

                        EditBookController editBookController = loader.getController();
                        editBookController.setBook(bookToEdit);

                        App.getScene().setRoot(parent);
                    } catch (IOException e) {
                        System.out.println(e.getCause());
                        System.err.println("Erreur lors de la création du bouton edit ->" + e);
                    }
                });

                deleteButton.setOnAction(event -> {
                    int index = getIndex();
                    Book bookToEdit = booksTable.getItems().get(index);
                    bookDAO.deleteBook(bookToEdit.getId());
                    loadBooks();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(vbox);
                }
            }
        });
        loadBooks();
    }

    @FXML
    void switchToCreate(ActionEvent event) throws IOException {
        App.setRoot("books/create-book");
    }

    @FXML
    void switchToMain(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }

    private void loadBooks() {
        List<Book> books = bookDAO.getAllBook();
        this.books.setAll(books);
        booksTable.setItems(this.books);
    }

}
