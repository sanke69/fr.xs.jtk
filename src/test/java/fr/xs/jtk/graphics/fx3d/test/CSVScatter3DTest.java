package fr.xs.jtk.graphics.fx3d.test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import fr.xs.jtk.graphics.fx3d.base.TestApplication3DPattern;
import fr.xs.jtk.graphics.fx3d.shapes.primitives.ScatterMesh;
import fr.xs.jtk.math.type.geom.Point3D;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public class CSVScatter3DTest extends TestApplication3DPattern {

	private long lastEffect;

	@Override
	public void configure() {
		getScene().setFill(Color.WHITESMOKE);
		getCamera().setTranslateZ(-30);
	};

	@Override
	public Group testObject3D() {
		Group group = new Group();

		List<Point3D> data = new ArrayList<>();
		Path out = getCSVFile(0);
		if(out != null) {
			try {
				Files.lines(out).map(s -> s.split(";")).forEach(s -> data.add(new Point3D(Float.parseFloat(s[0]), Float.parseFloat(s[1]), Float.parseFloat(s[2]), Float.parseFloat(s[3]))));
			} catch(NumberFormatException | IOException e) {
				e.printStackTrace();
			}
		}

		ScatterMesh scatter = new ScatterMesh(data, true, 1, 0);
		// DENSITY
		// texture is given by p.f value, don't change this!
		scatter.setTextureModeVertices3D(1530, p -> p.f);

		group.getChildren().add(scatter);

		List<List<Number>> fullData = new ArrayList<>();
		final boolean constantVertices = false;
		lastEffect = System.nanoTime();
		AtomicInteger count = new AtomicInteger(0);

		if(constantVertices) {
			// if possible we can cache all the data
			Stream.of(0, 1, 2, 3, 4, 3, 2, 1).forEach(i -> {
				Path out2 = getCSVFile(i);
				if(out2 != null) {
					try {
						List<Number> data2 = new ArrayList<>();
						Files.lines(out2).map(s -> s.split(";")).forEach(s -> {
							float f = Float.parseFloat(s[3]);
							// 4 vertices tetrahedra
							data2.add(f);
							data2.add(f);
							data2.add(f);
							data2.add(f);
						});
						fullData.add(data2);
					} catch(IOException ex) {
					}
				}
			});
		}

		AnimationTimer timerEffect = new AnimationTimer() {

			@Override
			public void handle(long now) {
				if(now > lastEffect + 50_000_000l) {
					try {
						// long t=System.currentTimeMillis();
						if(constantVertices && fullData != null) {
							// Vertices coordinates are always the same: mesh is
							// tha same, we only
							// need to update F on each element
							scatter.setFunctionData(fullData.get(count.get() % 8));
							// System.out.println("t
							// "+(System.currentTimeMillis()-t));
						} else {
							// vertices coordinates may change in time, we need
							// to create them all over again reading the files:
							Path out2 = getCSVFile((int) (Stream.of(0, 1, 2, 3, 4, 3, 2, 1).toArray()[count.get() % 8]));
							if(out2 != null) {
								List<Point3D> data2 = new ArrayList<>();
								Files.lines(out2).map(s -> s.split(";")).forEach(s -> data2.add(new Point3D(Float.parseFloat(s[0]), Float.parseFloat(s[1]), Float.parseFloat(s[2]), Float.parseFloat(s[3]))));
								scatter.setScatterData(data2);
								scatter.setTextureModeVertices1D(1530, p -> p);
							}
							// System.out.println("t
							// "+(System.currentTimeMillis()-t));
						}
					} catch(IOException ex) {
					}

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

	private Path getCSVFile(int i) {
		try {
			return Paths.get(getClass().getResource("/res/csv/input_" + i + ".txt").toURI());
		} catch(URISyntaxException ex) {
		}
		return null;
	}

}
