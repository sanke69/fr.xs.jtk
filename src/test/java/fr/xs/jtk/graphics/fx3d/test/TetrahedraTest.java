package fr.xs.jtk.graphics.fx3d.test;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import fr.xs.jtk.graphics.fx3d.base.TestApplication3DPattern;
import fr.xs.jtk.graphics.fx3d.shapes.composites.ScatterPlot;
import fr.xs.jtk.graphics.fx3d.shapes.primitives.IcosahedronMesh;
import fr.xs.jtk.graphics.fx3d.shapes.primitives.TetrahedraMesh;
import fr.xs.jtk.math.type.geom.Point3D;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;

public class TetrahedraTest extends TestApplication3DPattern {

	private Function<Point3D, Number> dens = p -> p.x * p.y * p.z;
	long lastEffect;

	@Override
	public void configure() {
		getCamera().setTranslateZ(-10);
	};

	@Override
	public Group testObject3D() {
		Group group = new Group();

		TetrahedraMesh tetra = new TetrahedraMesh(10, 7, null);
		// cylinder = new PrismMesh(0.2,2,3); //,new Point3D(-5,5,0),new
		// Point3D(0,0,5));
		tetra.setDrawMode(DrawMode.LINE);
		tetra.setCullFace(CullFace.NONE);
		// NONE
		// cylinder.setTextureModeNone(Color.ROYALBLUE);
		// IMAGE
		// tetra.setTextureModeImage(getClass().getResource("res/steel-mesh.jpg").toExternalForm());
		// tetra.setTextureModeVertices1D(6, t->t);
		// cylinder.setColorPalette(ColorPalette.GREEN);
		// DENSITY
		tetra.setTextureModeVertices3D(1530, p -> p.magnitude());
		// cylinder.setTextureModeVertices3D(1530,p->(double)cylinder.unTransform(p).magnitude());
		// FACES
		// tetra.setTextureModeFaces(1530);

		group.getChildren().add(tetra);

		return group;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
