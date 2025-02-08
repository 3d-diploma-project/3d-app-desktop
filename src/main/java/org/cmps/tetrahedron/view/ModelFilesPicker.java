package org.cmps.tetrahedron.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.Setter;
import org.cmps.tetrahedron.Tetrahedron;
import org.cmps.tetrahedron.controller.ModelController;
import org.cmps.tetrahedron.utils.DataReader;
import org.cmps.tetrahedron.utils.ResourceReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Objects;

public class ModelFilesPicker {

    private final ModelController modelController = ModelController.getInstance();

    @FXML
    private FilePicker nodesController;
    @FXML
    private FilePicker indicesController;

    @Setter
    private Dialog<Scene> dialog;

    public static void openDialogWindow() {
        Dialog<Scene> dialog = new Dialog<>();

        URL fxmlUrl = ModelFilesPicker.class.getClassLoader().getResource("view/ModelFilesPicker.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlUrl);

        try {
            dialog.setDialogPane(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ModelFilesPicker modelFilesPicker = loader.getController();
        modelFilesPicker.setDialog(dialog);

        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.setOnCloseRequest(modelFilesPicker::onClose);

        ImageView logo = ResourceReader.imageReader("/logo.png");
        stage.getIcons().add(logo.getImage());

        dialog.setTitle("Select model");
        dialog.show();
    }

    public void onClick() {
        boolean nodesValidation = validateFileExistence(nodesController) && validateVerticesFile(nodesController.getFile());
        boolean indicesValidation = validateFileExistence(indicesController);

        if (!nodesValidation || !indicesValidation) {
            return;
        }

        modelController.initModelData(nodesController.getFile(), indicesController.getFile());

        if (dialog != null) {
            dialog.close();
        }
    }

    private void onClose(WindowEvent dialogEvent) {
        dialogEvent.consume();
        onClick();
    }

    private boolean validateFileExistence(FilePicker filePicker) {
        File file = filePicker.getFile();

        if (file == null) {
            filePicker.showNotSelectedFileError();
            return false;
        }

        if (!file.getName().toLowerCase().endsWith(".txt")) {
            showError("Invalid file format. Only .txt files are allowed: " + file.getName());
            return false;
        }

        return true;
    }

    private boolean validateVerticesFile(File verticesFile) {
        try {
            Map<Integer, float[]> vertices = DataReader.readVertices(verticesFile);
            if (vertices.isEmpty()) {
                showError("Vertices file is empty.");
                return false;
            }
        } catch (Exception e) {
            showError("Error reading vertices file: " + e.getMessage());
            return false;
        }

        return true;
    }

    private void showError(String message) {
        System.err.println(message);
    }
}
