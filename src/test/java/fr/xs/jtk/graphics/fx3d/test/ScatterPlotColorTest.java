package fr.xs.jtk.graphics.fx3d.test;

import java.util.ArrayList;
import java.util.List;

import fr.xs.jtk.graphics.fx3d.base.TestApplication3DPattern;
import fr.xs.jtk.graphics.fx3d.shapes.composites.ScatterPlot;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public class ScatterPlotColorTest extends TestApplication3DPattern {

	@Override
	public Group testObject3D() {
		ScatterPlot scatterPlot = new ScatterPlot(1000, 1, true);

		List<Double> dataX = new ArrayList<>();
		List<Double> dataY = new ArrayList<>();
		List<Double> dataZ = new ArrayList<>();
		List<Color> colors = new ArrayList<>();
		int k = 0;
		for(int i = -250; i < 250; i++) {
			dataX.add(new Double(i));
			dataY.add(Math.sin(i) * 50 + i);
			dataZ.add(Math.cos(i) * 50 + i);
			colors.add(new Color(Math.abs(i) / 250D, Math.abs(dataY.get(k)) / 300D, Math.abs(dataZ.get(k) / 300D), 0.25));
			k++;
		}

		scatterPlot.setXYZData(dataX, dataY, dataZ, colors);

		return scatterPlot;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
 