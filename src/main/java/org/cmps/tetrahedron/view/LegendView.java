package org.cmps.tetrahedron.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import lombok.Getter;
import org.cmps.tetrahedron.controller.ModelController;
import org.cmps.tetrahedron.controller.SceneController;
import org.cmps.tetrahedron.model.Stress;
import org.cmps.tetrahedron.utils.FontUtils;
import org.cmps.tetrahedron.utils.LegendUtils;

import java.util.List;

import java.util.*;
import java.util.Map;
import java.util.TreeMap;

public class LegendView extends HBox {

    @Getter
    private static final LegendView instance = new LegendView();
    private final VBox allColorBoxes = new VBox();
    private final VBox allLabelBoxes = new VBox();

    private final int colorBoxSizeX = 80;
    private final int colorBoxSizeY = 25;

    private LegendView() {
        getChildren().addAll(allColorBoxes, allLabelBoxes);
    }

    public void updateLegend() {
        Map<Integer, float[]> colors = LegendUtils.COLORS;
        List<String> ranges = generateRangeValues();

        allColorBoxes.getChildren().clear();
        allLabelBoxes.getChildren().clear();

        addColorBoxes(colors);
        addValuesRangeBoxes(ranges);
    }

    private void addColorBoxes(Map<Integer, float[]> colors) {
        allColorBoxes.setAlignment(Pos.CENTER);

        for (Map.Entry<Integer, float[]> entry : colors.entrySet()) {
            float[] values = entry.getValue();
            VBox colorBox = new VBox();

            colorBox.setMinSize(colorBoxSizeX, colorBoxSizeY);
            colorBox.setStyle("-fx-background-color: " + toHex(values[0], values[1], values[2]) + ";");
            if (entry.getKey() != colors.size() - 1) {
                colorBox.setBorder(new Border(new BorderStroke(
                        Color.BLACK,
                        BorderStrokeStyle.SOLID,
                        new CornerRadii(0),
                        new BorderWidths(0, 0, 2, 0)
                )));
            }

            allColorBoxes.getChildren().add(colorBox);
        }
    }

    private void addValuesRangeBoxes(List<String> ranges) {
        allLabelBoxes.setAlignment(Pos.CENTER);
        allLabelBoxes.setSpacing(5.5);

        for (String border: ranges) {
            VBox labelBox = new VBox();
            labelBox.setStyle("-fx-background-color: #FAFAFA; "
                    + "-fx-padding: 2; "
                    + "-fx-border-color: black; "
                    + "-fx-border-width: 1px;");

            Label rangeValue = new Label(border);
            rangeValue.setFont(FontUtils.getGeolocicaFont(10, FontWeight.BOLD));

            labelBox.getChildren().add(rangeValue);
            allLabelBoxes.getChildren().add(labelBox);

            labelBox.widthProperty().addListener((obs, oldVal, newVal) -> {
                labelBox.setTranslateX(-(colorBoxSizeX - (colorBoxSizeX - (double) (newVal)) / 2));
            });
        }
    }

    private List<String> generateRangeValues() {
        Stress stressModel = ModelController.getInstance().getStress();
        TreeMap<Float, Integer> legend = LegendUtils.buildLegend(stressModel.getMinStress(), stressModel.getMaxStress());

        List<Float> stressChunks = new ArrayList<>(legend.keySet());
        stressChunks.add(stressModel.getMaxStress());

        return stressChunks.stream()
                .sorted(Comparator.reverseOrder())
                .map(num -> String.format("%.2e", num))
                .toList();
    }

    private String toHex(float red, float green, float blue) {
        return String.format("#%02X%02X%02X", (int) (red * 255), (int) (green * 255), (int) (blue * 255));
    }
}
