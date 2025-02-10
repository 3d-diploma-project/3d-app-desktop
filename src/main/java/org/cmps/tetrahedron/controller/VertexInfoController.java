package org.cmps.tetrahedron.controller;

import javafx.application.Platform;
import javafx.util.Pair;
import org.cmps.tetrahedron.utils.CoordinatesConvertor;
import org.cmps.tetrahedron.utils.Scaler;
import org.joml.Vector3f;

import java.util.*;
import java.util.function.Consumer;

/**
 * Checks if a vertex present on clicked coordinates and display vertex info.
 *
 * @author Mariia Borodin (HappyMary16)
 * @since 1.0
 */
public class VertexInfoController {

    private static final double MAX_ACCEPTABLE_DISTANCE_SQR = 0.002;

    private static final VertexInfoController instance = new VertexInfoController();

    private final ModelController modelController = ModelController.getInstance();
    private final CoordinatesConvertor coordinatesConvertor = CoordinatesConvertor.getInstance();
    private Consumer<String> displayInfo;

    private int x;
    private int y;
    private float depth;
    private boolean clicked;

    private String lastInfoToDisplay = "Click on a vertex";

    private VertexInfoController() {}

    public static VertexInfoController getInstance() {
        return instance;
    }

    public void setDisplayInfo(Consumer<String> displayInfo) {
        this.displayInfo = displayInfo;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClickCoords(int x, int y) {
        this.x = Scaler.scaleByX(x);
        this.y = Scaler.scaleByY(y);
        clicked = true;
    }

    public void setDepthAndUpdateInfo(float depth) {
        this.depth = depth;
        Vector3f worldCoord = coordinatesConvertor.getWorldCoordinates(x, y, depth);
        lastInfoToDisplay = buildNodeInfo(worldCoord);

        Platform.runLater(() -> displayInfo.accept(lastInfoToDisplay));
        clicked = false;
    }

    /**
     * TODO: Make this search more efficient.
     */
    private String buildNodeInfo(Vector3f worldCoord) {
        int closestNode = 0;
        double distanceToClosestNode = Double.MAX_VALUE;

        for (Map.Entry<Integer, float[]> entry : modelController.getVertices().entrySet()) {
            float[] vertex = entry.getValue();
            double distanceSqr = Math.pow(vertex[0] - worldCoord.x, 2)
                    + Math.pow(vertex[1] - worldCoord.y, 2)
                    + Math.pow(vertex[2] - worldCoord.z, 2);

            if (distanceSqr < distanceToClosestNode) {
                closestNode = entry.getKey();
                distanceToClosestNode = distanceSqr;
            }
        }

        float[] vertex = moveToOriginal(modelController.getVertices().get(closestNode));

        return "Closest node " + closestNode + "-> X: " + vertex[0]
                + ", Y: " + vertex[1] + ", Z: " + vertex[2];
    }

    private float[] moveToOriginal(float[] vertex) {
        Vector3f center = modelController.getModel().getCenter();
        return new float[]{
                vertex[0] + center.x,
                vertex[1] + center.y,
                vertex[2] + center.z
        };
    }
}
