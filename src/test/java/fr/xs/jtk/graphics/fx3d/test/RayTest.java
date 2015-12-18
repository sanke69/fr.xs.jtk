package fr.xs.jtk.graphics.fx3d.test;

import javafx.geometry.Bounds;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import fr.xs.jtk.graphics.fx3d.base.TestApplication3DPattern;
import fr.xs.jtk.graphics.fx3d.shapes.composites.Histogram;
import fr.xs.jtk.graphics.fx3d.shapes.primitives.CurvedSpringMesh;
import fr.xs.jtk.graphics.fx3d.shapes.primitives.KnotMesh;
import fr.xs.jtk.graphics.fx3d.shapes.primitives.helper.TriangleMeshHelper.SectionType;
import fr.xs.jtk.graphics.fx3d.utils.Axes;

public class RayTest extends TestApplication3DPattern {
	private KnotMesh knot;
	private CurvedSpringMesh spring;

	@Override
	public void configure() {
		getSubScene().setFill(Color.WHITESMOKE);
		getCamera().setTranslateZ(-30);
	};

	@Override
	public Group testObject3D() {
		Group group = new Group();

		knot = new KnotMesh(2d, 1d, 0.4d, 2d, 3d, 100, 20, 0, 0);
		// knot.setDrawMode(DrawMode.LINE);
		knot.setCullFace(CullFace.NONE);
		knot.setSectionType(SectionType.TRIANGLE);
		spring = new CurvedSpringMesh(6d, 2d, 0.4d, 25d, 6.25d * 2d * Math.PI, 1000, 60, 0, 0);
		spring.getTransforms().addAll(new Translate(6, -6, 0));
		spring.setDrawMode(DrawMode.LINE);
		spring.setCullFace(CullFace.NONE);
		// spring.setTextureModeVertices3D(256*256,dens);
		// NONE
		knot.setTextureModeNone(Color.BROWN);
		spring.setTextureModeNone(Color.BROWN);

		group.getChildren().add(knot);
		group.getChildren().add(spring);

		/*
		 * Origin in knot Target in spring
		 */
		fr.xs.jtk.math.type.geom.Point3D locOrigin = knot.getOrigin();
		Point3D gloOrigin = knot.localToScene(new Point3D(locOrigin.x, locOrigin.y, locOrigin.z));
		fr.xs.jtk.math.type.geom.Point3D locTarget1 = spring.getOrigin();
		Point3D locTarget2 = new Point3D(locTarget1.x, locTarget1.y, locTarget1.z);
		Point3D gloTarget = spring.localToScene(locTarget2);

		Point3D gloDirection = gloTarget.subtract(gloOrigin).normalize();
		Point3D gloOriginInLoc = spring.sceneToLocal(gloOrigin);

		Bounds locBounds = spring.getBoundsInLocal();
		Bounds gloBounds = spring.localToScene(locBounds);

		Sphere s = new Sphere(0.05d);
		s.getTransforms().add(new Translate(gloOrigin.getX(), gloOrigin.getY(), gloOrigin.getZ()));
		s.setMaterial(new PhongMaterial(Color.GREENYELLOW));
		group.getChildren().add(s);
		s = new Sphere(0.05d);
		s.getTransforms().add(new Translate(gloTarget.getX(), gloTarget.getY(), gloTarget.getZ()));
		s.setMaterial(new PhongMaterial(Color.GREENYELLOW));

		Point3D dir = gloTarget.subtract(gloOrigin).crossProduct(new Point3D(0, -1, 0));
		double angle = Math.acos(gloTarget.subtract(gloOrigin).normalize().dotProduct(new Point3D(0, -1, 0)));
		double h1 = gloTarget.subtract(gloOrigin).magnitude();
		Cylinder c = new Cylinder(0.01d, h1);
		c.getTransforms().addAll(new Translate(gloOrigin.getX(), gloOrigin.getY() - h1 / 2d, gloOrigin.getZ()), new Rotate(-Math.toDegrees(angle), 0d, h1 / 2d, 0d, new Point3D(dir.getX(), -dir.getY(), dir.getZ())));
		c.setMaterial(new PhongMaterial(Color.GREEN));
		group.getChildren().add(c);

		group.getChildren().add(new Axes(0.02));
		Box box = new Box(gloBounds.getWidth(), gloBounds.getHeight(), gloBounds.getDepth());
		// If transparency is enabled (8u60+), comment:
		box.setDrawMode(DrawMode.LINE);
		box.setCullFace(CullFace.NONE);
		// If transparency is enabled (8u60+), uncomment:
		// box.setMaterial(new PhongMaterial(Color.web("8A2BE2",0.3)));
		box.getTransforms().add(new Translate(gloBounds.getMinX() + gloBounds.getWidth() / 2d, gloBounds.getMinY() + gloBounds.getHeight() / 2d, gloBounds.getMinZ() + gloBounds.getDepth() / 2d));
		group.getChildren().add(box);

		/*
		 * FIRST STEP; Check the ray crosses the bounding box of the shape at
		 * any of its 6 faces
		 */
		List<Point3D> normals = Arrays.asList(new Point3D(-1, 0, 0), new Point3D(1, 0, 0), new Point3D(0, -1, 0), new Point3D(0, 1, 0), new Point3D(0, 0, -1), new Point3D(0, 0, 1));
		List<Point3D> positions = Arrays.asList(new Point3D(locBounds.getMinX(), 0, 0), new Point3D(locBounds.getMaxX(), 0, 0), new Point3D(0, locBounds.getMinY(), 0), new Point3D(0, locBounds.getMaxY(), 0), new Point3D(0, 0, locBounds.getMinZ()),
				new Point3D(0, 0, locBounds.getMaxZ()));
		AtomicInteger counter = new AtomicInteger();
		IntStream.range(0, 6).forEach(i -> {
			// ray[t]= ori+t.dir; t/ray[t]=P in plane
			// plane P·N+d=0->(ori+t*dir)·N+d=0->t=-(ori.N+d)/(dir.N)
			// P=P(x,y,z), N={a,b,c}, d=-a·x0-b·y0-c·z0
			double d = -normals.get(i).dotProduct(positions.get(i));
			double t = -(gloOriginInLoc.dotProduct(normals.get(i)) + d) / (gloDirection.dotProduct(normals.get(i)));
			Point3D locInter = gloOriginInLoc.add(gloDirection.multiply(t));
			if(locBounds.contains(locInter)) {
				counter.getAndIncrement();
				Point3D gloInter = spring.localToScene(locInter);
				Sphere s2 = new Sphere(0.1d);
				s2.getTransforms().add(new Translate(gloInter.getX(), gloInter.getY(), gloInter.getZ()));
				s2.setMaterial(new PhongMaterial(Color.GOLD));
				group.getChildren().add(s2);
			}
		});
		if(counter.get() > 0) {
			/*
			 * SECOND STEP: Check if the ray crosses any of the triangles of the mesh
			 */
			// triangle mesh
			fr.xs.jtk.math.type.geom.Point3D gloOriginInLoc1 = new fr.xs.jtk.math.type.geom.Point3D((float) gloOriginInLoc.getX(), (float) gloOriginInLoc.getY(), (float) gloOriginInLoc.getZ());
			fr.xs.jtk.math.type.geom.Point3D gloDirection1 = new fr.xs.jtk.math.type.geom.Point3D((float) gloDirection.getX(), (float) gloDirection.getY(), (float) gloDirection.getZ());

			System.out.println("inter: " + spring.getIntersections(gloOriginInLoc1, gloDirection1));
		}

		return group;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
