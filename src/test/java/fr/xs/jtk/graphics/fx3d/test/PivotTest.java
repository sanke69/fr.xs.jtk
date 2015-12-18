
package fr.xs.jtk.graphics.fx3d.test;

import com.sun.javafx.geom.Vec3d;

import fr.xs.jtk.graphics.fx3d.base.TestApplication3DPattern;
import fr.xs.jtk.graphics.fx3d.utils.PhongPhactory;
import fr.xs.jtk.math.fx.Pivot;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Sphere;
import javafx.util.Duration;

public class PivotTest extends TestApplication3DPattern {

	private static final double AXIS_LENGTH = 250.0;
	private static final Vec3d up = new Vec3d(0, -1, 0);
	
	private double a = 0; // everything rotates using multiples of this counter

	private final Group root = new Group();
	private final PerspectiveCamera cam = new PerspectiveCamera(true);

	private Pivot axis = new Pivot();
	private Pivot camera = new Pivot();
	private Pivot earth = new Pivot();
	private Pivot light = new Pivot();
	private Pivot redLight = new Pivot();
	private Pivot box = new Pivot();

	public static void main(String[] args) {
		launch(args);
	}

	private void buildAxes(Pivot p) {
		final Box xAxis = new Box(AXIS_LENGTH, 4, 4);
		final Box yAxis = new Box(4, AXIS_LENGTH, 4);
		final Box zAxis = new Box(4, 4, AXIS_LENGTH);

		xAxis.setMaterial(PhongPhactory.fromColour(Color.RED));
		yAxis.setMaterial(PhongPhactory.fromColour(Color.GREEN));
		zAxis.setMaterial(PhongPhactory.fromColour(Color.BLUE));

		p.getChildren().addAll(xAxis, yAxis, zAxis);
		p.setVisible(true);
		root.getChildren().addAll(p);
	}

	@Override
	public Group testObject3D() {
		root.getChildren().add(new AmbientLight(Color.color(0.1, 0.1, 0.1)));

		buildAxes(axis);
		Pivot p1 = new Pivot(-AXIS_LENGTH / 2, 0, -AXIS_LENGTH / 2);
		buildAxes(p1);
		Pivot p2 = new Pivot(AXIS_LENGTH / 2, 0, -AXIS_LENGTH / 2);
		buildAxes(p2);
		Pivot p3 = new Pivot(-AXIS_LENGTH / 2, 0, AXIS_LENGTH / 2);
		buildAxes(p3);
		Pivot p4 = new Pivot(AXIS_LENGTH / 2, 0, AXIS_LENGTH / 2);
		buildAxes(p4);

		Sphere s = new Sphere(100);
		s.setMaterial(PhongPhactory.fromImage("data/ball.jpg"));
		earth.getChildren().add(s);
		root.getChildren().add(earth);

		camera.getChildren().add(cam);

		PointLight l = new PointLight(Color.WHITE);
		light.getChildren().add(l);
		light.setPosition(300, -200, -300);
		buildAxes(light); // adding the axis will add light pivot to scene

		PointLight rl = new PointLight(Color.DARKRED);
		redLight.getChildren().add(rl);
		redLight.setPosition(-300, -200, 300);
		buildAxes(redLight);

		Box b = new Box(1200, 600, 1200);
		box.getChildren().add(b);
		box.setPosition(0, 0, 0);
		b.setCullFace(CullFace.FRONT);
		b.setMaterial(PhongPhactory.fromImage("/data/crate.jpg"));
		root.getChildren().add(box);

		Timeline t = new Timeline(new KeyFrame(Duration.millis(20), e -> {
			a = a + 0.02;

			earth.setEularRotation(a, -a * 4, -a);

			axis.setPosition(Math.cos(a) * (AXIS_LENGTH / 2), 0, Math.sin(a) * (AXIS_LENGTH / 2));
			camera.setPosition(Math.cos(-a / 4) * (AXIS_LENGTH * 4), -AXIS_LENGTH, Math.sin(-a / 4) * (AXIS_LENGTH * 4));
			camera.lookAt(axis.getPosition(), up);

		}));
		t.setCycleCount(Timeline.INDEFINITE);
		t.play();

		return root;
	}
}
