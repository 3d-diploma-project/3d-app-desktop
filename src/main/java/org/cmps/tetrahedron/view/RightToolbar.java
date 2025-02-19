package org.cmps.tetrahedron.view;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import org.cmps.tetrahedron.controller.ModelController;
import org.cmps.tetrahedron.controller.SceneController;
import org.cmps.tetrahedron.model.Stress;

import javafx.scene.input.MouseEvent;
import java.io.File;

public class RightToolbar {

    @FXML
    private void selectStressFile(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(SceneController.getScene().getWindow());
        if (file != null) {
            ModelController.getInstance().initStress(file);
            Stress stressModel = ModelController.getInstance().getStress();
            LegendView.getInstance().updateLegend(stressModel.getMinStress(), stressModel.getMaxStress());
        }
    }

}
