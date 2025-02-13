package org.cmps.tetrahedron.model;

import lombok.*;
import org.cmps.tetrahedron.utils.DataReader;
import org.joml.Vector3f;

import java.io.File;
import java.util.*;

@Getter
public class Model {

    private final Map<Integer, float[]> vertices;
    private final List<float[][]> faces;
    private final Vector3f center;

    @Builder
    public Model(Map<Integer, float[]> vertices, List<float[][]> faces) {
        this.vertices = vertices;
        this.faces = faces;
        this.center = calculateModelCenter();
    }

    private Vector3f calculateModelCenter() {
        if (vertices == null || vertices.isEmpty()) {
            System.out.println("Model has no vertices");
            return null;
        }

        float sumX = 0, sumY = 0, sumZ = 0;
        int vertexCount = vertices.size();

        for (float[] vertex : vertices.values()) {
            sumX += vertex[0];
            sumY += vertex[1];
            sumZ += vertex[2];
        }

        System.out.println("Model Center: " + sumX / vertexCount + ", " + sumY / vertexCount + ", " + sumZ / vertexCount);
        return new Vector3f(sumX / vertexCount, sumY / vertexCount, sumZ / vertexCount);
    }

}
