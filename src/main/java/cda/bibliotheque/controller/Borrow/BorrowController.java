package cda.bibliotheque.controller.Borrow;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import cda.bibliotheque.App;
import cda.bibliotheque.dao.BorrowDAO;
import cda.bibliotheque.model.Borrow;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

public class BorrowController {

    @FXML
    private TableView<Borrow> borrowsTable;

    @FXML
    private TableColumn<Borrow, Void> colActions;

    @FXML
    private TableColumn<Borrow, String> colBook;

    @FXML
    private TableColumn<Borrow, String> colClient;

    @FXML
    private TableColumn<Borrow, LocalDate> colEndDate;

    @FXML
    private TableColumn<Borrow, Boolean> colIsDone;

    @FXML
    private TableColumn<Borrow, LocalDate> colStartDate;

    private final BorrowDAO borrowDAO = new BorrowDAO();
    private ObservableList<Borrow> borrows = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colClient.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getClient().toString()));
        colBook.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getBook().getTitle()));
        colEndDate.setCellValueFactory(cell -> new SimpleObjectProperty<LocalDate>(cell.getValue().getEndDate()));
        colStartDate.setCellValueFactory(cell -> new SimpleObjectProperty<LocalDate>(cell.getValue().getStartDate()));
        colIsDone.setCellFactory(cell -> new TableCell<>() {
            CheckBox checkBox = new CheckBox();

            {
                checkBox.selectedProperty().addListener((obs, oldValue, newValue) -> {
                    int index = getIndex();
                    Borrow borrowToEdit = borrowsTable.getItems().get(index);
                    borrowToEdit.setIsDone(newValue);
                    borrowDAO.updateBorrow(borrowToEdit);
                });
            }

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Borrow borrow = getTableRow().getItem();
                    if (borrow != null) {
                        checkBox.setSelected(borrow.getIsDone());
                    }
                    setGraphic(checkBox);
                };
            }
        ;
        });
        colActions.setCellFactory(cell -> new TableCell<>() {
            Button editButton = new Button("Modifier");
            Button deleteButton = new Button("Supprimer");
            HBox hBox = new HBox(10, editButton, deleteButton);

            {
                editButton.setOnAction(event -> {
                    int index = getIndex();
                    Borrow borrowToEdit = borrowsTable.getItems().get(index);
                    try {
                        FXMLLoader loader = new FXMLLoader(App.class.getResource("borrows/edit-borrow.fxml"));
                        Parent parent = loader.load();

                        EditBorrowController editBorrowController = loader.getController();
                        editBorrowController.setBorrow(borrowToEdit);

                        App.getScene().setRoot(parent);
                    } catch (IOException e) {
                        System.out.println(e.getCause());
                        System.err.println("Erreur lors de la création du bouton edit ->" + e);
                    }
                });

                deleteButton.setOnAction(event -> {
                    int index = getIndex();
                    Borrow borrow = borrowsTable.getItems().get(index);
                    borrowDAO.deleteBorrow(borrow);
                    loadBorrows();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(hBox);
                }
            }
        });
        loadBorrows();
    }

    @FXML
    void switchToCreate(ActionEvent event) throws IOException {
        App.setRoot("borrows/create-borrow");
    }

    @FXML
    void switchToMain(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }

    public void loadBorrows() {
        List<Borrow> borrows = borrowDAO.getAllBorrow();
        this.borrows.setAll(borrows);
        this.borrowsTable.setItems(this.borrows);
    }
}
