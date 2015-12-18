package fr.xs.jtk.graphics.fx3d.test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import fr.xs.jtk.graphics.fx3d.base.TestApplication3DPattern;
import fr.xs.jtk.graphics.fx3d.shapes.primitives.FrustumMesh;
import fr.xs.jtk.math.type.geom.Point3D;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class FrustumTest extends TestApplication3DPattern {

	private Function<Point3D, Number> dens = p -> p.x;
	private Function<Number, Number> func = t -> (double) t;
	private long lastEffect;

	@Override
	public void configure() {
		getScene().setFill(Color.WHITESMOKE);
		getCamera().setTranslateZ(-30);
	};

	@Override
	public Group testObject3D() {
//		scene.setFill(Color.WHITESMOKE);
		
		Rotate rotateY = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
		Group group = new Group();

		FrustumMesh cylinder = new FrustumMesh(1, 0.2, 4, 3);

		// cylinder.setDrawMode(DrawMode.LINE);
		// SECTION TYPE
		// cylinder.setSectionType(TriangleMeshHelper.SectionType.TRIANGLE);
		// NONE
		// cylinder.setTextureModeNone(Color.ROYALBLUE);
		// IMAGE
		// cylinder.setTextureModeImage(getClass().getResource("res/netCylinder.png").toExternalForm());
		cylinder.setTextureModeVertices1D(1530, t -> t);
		// cylinder.setColorPalette(ColorPalette.GREEN);
		// DENSITY
		// cylinder.setTextureModeVertices3D(1530,p->(double)p.y);
		// cylinder.setTextureModeVertices3D(1530,p->(double)cylinder.unTransform(p).magnitude());
		// FACES
		// cylinder.setTextureModeFaces(1530);

		cylinder.getTransforms().addAll(new Rotate(0, Rotate.X_AXIS), rotateY);
		group.getChildren().add(cylinder);

		boolean showKnots = true;
		if(showKnots) {
			Sphere s = new Sphere(cylinder.getMajorRadius() / 10d);
			Point3D k0 = cylinder.getAxisOrigin();
			s.getTransforms().add(new Translate(k0.x, k0.y, k0.z));
			s.setMaterial(new PhongMaterial(Color.GREENYELLOW));
			group.getChildren().add(s);
			s = new Sphere(cylinder.getMinorRadius() / 10d);
			Point3D k3 = cylinder.getAxisEnd();
			s.getTransforms().add(new Translate(k3.x, k3.y, k3.z));
			s.setMaterial(new PhongMaterial(Color.ROSYBROWN));
			group.getChildren().add(s);
		}

		lastEffect = System.nanoTime();
		AtomicInteger count = new AtomicInteger(0);
		AnimationTimer timerEffect = new AnimationTimer() {

			@Override
			public void handle(long now) {
				if(now > lastEffect + 100_000_000l) {
					func = t -> Math.pow(Math.sin(8d * Math.PI * (10d * (1 - t.doubleValue()) + count.get() % 11) / 20d), 6); // <=1/6d)?1d:0d;
					cylinder.setFunction(func);

					count.getAndIncrement();
					lastEffect = now;
				}
			}
		};
		timerEffect.start();

		return group;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
