package fr.xs.jtk.graphics.fx3d.test;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import fr.xs.jtk.graphics.fx3d.base.TestApplication3DPattern;
import fr.xs.jtk.graphics.fx3d.shapes.composites.ScatterPlot;
import fr.xs.jtk.graphics.fx3d.shapes.primitives.IcosahedronMesh;
import fr.xs.jtk.math.type.geom.Point3D;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.CullFace;

public class ScatterPlotTest extends TestApplication3DPattern {

	private Function<Point3D, Number> dens = p -> p.x * p.y * p.z;
	long lastEffect;

	@Override
	public void configure() {
//		getCamera().setTranslateZ(-30);
	};

	@Override
	public Group testObject3D() {
		Group group = new Group();

		ScatterPlot scatterPlot = new ScatterPlot(1000, 1, true);

		ArrayList<Double> dataX = new ArrayList<>();
		ArrayList<Double> dataY = new ArrayList<>();
		ArrayList<Double> dataZ = new ArrayList<>();
		for(int i = -250; i < 250; i++) {
			dataX.add(new Double(i));
			dataY.add(new Double(Math.sin(i) * 50) + i);
			dataZ.add(new Double(Math.cos(i) * 50) + i);
		}

		scatterPlot.setXYZData(dataX, dataY, dataZ);

		group.getChildren().addAll(scatterPlot);

		return group;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
