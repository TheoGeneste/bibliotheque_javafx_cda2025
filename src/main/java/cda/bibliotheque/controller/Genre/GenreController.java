package cda.bibliotheque.controller.Genre;

import java.io.IOException;
import java.util.List;

import cda.bibliotheque.App;
import cda.bibliotheque.dao.GenreDAO;
import cda.bibliotheque.model.Genre;
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

public class GenreController {

    @FXML
    private TableColumn<Genre, Void> colActions;

    @FXML
    private TableColumn<Genre, String> colLabel;

    @FXML
    private TableView<Genre> genresTable;

    private ObservableList<Genre> genres = FXCollections.observableArrayList();
    private final GenreDAO genreDAO = new GenreDAO();

    @FXML
    public void initialize() {
        colLabel.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getLabel()));
        colActions.setCellFactory(cell -> new TableCell<>() {
            Button editButton = new Button("Modifier");
            Button deleteButton = new Button("Supprimer");
            VBox vbox = new VBox(10, editButton, deleteButton);

            {
                editButton.setOnAction(event -> {
                    int index = getIndex();
                    Genre genreToEdit = genresTable.getItems().get(index);
                    try {
                        FXMLLoader loader = new FXMLLoader(App.class.getResource("genres/edit-genre.fxml"));
                        Parent parent = loader.load();

                        EditGenreController editGenreController = loader.getController();
                        editGenreController.setGenre(genreToEdit);

                        App.getScene().setRoot(parent);
                    } catch (IOException e) {
                        System.out.println(e.getCause());
                        System.err.println("Erreur lors de la création du bouton edit ->" + e);
                    }
                });

                deleteButton.setOnAction(event -> {
                    int index = getIndex();
                    Genre genre = genresTable.getItems().get(index);
                    genreDAO.deleteGenre(genre);
                    loadGenres();
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
        loadGenres();
    }

    @FXML
    void switchToCreate(ActionEvent event) throws IOException {
        App.setRoot("genres/create-genre");
    }

    @FXML
    void switchToMain(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }

    private void loadGenres() {
        List<Genre> genres = genreDAO.getAllGenre();
        this.genres.setAll(genres);
        this.genresTable.setItems(this.genres);
    }

}
