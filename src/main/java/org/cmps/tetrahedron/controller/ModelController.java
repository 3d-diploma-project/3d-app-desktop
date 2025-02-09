package org.cmps.tetrahedron.controller;

import lombok.Getter;
import lombok.Setter;
import org.cmps.tetrahedron.model.Model;
import org.cmps.tetrahedron.model.Stress;
import org.cmps.tetrahedron.utils.DataReader;
import org.cmps.tetrahedron.utils.LegendUtils;
import org.joml.Vector3f;

import java.io.File;
import java.util.*;

import static org.cmps.tetrahedron.utils.LegendUtils.COLORS;

@Getter
public class ModelController {

    @Getter
    private static ModelController instance = new ModelController();

    private Model model;
    @Setter
    private boolean modelReady = false;
    private Stress stress;
    @Setter
    private boolean stressDataLoaded = false;

    private ModelController() {
        model = Model.builder()
                     .vertices(new HashMap<>())
                     .faces(new ArrayList<>())
                     .build();
    }

    public void initModelData(File nodes, File indices) {
        Map<Integer, float[]> vertices = DataReader.readVertices(nodes);

        model = Model.builder()
                     .vertices(vertices)
                     .faces(DataReader.readIndexesAndConvertToFaces(indices, vertices))
                     .build();
        modelReady = true;

        centerModel(model);
    }

    public List<float[][]> getFaces() {
        return model.getFaces();
    }

    public Map<Integer, float[]> getVertices() {
        if (model == null) {
            throw new RuntimeException("Model is not initialized");
        }
        return model.getVertices();
    }

    public void initStress(File stressData) {
        stress = DataReader.readStress(stressData);

        TreeMap<Float, Integer> legend = LegendUtils.buildLegend(stress.getMinStress(), stress.getMaxStress());
        stress.setColors(stress.getStress()
                               .stream()
                               .map(value -> COLORS.get(legend.get(legend.floorKey(value))))
                               .toList());

        stressDataLoaded = true;
    }

    public void centerModel(Model model) {
        Vector3f center = model.getModelCenter();

        Map<Integer, float[]> vertices = ModelController.getInstance().getVertices();
        for (Map.Entry<Integer, float[]> entry : vertices.entrySet()) {
            float[] vertex = entry.getValue();
            vertex[0] -= center.x;
            vertex[1] -= center.y;
            vertex[2] -= center.z;
        }
    }
}
