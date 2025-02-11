package org.cmps.tetrahedron.utils;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class FontUtils {

    private static final String GEOLOGICA_FONT_PATH = "/fonts/Geologica.ttf";

    public static Font getGeolocicaFont(int size, FontWeight weight) {
        Font font = Font.loadFont(
                FontUtils.class.getResource(GEOLOGICA_FONT_PATH).toExternalForm(),
                size
        );

        return Font.font(font.getFamily(), weight, size);
    }

    public static Font getGeolocicaFont(int size) {
        return getGeolocicaFont(size, FontWeight.NORMAL);
    }

}
