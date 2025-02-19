package org.cmps.tetrahedron.view;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import org.cmps.tetrahedron.controller.FileChooserController;
import org.cmps.tetrahedron.controller.ModelController;
import org.cmps.tetrahedron.controller.SceneController;
import org.cmps.tetrahedron.model.Stress;

import javafx.scene.input.MouseEvent;
import java.io.File;

public class RightToolbar {

    @FXML
    private void selectStressFile(MouseEvent mouseEvent) {
        FileChooserController fileChooserController = FileChooserController.getInstance();
        FileChooser fileChooser = fileChooserController.createFileChooser();
        File file = fileChooser.showOpenDialog(SceneController.getScene().getWindow());

        if (file != null) {
            fileChooserController.saveLastUsedDirectory(file);
            ModelController.getInstance().initStress(file);
            Stress stressModel = ModelController.getInstance().getStress();
            LegendView.getInstance().updateLegend(stressModel.getMinStress(), stressModel.getMaxStress());
        }
    }

}
