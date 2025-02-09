package org.cmps.tetrahedron.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ErrorDialog {

    @FXML
    private Label errorTitle;
    @FXML
    private Label errorMessage;
    @FXML
    private Button closeButton;
    @FXML
    private DialogPane dialogPane;

    private Dialog<Void> dialog;

    public ErrorDialog(String title, String message) {
        try {
            URL fxmlPath = getClass().getClassLoader().getResource("view/ErrorDialog.fxml");
            if (fxmlPath == null) {
                throw new IOException("FXML file is not found: view/ErrorDialog.fxml");
            }

            FXMLLoader loader = new FXMLLoader(fxmlPath);
            loader.setController(this);
            dialogPane = loader.load();
            dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);

            errorTitle.setText(title);
            errorMessage.setText(message);

            closeButton.setOnAction(event -> closeDialog());

        } catch (IOException e) {
            throw new RuntimeException("Open DialogWindow Error: " + e.getMessage(), e);
        }
    }

    @FXML
    private void closeDialog() {
        Stage stage = (Stage) dialogPane.getScene().getWindow();
        stage.close();
    }

    public void show() {
        dialog.showAndWait();
    }
}
