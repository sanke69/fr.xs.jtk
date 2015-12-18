package fr.xs.jtk.graphics.fx3d.test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import fr.xs.jtk.graphics.fx3d.base.TestApplication3DPattern;
import fr.xs.jtk.graphics.fx3d.shapes.primitives.IcosahedronMesh;
import fr.xs.jtk.math.type.geom.Point3D;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.CullFace;

public class IcosahedronTest extends TestApplication3DPattern {

	private Function<Point3D, Number> dens = p -> p.x * p.y * p.z;
	long lastEffect;

	@Override
	public void configure() {
		getSubScene().setFill(Color.WHITESMOKE);
		getCamera().setTranslateZ(-30);
	};

	@Override
	public Group testObject3D() {
		Group group = new Group();

		IcosahedronMesh ico = new IcosahedronMesh(5, 1f);
		// ico.setDrawMode(DrawMode.LINE);
		ico.setCullFace(CullFace.NONE);
		// NONE
		// ico.setTextureModeNone(Color.ROYALBLUE);
		// IMAGE
		// ico.setTextureModeImage(getClass().getResource("res/0ZKMx.png").toExternalForm());
		// PATTERN
		// ico.setTextureModePattern(2d);
		// DENSITY
		ico.setTextureModeVertices3D(1530, dens);
		// FACES
		// ico.setTextureModeFaces(256);

		group.getChildren().addAll(ico);

		lastEffect = System.nanoTime();
		AtomicInteger count = new AtomicInteger();
		AnimationTimer timerEffect = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if(now > lastEffect + 100_000_000l) {
					double t = count.getAndIncrement() % 10;
					dens = p -> (double) (p.x + t) * (p.y + t) * (p.z + t);
					ico.setDensity(dens);
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
