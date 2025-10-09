package cda.bibliotheque.controller.Editor;

import java.io.IOException;

import cda.bibliotheque.App;
import cda.bibliotheque.dao.EditorDAO;
import cda.bibliotheque.model.Editor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class CreateEditorController {

    @FXML
    private DatePicker inputCreatedAt;

    @FXML
    private TextField inputLabel;

    private final EditorDAO editorDAO = new EditorDAO();

    @FXML
    void submit(ActionEvent event) throws IOException {
        Editor newEditor = new Editor(inputLabel.getText(), inputCreatedAt.getValue());
        editorDAO.addEditor(newEditor);
        App.setRoot("editors/editors");
    }

    @FXML
    void switchToBooks(ActionEvent event) throws IOException {
        App.setRoot("editors/editors");
    }

}
