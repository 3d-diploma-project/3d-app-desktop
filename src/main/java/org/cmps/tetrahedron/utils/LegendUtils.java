package org.cmps.tetrahedron.utils;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class LegendUtils {

    private static final int COLOR_ARRAY_SIZE = 7;

    public static final Map<Integer, float[]> COLORS = getColors();

    public static TreeMap<Float, Integer> buildLegend(float min, float max) {
        TreeMap<Float, Integer> stressColorMap = new TreeMap<>();
        float stressDiapason = max - min;
        float stressChunk = stressDiapason / COLOR_ARRAY_SIZE;

        for (int i = 0; i < COLOR_ARRAY_SIZE; i++) {
            float stress = min + (stressChunk * i);
            stressColorMap.put(stress, COLOR_ARRAY_SIZE - i - 1);
        }

        return stressColorMap;
    }

    private static Map<Integer, float[]> getColors() {
        double jump = 0.66 / (COLOR_ARRAY_SIZE * 1.0);
        Map<Integer, float[]> colors = new HashMap<>();

        for (int i = 0; i < COLOR_ARRAY_SIZE; i++) {
            Color color = Color.getHSBColor((float) (jump * i), 0.7f, 0.8f);
            colors.put(i, new float[]{color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f});
        }

        return colors;
    }
}
