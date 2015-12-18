package fr.xs.jtk.graphics.fx3d.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import fr.xs.jtk.graphics.fx3d.base.TestApplication3DPattern;
import fr.xs.jtk.graphics.fx3d.shapes.primitives.BezierMesh;
import fr.xs.jtk.graphics.fx3d.shapes.primitives.PrismMesh;
import fr.xs.jtk.graphics.fx3d.shapes.primitives.helper.InterpolateBezier;
import fr.xs.jtk.graphics.fx3d.tools.CubeViewer;
import fr.xs.jtk.math.type.geom.Point3D;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class CubeViewerTest extends TestApplication3DPattern {

	@Override
	public void configure() {
		getScene().setFill(Color.WHITESMOKE);
		getCamera().setTranslateZ(-30);
	};

	@Override
	public Group testObject3D() {
		CubeViewer cubeViewer = new CubeViewer(1000, 100, true);

		// Create and add some data to the Cube
		ArrayList<Double> dataX = new ArrayList<>();
		ArrayList<Double> dataY = new ArrayList<>();
		ArrayList<Double> dataZ = new ArrayList<>();
		for(int i = -250; i < 250; i++) {
			dataX.add(new Double(i));
			dataY.add(new Double(Math.sin(i) * 50) + i);
			dataZ.add(new Double(Math.cos(i) * 50) + i);
		}

		// The cube viewer will add data nodes as cubes here but you can add
		// your own scatter plot to the same space as the cube if you want.
		cubeViewer.setxAxisData(dataX);
		cubeViewer.setyAxisData(dataY);
		cubeViewer.setzAxisData(dataZ);

		return cubeViewer;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
