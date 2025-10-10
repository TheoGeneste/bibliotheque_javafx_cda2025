package cda.bibliotheque.controller.Client;

import java.io.IOException;

import cda.bibliotheque.App;
import cda.bibliotheque.dao.ClientDAO;
import cda.bibliotheque.model.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CreateClientController {

    @FXML
    private TextField inputEmail;

    @FXML
    private TextField inputFirstname;

    @FXML
    private TextField inputLastname;

    private final ClientDAO clientDAO = new ClientDAO();

    @FXML
    void submit(ActionEvent event) throws IOException {
        Client client = new Client(inputFirstname.getText(),inputLastname.getText(), inputEmail.getText() );
        clientDAO.addClient(client);
        App.setRoot("clients/clients");
        
    }

    @FXML
    void switchToClients(ActionEvent event) throws IOException {
        App.setRoot("clients/clients");
    }

}
