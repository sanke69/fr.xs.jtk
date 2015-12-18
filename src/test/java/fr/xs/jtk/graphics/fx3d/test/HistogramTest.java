package fr.xs.jtk.graphics.fx3d.test;

import javafx.scene.Group;
import javafx.scene.paint.Color;

import fr.xs.jtk.graphics.fx3d.base.TestApplication3DPattern;
import fr.xs.jtk.graphics.fx3d.shapes.composites.Histogram;

public class HistogramTest extends TestApplication3DPattern {

	@Override
	public Group testObject3D() {
		Histogram histogram = new Histogram(1000, 1, true);

		int size = 30;
		float[][] arrayY = new float[2 * size][2 * size];
		for(int i = -size; i < size; i++) {
			for(int j = -size; j < size; j++) {
				double xterm = (Math.cos(Math.PI * i / size) * Math.cos(Math.PI * i / size));
				double yterm = (Math.cos(Math.PI * j / size) * Math.cos(Math.PI * j / size));
				arrayY[i + size][j + size] = (float) (10 * ((xterm + yterm) * (xterm + yterm)));
			}
		}
		histogram.setHeightData(arrayY, 1, 4, Color.SKYBLUE, false, true);

		return histogram;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
