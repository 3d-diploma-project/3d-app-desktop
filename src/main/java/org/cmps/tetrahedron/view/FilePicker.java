package org.cmps.tetrahedron.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import lombok.Getter;
import org.cmps.tetrahedron.controller.SceneController;
import org.cmps.tetrahedron.utils.FileChooserUtils;

import java.io.File;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

/**
 * TODO: add description.
 *
 * @author Mariia Borodin (HappyMary16)
 * @since 1.0
 */
@Getter
public class FilePicker {

    @FXML
    private ResourceBundle resources;
    @FXML
    private Label label;

    private File file;
    private static final String LAST_USED_DIRECTORY_KEY = "last_used_directory";
    private static final Preferences prefs = Preferences.userNodeForPackage(FilePicker.class);

    @FXML
    public void onClick() {
        FileChooser fileChooser = FileChooserUtils.createFileChooser();
        file = fileChooser.showOpenDialog(SceneController.getScene().getWindow());

        if (file != null) {
            FileChooserUtils.saveLastUsedDirectory(file);
            label.setText(resources.getString("file-type") + ": " + file.getName());
        }
    }

    public void showNotSelectedFileError() {
        label.setText(resources.getString("file-is-not-selected"));
    }
}
