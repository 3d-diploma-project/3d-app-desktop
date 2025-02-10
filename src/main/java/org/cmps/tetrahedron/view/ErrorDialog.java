package org.cmps.tetrahedron.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;

public class ErrorDialog {

    @FXML
    private Label errorTitle;
    @FXML
    private Label errorMessage;
    @FXML
    private Button closeButton;

    private Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;

    public ErrorDialog(String title, String message) {
        try {
            URL fxmlPath = getClass().getClassLoader().getResource("view/ErrorDialog.fxml");
            if (fxmlPath == null) {
                throw new IOException("FXML файл не найден: view/ErrorDialog.fxml");
            }

            FXMLLoader loader = new FXMLLoader(fxmlPath);
            loader.setController(this);
            Pane root = loader.load();

            stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            Scene scene = new Scene(root);
            scene.setFill(null);
            stage.setScene(scene);

            errorTitle.setText(title);
            errorMessage.setText(message);

            if (closeButton != null) {
                closeButton.setOnAction(event -> closeDialog());
            }

            root.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });

            root.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });

        } catch (IOException e) {
            throw new RuntimeException("Ошибка открытия окна ошибки: " + e.getMessage(), e);
        }
    }

    @FXML
    private void closeDialog() {
        if (stage != null) {
            stage.close();
        }
    }

    public void show() {
        if (stage != null) {
            stage.showAndWait();
        }
    }
}
