package org.cmps.tetrahedron.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ResourceReader {
    public static ImageView imageReader(String filePath) {
        InputStream input = ResourceReader.class.getResourceAsStream(filePath);
        if (input == null) {
            try {
                throw new FileNotFoundException("Image file was not found");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        Image image = new Image(input);
        return new ImageView(image);
    }

    public static Node readComponent(String path) {
        FXMLLoader loader = new FXMLLoader(ResourceReader.class.getResource(path));
        try {
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
