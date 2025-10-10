package cda.bibliotheque.controller;

import java.io.IOException;

import cda.bibliotheque.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    void switchToAuthors(ActionEvent event) throws IOException {
        App.setRoot("authors/authors");
    }

    @FXML
    void switchToBooks(ActionEvent event)throws IOException {
        App.setRoot("books/books");

    }

    @FXML
    void switchToClients(ActionEvent event)throws IOException {
        App.setRoot("clients/clients");

    }

    @FXML
    void switchToEditor(ActionEvent event) throws IOException{
        App.setRoot("editors/editors");

    }
    @FXML
    void switchToGenres(ActionEvent event) throws IOException{
        App.setRoot("genres/genres");

    }
    @FXML
    void switchToBorrows(ActionEvent event) throws IOException{
        App.setRoot("borrows/borrows");

    }

    

}
