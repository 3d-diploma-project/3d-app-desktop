package org.cmps.tetrahedron.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.Setter;
import org.cmps.tetrahedron.controller.ModelController;
import org.cmps.tetrahedron.exception.ModelValidationException;
import org.cmps.tetrahedron.utils.ResourceReader;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ModelFilesPicker {

    private final ModelController modelController = ModelController.getInstance();
    private boolean errorDialogShown = false;

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
            new ErrorDialog("Помилка!", "Неможливо зчитати матрицю індексів та таблицю координат.\n\nПеревірте дані та спробуйте знову!").show();
            return;
        }

        try {
            modelController.initModelData(nodesController.getFile(), indicesController.getFile());
        } catch (ModelValidationException e) {
            new ErrorDialog("Помилка!", e.getMessage()).show();
            return;
        }

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
            // there should be another text here
            new ErrorDialog("Помилка!", "Неможливо зчитати матрицю індексів та таблицю координат.\n\nПеревірте дані та спробуйте знову!").show();
            errorDialogShown = true;
            return false;
        }

        return true;
    }

    private void showError(String message) {
        System.err.println(message);
    }
}
