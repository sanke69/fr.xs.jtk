package fr.xs.jtk.graphics.fx3d.test;

import java.util.ArrayList;
import java.util.function.Function;

import fr.xs.jtk.graphics.fx3d.base.TestApplication3DPattern;
import fr.xs.jtk.graphics.fx3d.shapes.composites.PolyLine3D;
import fr.xs.jtk.math.type.geom.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public class PolyLine3DTest extends TestApplication3DPattern {

	private Function<Point3D, Number> dens = p -> p.x * p.y * p.z;
	long lastEffect;

	@Override
	public void configure() {
		getCamera().setTranslateZ(-1000);
	};

	@Override
	public Group testObject3D() {
		Group group = new Group();

		ArrayList<Point3D> points = new ArrayList<>();
		for(int i = -250; i < 250; i++) {
			points.add(new Point3D((float) i, (float) Math.sin(i) * 50 + i, (float) Math.cos(i) * 50 + i));
		}
		PolyLine3D polyLine3D = new PolyLine3D(points, 3, Color.STEELBLUE);

		group.getChildren().addAll(polyLine3D);

		return group;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
