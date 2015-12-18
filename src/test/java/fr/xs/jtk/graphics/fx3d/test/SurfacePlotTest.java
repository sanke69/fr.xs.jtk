package fr.xs.jtk.graphics.fx3d.test;

import javafx.scene.Group;
import javafx.scene.paint.Color;

import fr.xs.jtk.graphics.fx3d.base.TestApplication3DPattern;
import fr.xs.jtk.graphics.fx3d.shapes.composites.SurfacePlot;

public class SurfacePlotTest extends TestApplication3DPattern {

	@Override
	public void configure() {
		getCamera().setTranslateZ(-10);
	};

	@Override
	public Group testObject3D() {
		Group group = new Group();

		int size = 10;
		float[][] arrayY = new float[2 * size][2 * size];
		for(int i = -size; i < size; i++) {
			for(int j = -size; j < size; j++) {
				double R = Math.sqrt((i * i) + (j * j)) + 0.00000000000000001;
				arrayY[i + size][j + size] = ((float) -(Math.sin(R) / R)) * 100;
			}
		}
		SurfacePlot surfacePlot = new SurfacePlot(arrayY, 10, Color.AQUA, false, false);

		group.getChildren().add(surfacePlot);

		return group;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
