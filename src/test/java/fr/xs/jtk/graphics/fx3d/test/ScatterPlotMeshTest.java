package fr.xs.jtk.graphics.fx3d.test;

import java.util.ArrayList;

import fr.xs.jtk.graphics.fx3d.base.TestApplication3DPattern;
import fr.xs.jtk.graphics.fx3d.shapes.composites.ScatterPlotMesh;
import javafx.scene.Group;

public class ScatterPlotMeshTest extends TestApplication3DPattern {

	@Override
	public Group testObject3D() {
		ScatterPlotMesh scatterPlotMesh = new ScatterPlotMesh(1000, 1, true);

		ArrayList<Double> dataX = new ArrayList<>();
		ArrayList<Double> dataY = new ArrayList<>();
		ArrayList<Double> dataZ = new ArrayList<>();
		for(int i = -250; i < 250; i++) {
			dataX.add(new Double(i));
			dataY.add(new Double(Math.sin(i) * 50) + i);
			dataZ.add(new Double(Math.cos(i) * 50) + i);
		}

		scatterPlotMesh.setXYZData(dataX, dataY, dataZ);

		return scatterPlotMesh;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
