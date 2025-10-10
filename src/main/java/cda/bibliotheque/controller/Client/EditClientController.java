package cda.bibliotheque.controller.Client;

import java.io.IOException;

import cda.bibliotheque.App;
import cda.bibliotheque.dao.ClientDAO;
import cda.bibliotheque.model.Client;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class EditClientController {

    @FXML
    private TextField inputEmail;

    @FXML
    private TextField inputFirstname;

    @FXML
    private TextField inputLastname;

    private final ClientDAO clientDAO = new ClientDAO();
    private ObjectProperty<Client> client = new SimpleObjectProperty<>();

    @FXML
    public void initialize() {
        this.client.addListener((obs, oldValue, newValue) -> {
            inputFirstname.setText(newValue.getFirstname());
            inputLastname.setText(newValue.getLastname());
            inputEmail.setText(newValue.getEmail());
        });
    }

    @FXML
    void submit(ActionEvent event) throws IOException {
        
        Client client = this.client.get();
        client.setFirstname(inputFirstname.getText());
        client.setLastname(inputLastname.getText());
        client.setEmail(inputEmail.getText());
        clientDAO.updateClient(client);
        App.setRoot("clients/clients");

    }

    @FXML
    void switchToClients(ActionEvent event) throws IOException {
        App.setRoot("clients/clients");
    }

    public void setClient(Client client){
        this.client.set(client);
    }

}
