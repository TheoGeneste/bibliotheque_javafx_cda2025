package cda.bibliotheque.controller.Borrow;

import java.io.IOException;

import cda.bibliotheque.App;
import cda.bibliotheque.dao.BookDAO;
import cda.bibliotheque.dao.BorrowDAO;
import cda.bibliotheque.dao.ClientDAO;
import cda.bibliotheque.model.Book;
import cda.bibliotheque.model.Borrow;
import cda.bibliotheque.model.Client;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;

public class EditBorrowController {

    @FXML
    private ChoiceBox<Book> inputBook;

    @FXML
    private ChoiceBox<Client> inputClient;

    @FXML
    private DatePicker inputEndDate;

    @FXML
    private DatePicker inputStartDate;

    private ObjectProperty<Borrow> borrow = new SimpleObjectProperty<>();
    private final BookDAO bookDAO = new BookDAO();
    private final ClientDAO clientDAO = new ClientDAO();
    private final BorrowDAO borrowDAO = new BorrowDAO();

    @FXML
    public void initialize() {
        ObservableList<Book> books = FXCollections.observableArrayList();
        books.setAll(bookDAO.getAllBook());
        inputBook.getItems().setAll(books);
        ObservableList<Client> clients = FXCollections.observableArrayList();
        clients.setAll(clientDAO.getAllCLient());
        inputClient.getItems().setAll(clients);
        inputBook.setDisable(true);
        inputClient.setDisable(true);
        borrow.addListener((obs, oldValue, newValue) -> {
            inputBook.setValue(newValue.getBook());
            inputClient.setValue(newValue.getClient());
            inputStartDate.setValue(newValue.getStartDate());
            inputEndDate.setValue(newValue.getEndDate());
        });
    }

    @FXML
    void submit(ActionEvent event)  throws IOException {
        Borrow borrow = this.borrow.get();
        borrow.setStartDate(inputStartDate.getValue());
        borrow.setEndDate(inputEndDate.getValue());
        borrowDAO.updateBorrow(borrow);
        App.setRoot("borrows/borrows");
    }

    @FXML
    void switchToBorrows(ActionEvent event) throws IOException {
        App.setRoot("borrows/borrows");
    }

    public void setBorrow(Borrow borrow) {
        this.borrow.set(borrow);
    }

}
