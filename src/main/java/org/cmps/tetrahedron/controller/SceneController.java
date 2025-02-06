package org.cmps.tetrahedron.controller;

import javafx.scene.layout.*;
import org.cmps.tetrahedron.view.*;
import lombok.Getter;
import javafx.scene.Scene;
import org.cmps.tetrahedron.config.WindowProperties;

import java.util.Objects;

/**
 * Builds scene and adds to it all mouse event listeners.
 *
 * @author Vadim Startchak (VadimST04)
 * @since 1.0
 */
public class SceneController {

    @Getter
    private static final SceneController instance = new SceneController();
    private VBox root;
    private VBox stressView;

    private final Scene scene;

    private SceneController() {
        scene = buildScene();
    }

    public static Scene getScene() {
        return instance.scene;
    }

    private Scene buildScene() {
        root = new VBox();
        root.getStyleClass().add("model-view-page");
        Scene scene = new Scene(root, WindowProperties.getLogicalWidth(), WindowProperties.getLogicalHeight());
        scene.getStylesheets().add(Objects.requireNonNull(SceneController.class.getResource("/styles.css")).toExternalForm());

        HBox navbar = new Navbar();

        VBox instrumentSidebar = new InstrumentsSidebar();
        instrumentSidebar.getStyleClass().add("instrument-sidebar");

        VBox rightToolbar = new RightToolbar();
        AnchorPane anchorPane = new AnchorPane(instrumentSidebar, rightToolbar);
        anchorPane.getStyleClass().add("main");

        AnchorPane.setLeftAnchor(instrumentSidebar, 30d);
        AnchorPane.setTopAnchor(instrumentSidebar, 50d);
        AnchorPane.setRightAnchor(rightToolbar, 20d);
        AnchorPane.setTopAnchor(rightToolbar, 50d);
        root.getChildren().addAll(navbar, anchorPane);

        InfoPanel infoPanel = InfoPanel.getInstance();
        AnchorPane absolutePane = new AnchorPane(infoPanel);
        AnchorPane.setBottomAnchor(infoPanel, 80d);
        VBox.setVgrow(absolutePane, Priority.ALWAYS);

        root.getChildren().add(absolutePane);
        return scene;
    }

    public void addStressView() {
        if (stressView == null) {
            stressView = new StressView();
            root.getChildren().add(1, stressView);
        }
    }
}
