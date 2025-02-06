package org.cmps.tetrahedron.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.cmps.tetrahedron.controller.ModelController;
import org.cmps.tetrahedron.model.Stress;
import org.cmps.tetrahedron.utils.FontUtils;
import org.cmps.tetrahedron.utils.LegendUtils;

import java.util.List;

import java.util.*;
import java.util.Map;
import java.util.TreeMap;

public class StressView extends VBox {

    public StressView() {

        Map<Integer, float[]> colors = LegendUtils.COLORS;
        List<String> ranges = generateRange();

        HBox allColorBoxes = new HBox();
        allColorBoxes.setAlignment(Pos.CENTER);
        allColorBoxes.setSpacing(2);

        for (Map.Entry<Integer, float[]> entry : colors.entrySet()) {
            float[] values = entry.getValue();
            VBox colorBox = new VBox();
            colorBox.setMinSize(100, 20);
            colorBox.setStyle("-fx-background-color: " + toHex(values[0], values[1], values[2]) + ";");

            allColorBoxes.getChildren().add(colorBox);
        }
        getChildren().add(allColorBoxes);

        HBox allLabelBoxes = new HBox();
        allLabelBoxes.setAlignment(Pos.CENTER);
        allLabelBoxes.setSpacing(40);

        for (String border: ranges) {
            Label rangeValue = new Label(border);
            rangeValue.setFont(FontUtils.getGeolocicaFont(16));
            allLabelBoxes.getChildren().add(rangeValue);
        }
        getChildren().add(allLabelBoxes);

    }

    private List<String> generateRange() {
        Stress stressModel = ModelController.getInstance().getStress();
        TreeMap<Float, Integer> legend = LegendUtils.buildLegend(stressModel.getMinStress(), stressModel.getMaxStress());

        List<Float> stressChunks = new ArrayList<>(legend.keySet());
        stressChunks.add(stressModel.getMaxStress());

        List<String> expNotationNumbers = stressChunks.stream()
                .map(num -> String.format("%.2e", num))
                .toList();

        return expNotationNumbers;
    }

    private String toHex(float red, float green, float blue) {
        return String.format("#%02X%02X%02X", (int) (red * 255), (int) (green * 255), (int) (blue * 255));
    }
}
