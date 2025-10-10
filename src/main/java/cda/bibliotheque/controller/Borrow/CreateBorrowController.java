package cda.bibliotheque.controller.Borrow;

import java.io.IOException;

import cda.bibliotheque.App;
import cda.bibliotheque.dao.BookDAO;
import cda.bibliotheque.dao.BorrowDAO;
import cda.bibliotheque.dao.ClientDAO;
import cda.bibliotheque.model.Book;
import cda.bibliotheque.model.Borrow;
import cda.bibliotheque.model.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;

public class CreateBorrowController {

    @FXML
    private ChoiceBox<Book> inputBook;

    @FXML
    private ChoiceBox<Client> inputClient;

    @FXML
    private DatePicker inputEndDate;

    @FXML
    private DatePicker inputStartDate;

    private final BorrowDAO borrowDAO = new BorrowDAO();
    private final BookDAO bookDAO = new BookDAO();
    private final ClientDAO clientDAO = new ClientDAO();

    @FXML
    public void initialize(){
        ObservableList<Book> books = FXCollections.observableArrayList();
        books.setAll(bookDAO.getAllBook());
        inputBook.getItems().setAll(books);
        ObservableList<Client> clients = FXCollections.observableArrayList();
        clients.setAll(clientDAO.getAllCLient());
        inputClient.getItems().setAll(clients);
    }

    @FXML
    void submit(ActionEvent event) throws IOException {
        Borrow borrow = new Borrow(
                inputClient.getValue(),
                inputBook.getValue(),
                inputEndDate.getValue(),
                inputStartDate.getValue(),
                false
        );
        borrowDAO.addBorrow(borrow);
        App.setRoot("borrows/borrows");
    }

    @FXML
    void switchToBorrows(ActionEvent event) throws IOException {
        App.setRoot("borrows/borrows");
    }

}
