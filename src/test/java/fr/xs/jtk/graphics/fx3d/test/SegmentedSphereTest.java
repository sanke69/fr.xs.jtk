package fr.xs.jtk.graphics.fx3d.test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;
import fr.xs.jtk.math.type.geom.Point3D;
import fr.xs.jtk.graphics.fx3d.base.TestApplication3DPattern;
import fr.xs.jtk.graphics.fx3d.shapes.primitives.SegmentedSphereMesh;
import fr.xs.jtk.graphics.fx3d.shapes.primitives.SegmentedTorusMesh;

public class SegmentedSphereTest extends TestApplication3DPattern {

	private Function<Point3D, Number> dens = p -> p.x * p.y * p.z;
	long lastEffect;

	@Override
	public void configure() {
		getCamera().setTranslateZ(-10);
	};

	@Override
	public Group testObject3D() {
		Group group = new Group();

		SegmentedSphereMesh sphere = new SegmentedSphereMesh(200, 0, 0, 100, new Point3D(0f, 0f, 0f));
		// sphere.setDrawMode(DrawMode.LINE);
		// sphere.setCullFace(CullFace.NONE);
		// NONE
		// sphere.setTextureModeNone(Color.FORESTGREEN);
		// IMAGE
		// sphere.setTextureModeImage(getClass().getResource("res/share-carousel_In.png").toExternalForm());
		// PATTERN
		// sphere.setTextureModePattern(20.0d);
		// DENSITY
		sphere.setTextureModeVertices3D(2, dens);
		// FACES
		// sphere.setTextureModeFaces(256*256);

		group.getChildren().addAll(sphere);

		lastEffect = System.nanoTime();
		AtomicInteger count = new AtomicInteger();
		AnimationTimer timerEffect = new AnimationTimer() {

			@Override
			public void handle(long now) {
				if(now > lastEffect + 100_000_000l) {
					dens = p -> p.x * Math.cos(count.get() % 100d * 2d * Math.PI / 50d) + p.y * Math.sin(count.get() % 100d * 2d * Math.PI / 50d);
					sphere.setDensity(dens);

					if(count.get() % 100 < 50) {
						sphere.setDrawMode(DrawMode.LINE);
					} else {
						sphere.setDrawMode(DrawMode.FILL);
					}
					sphere.setRadius(500+100*(count.get()%10));
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
