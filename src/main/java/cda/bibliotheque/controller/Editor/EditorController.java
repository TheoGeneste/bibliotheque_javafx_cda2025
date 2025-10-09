package cda.bibliotheque.controller.Editor;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import cda.bibliotheque.App;
import cda.bibliotheque.controller.Author.EditAuthorController;
import cda.bibliotheque.dao.EditorDAO;
import cda.bibliotheque.model.Author;
import cda.bibliotheque.model.Editor;
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
import javafx.scene.layout.HBox;

public class EditorController {

    @FXML
    private TableView<Editor> editorsTable;

    @FXML
    private TableColumn<Editor, Void> colActions;

    @FXML
    private TableColumn<Editor, LocalDate> colCreatedAt;

    @FXML
    private TableColumn<Editor, String> colLabel;

    private final EditorDAO editorDAO = new EditorDAO();
    private ObservableList<Editor> editors = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colLabel.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getLabel()));
        colCreatedAt.setCellValueFactory(cell -> new SimpleObjectProperty<LocalDate>(cell.getValue().getCreatedAt()));
        colActions.setCellFactory(cell -> new TableCell<>() {
            Button editButton = new Button("Modifier");
            Button deleteButton = new Button("Supprimer");
            HBox hBox = new HBox(10, editButton, deleteButton);

            {
                editButton.setOnAction(event -> {
                    int index = getIndex();
                    Editor editorToEdit = editorsTable.getItems().get(index);
                    try {
                        FXMLLoader loader = new FXMLLoader(App.class.getResource("editors/edit-editor.fxml"));
                        Parent parent = loader.load();

                        EditEditorController editEditorController = loader.getController();
                        editEditorController.setEditor(editorToEdit);

                        App.getScene().setRoot(parent);
                    } catch (IOException e) {
                        System.out.println(e.getCause());
                        System.err.println("Erreur lors de la création du bouton edit ->" + e);
                    }
                });

                deleteButton.setOnAction(event -> {
                    int index = getIndex();
                    Editor editorToDelete = editorsTable.getItems().get(index);
                    editorDAO.deleteEditor(editorToDelete);
                    loadEditors();
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
        loadEditors();
    }

    @FXML
    void switchToCreate(ActionEvent event) throws IOException {
        App.setRoot("editors/create-editor");
    }

    @FXML
    void switchToMain(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }

    private void loadEditors() {
        List<Editor> editors = editorDAO.getAllEditor();
        this.editors.setAll(editors);
        this.editorsTable.setItems(this.editors);
    }

}
