package fr.xs.jtk.graphics.fx3d.test;

import java.util.function.Function;

import fr.xs.jtk.graphics.fx3d.base.TestApplication3DPattern;
import fr.xs.jtk.graphics.fx3d.shapes.primitives.PrismMesh;
import fr.xs.jtk.graphics.fx3d.shapes.primitives.helper.TriangleMeshHelper;
import fr.xs.jtk.math.type.geom.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Translate;

public class PrismTest extends TestApplication3DPattern {

	private Function<Point3D, Number> dens = p -> p.x * p.y * p.z;
	long lastEffect;

	@Override
	public void configure() {
		getCamera().setTranslateZ(-10);
	};

	@Override
	public Group testObject3D() {
		Group group = new Group();
		PrismMesh cylinder = new PrismMesh(2, 5, 4);
		// cylinder = new PrismMesh(0.2,2,3); //,new Point3D(-5,5,0),new
		// Point3D(0,0,5));
		// cylinder.setDrawMode(DrawMode.LINE);
		// SECTION TYPE
		cylinder.setSectionType(TriangleMeshHelper.SectionType.TRIANGLE);
		// NONE
		// cylinder.setTextureModeNone(Color.ROYALBLUE);
		// IMAGE
		// cylinder.setTextureModeImage(getClass().getResource("res/netCylinder.png").toExternalForm());
		cylinder.setTextureModeVertices1D(6, t -> t);
		// cylinder.setColorPalette(ColorPalette.GREEN);
		// DENSITY
		// cylinder.setTextureModeVertices3D(1530,p->(double)p.magnitude());
		// cylinder.setTextureModeVertices3D(1530,p->(double)cylinder.unTransform(p).magnitude());
		// FACES
		// cylinder.setTextureModeFaces(1530);

		group.getChildren().add(cylinder);

		boolean showKnots = true;
		if(showKnots) {
			Sphere s = new Sphere(cylinder.getRadius() / 10d);
			Point3D k0 = cylinder.getAxisOrigin();
			s.getTransforms().add(new Translate(k0.x, k0.y, k0.z));
			s.setMaterial(new PhongMaterial(Color.GREENYELLOW));
			group.getChildren().add(s);
			s = new Sphere(cylinder.getRadius() / 10d);
			Point3D k3 = cylinder.getAxisEnd();
			s.getTransforms().add(new Translate(k3.x, k3.y, k3.z));
			s.setMaterial(new PhongMaterial(Color.ROSYBROWN));
			group.getChildren().add(s);
		}

		return group;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
