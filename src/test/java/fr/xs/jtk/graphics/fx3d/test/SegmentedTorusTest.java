package fr.xs.jtk.graphics.fx3d.test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.shape.DrawMode;

import fr.xs.jtk.math.type.geom.Point3D;
import fr.xs.jtk.graphics.fx3d.base.TestApplication3DPattern;
import fr.xs.jtk.graphics.fx3d.shapes.primitives.SegmentedTorusMesh;

public class SegmentedTorusTest extends TestApplication3DPattern {

	private Function<Point3D, Number> dens = p -> p.x * p.y * p.z;
	long lastEffect;

	@Override
	public void configure() {
		getCamera().setTranslateZ(-10);
	};

	@Override
	public Group testObject3D() {
		Group group = new Group();

		SegmentedTorusMesh torus = new SegmentedTorusMesh(50, 42, 0, 500d, 300d);
		// PhongMaterial matTorus = new PhongMaterial(Color.FIREBRICK);
		// torus.setMaterial(matTorus);
		SegmentedTorusMesh banner = new SegmentedTorusMesh(50, 42, 14, 500d, 300d);
		// PhongMaterial matBanner = new PhongMaterial();
		// matBanner.setDiffuseMap(new
		// Image(getClass().getResource("res/Duke3DprogressionSmall.jpg").toExternalForm()));
		// banner.setMaterial(matBanner);
		// torus.setDrawMode(DrawMode.LINE);
		// NONE
		// torus.setTextureModeNone(Color.FORESTGREEN);
		// IMAGE
		// torus.setTextureModeImage(getClass().getResource("res/grid1.png").toExternalForm());
		banner.setTextureModeImage(getClass().getResource("/res/office.jpg").toExternalForm());
		// PATTERN
		// torus.setTextureModePattern(1.0d);
		// DENSITY
		torus.setTextureModeVertices3D(1530, dens);
		// FACES
		// torus.setTextureModeFaces(256*256);

		group.getChildren().addAll(torus, banner);

		lastEffect = System.nanoTime();
		AtomicInteger count = new AtomicInteger();
		AnimationTimer timerEffect = new AnimationTimer() {

			@Override
			public void handle(long now) {
				if(now > lastEffect + 100_000_000l) {
					dens = p -> p.x * Math.cos(count.get() % 100d * 2d * Math.PI / 50d) + p.y * Math.sin(count.get() % 100d * 2d * Math.PI / 50d);
					torus.setDensity(dens);

					if(count.get() % 100 < 50) {
						torus.setDrawMode(DrawMode.LINE);
					} else {
						torus.setDrawMode(DrawMode.FILL);
					}
					// spring.setLength(100+20*(count.get()%10));
					// torus.setColors((int)Math.pow(2,count.get()%16));
					// torus.setMajorRadius(500+100*(count.get()%10));
					// torus.setMinorRadius(150+10*(count.get()%10));
					// torus.setPatternScale(1d+(count.get()%10)*5d);
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
