package fr.xs.jtk.graphics.fx3d.test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import fr.xs.jtk.graphics.fx3d.base.TestApplication3DPattern;
import fr.xs.jtk.graphics.fx3d.shapes.primitives.ScatterMesh;
import fr.xs.jtk.math.type.geom.Point3D;
import javafx.scene.Group;

public class Scatter3DTest extends TestApplication3DPattern {

	private Function<Point3D, Number> dens = p -> p.x * p.y * p.z;
	long lastEffect;

	@Override
	public void configure() {
		// getCamera().setTranslateZ(-30);
	};

	@Override
	public Group testObject3D() {
		Group group = new Group();

		List<Point3D> data = new ArrayList<>();
		IntStream.range(0, 100000).forEach(i -> data.add(new Point3D((float) (30 * Math.sin(50 * i)), (float) (Math.sin(i) * (100 + 30 * Math.cos(100 * i))), (float) (Math.cos(i) * (100 + 30 * Math.cos(200 * i))), i))); // <--
																																																							// f
		ScatterMesh scatter = new ScatterMesh(data, true, 1, 0);
		// NONE
		// scatter.setTextureModeNone(Color.ROYALBLUE);
		// scatter.setDrawMode(DrawMode.LINE);
		// IMAGE
		// scatter.setTextureModeImage(getClass().getResource("res/steel-background1.jpg").toExternalForm());
		// scatter.setTextureModeImage(getClass().getResource("res/share-carousel2.jpg").toExternalForm());
		// DENSITY
		scatter.setTextureModeVertices3D(1530, p -> p.magnitude());
		// scatter.setTextureModeVertices1D(1530,p->Math.sqrt(p.doubleValue()));
		// scatter.setTextureModeVertices3D(1530,p->Math.sin(p.y/50)*Math.cos(p.x/40)*p.z);
		// FACES
		// scatter.setTextureModeFaces(Palette.ColorPalette.HSB,1530);
		group.getChildren().add(scatter);

		return group;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
