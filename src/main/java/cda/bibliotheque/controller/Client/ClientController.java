package cda.bibliotheque.controller.Client;

import java.io.IOException;
import java.util.List;

import cda.bibliotheque.App;
import cda.bibliotheque.dao.ClientDAO;
import cda.bibliotheque.model.Client;
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

public class ClientController {

    @FXML
    private TableView<Client> clientsTable;

    @FXML
    private TableColumn<Client, Void> colActions;

    @FXML
    private TableColumn<Client, String> colEmail;

    @FXML
    private TableColumn<Client, String> colFirstname;

    @FXML
    private TableColumn<Client, String> colLastname;

    private final ClientDAO clientDAO = new ClientDAO();
    private ObservableList<Client> clients = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colFirstname.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFirstname()));
        colLastname.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getLastname()));
        colEmail.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getEmail()));
        colActions.setCellFactory(cell -> new TableCell<>() {
            Button editButton = new Button("Modifier");
            Button deleteButton = new Button("Supprimer");
            VBox vbox = new VBox(10, editButton, deleteButton);

            {
                editButton.setOnAction(event -> {
                    int index = getIndex();
                    Client clientToEdit = clientsTable.getItems().get(index);
                    try {
                        FXMLLoader loader = new FXMLLoader(App.class.getResource("clients/edit-client.fxml"));
                        Parent parent = loader.load();

                        EditClientController editClientController = loader.getController();
                        editClientController.setClient(clientToEdit);

                        App.getScene().setRoot(parent);
                    } catch (IOException e) {
                        System.out.println(e.getCause());
                        System.err.println("Erreur lors de la création du bouton edit ->" + e);
                    }
                });

                deleteButton.setOnAction(event -> {
                    int index = getIndex();
                    Client client = clientsTable.getItems().get(index);
                    clientDAO.deleteClient(client);
                    loadClients();
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
        loadClients();
    }

    @FXML
    void switchToCreate(ActionEvent event) throws IOException {
        App.setRoot("clients/create-client");
    }

    @FXML
    void switchToMain(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }

    public void loadClients(){
        List<Client> clients = clientDAO.getAllCLient();
        this.clients.setAll(clients);
        clientsTable.setItems(this.clients);
    }
}
