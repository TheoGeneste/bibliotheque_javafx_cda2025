package cda.bibliotheque.controller.Editor;

import java.io.IOException;

import cda.bibliotheque.App;
import cda.bibliotheque.dao.EditorDAO;
import cda.bibliotheque.model.Editor;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class EditEditorController {

    private final ObjectProperty<Editor> editor = new SimpleObjectProperty<>();
    private final EditorDAO editorDAO = new EditorDAO();

    @FXML
    private DatePicker inputCreatedAt;

    @FXML
    private TextField inputLabel;

    @FXML
    public void initialize(){
        this.editor.addListener((obs, oldValue, newValue) -> {
            inputCreatedAt.setValue(newValue.getCreatedAt());
            inputLabel.setText(newValue.getLabel());
        });
    }

    @FXML
    void submit(ActionEvent event) throws IOException {
        Editor editorEdited = editor.get();
        editorEdited.setLabel(inputLabel.getText());
        editorEdited.setCreatedAt(inputCreatedAt.getValue());
        editorDAO.updateEditor(editorEdited);
        App.setRoot("editors/editors");
    }

    @FXML
    void switchToBooks(ActionEvent event) throws IOException {
        App.setRoot("editors/editors");
    }

    public void setEditor(Editor editor){
        this.editor.set(editor);
    }

}
