package fr.xs.jtk.graphics.fx3d.test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import fr.xs.jtk.graphics.fx3d.base.TestApplication3DPattern;
import fr.xs.jtk.graphics.fx3d.shapes.primitives.CuboidMesh;
import fr.xs.jtk.graphics.fx3d.utils.Axes;
import fr.xs.jtk.graphics.fx3d.utils.OBJWriter;
import fr.xs.jtk.math.type.geom.Point3D;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class CuboidTest extends TestApplication3DPattern {

	@Override
	public void configure() {
		getScene().setFill(Color.WHITESMOKE);
		getCamera().setTranslateZ(-30);
	};

	@Override
	public Group testObject3D() {
		Group group = new Group();
		
		CuboidMesh cuboid = new CuboidMesh(10f, 12f, 4f, 5, new Point3D(0f, 0f, 0f));
		// cuboid.setDrawMode(DrawMode.LINE);
		// cuboid.setCullFace(CullFace.NONE);
		// NONE
		cuboid.setTextureModeNone(Color.ROYALBLUE);
		// IMAGE
		// cuboid.setTextureModeImage(getClass().getResource("res/netCuboid.png").toExternalForm());
		// DENSITY
		// cuboid.setTextureModeVertices3D(256*256,p->(double)p.x*p.y*p.z);
		// FACES
		// cuboid.setTextureModeFaces(1530);

		group.getChildren().add(cuboid);

		boolean testRayIntersection = false;

		if(testRayIntersection) {
			/*
			 * RAY INTERSECTION
			 */
			javafx.geometry.Point3D gloOrigin = new javafx.geometry.Point3D(4, -7, -4);
			javafx.geometry.Point3D gloTarget = new javafx.geometry.Point3D(2, 3, 4);

			javafx.geometry.Point3D gloDirection = gloTarget.subtract(gloOrigin).normalize();
			javafx.geometry.Point3D gloOriginInLoc = cuboid.sceneToLocal(gloOrigin);

			Bounds locBounds = cuboid.getBoundsInLocal();
			Bounds gloBounds = cuboid.localToScene(locBounds);

			Sphere s = new Sphere(0.05d);
			s.getTransforms().add(new Translate(gloOrigin.getX(), gloOrigin.getY(), gloOrigin.getZ()));
			s.setMaterial(new PhongMaterial(Color.GREENYELLOW));
			group.getChildren().add(s);
			s = new Sphere(0.05d);
			s.getTransforms().add(new Translate(gloTarget.getX(), gloTarget.getY(), gloTarget.getZ()));
			s.setMaterial(new PhongMaterial(Color.GREENYELLOW));
			group.getChildren().add(s);

			javafx.geometry.Point3D dir = gloTarget.subtract(gloOrigin).crossProduct(new javafx.geometry.Point3D(0, -1, 0));
			double angle = Math.acos(gloTarget.subtract(gloOrigin).normalize().dotProduct(new javafx.geometry.Point3D(0, -1, 0)));
			double h1 = gloTarget.subtract(gloOrigin).magnitude();
			Cylinder c = new Cylinder(0.03d, h1);
			c.getTransforms().addAll(new Translate(gloOrigin.getX(), gloOrigin.getY() - h1 / 2d, gloOrigin.getZ()), new Rotate(-Math.toDegrees(angle), 0d, h1 / 2d, 0d, new javafx.geometry.Point3D(dir.getX(), -dir.getY(), dir.getZ())));
			c.setMaterial(new PhongMaterial(Color.GREEN));
			group.getChildren().add(c);

			group.getChildren().add(new Axes(0.02));
			Box box = new Box(gloBounds.getWidth(), gloBounds.getHeight(), gloBounds.getDepth());
			box.setDrawMode(DrawMode.LINE);
			box.setMaterial(new PhongMaterial(Color.BLUEVIOLET));
			box.getTransforms().add(new Translate(gloBounds.getMinX() + gloBounds.getWidth() / 2d, gloBounds.getMinY() + gloBounds.getHeight() / 2d, gloBounds.getMinZ() + gloBounds.getDepth() / 2d));
			// group.getChildren().add(box);

			/*
			 * FIRST STEP; Check the ray crosses the bounding box of the shape
			 * at any of its 6 faces
			 */
			List<javafx.geometry.Point3D> normals = Arrays.asList(new javafx.geometry.Point3D(-1, 0, 0), new javafx.geometry.Point3D(1, 0, 0), new javafx.geometry.Point3D(0, -1, 0), new javafx.geometry.Point3D(0, 1, 0),
					new javafx.geometry.Point3D(0, 0, -1), new javafx.geometry.Point3D(0, 0, 1));
			List<javafx.geometry.Point3D> positions = Arrays.asList(new javafx.geometry.Point3D(locBounds.getMinX(), 0, 0), new javafx.geometry.Point3D(locBounds.getMaxX(), 0, 0), new javafx.geometry.Point3D(0, locBounds.getMinY(), 0),
					new javafx.geometry.Point3D(0, locBounds.getMaxY(), 0), new javafx.geometry.Point3D(0, 0, locBounds.getMinZ()), new javafx.geometry.Point3D(0, 0, locBounds.getMaxZ()));
			AtomicInteger counter = new AtomicInteger();
			IntStream.range(0, 6).forEach(i -> {
				// ray[t]= ori+t.dir; t/ray[t]=P in plane
				// plane P·N+d=0->(ori+t*dir)·N+d=0->t=-(ori.N+d)/(dir.N)
				// P=P(x,y,z), N={a,b,c}, d=-a·x0-b·y0-c·z0
				double d = -normals.get(i).dotProduct(positions.get(i));
				double t = -(gloOriginInLoc.dotProduct(normals.get(i)) + d) / (gloDirection.dotProduct(normals.get(i)));
				javafx.geometry.Point3D locInter = gloOriginInLoc.add(gloDirection.multiply(t));
				if(locBounds.contains(locInter)) {
					counter.getAndIncrement();

					javafx.geometry.Point3D gloInter = cuboid.localToScene(locInter);
					Sphere s2 = new Sphere(0.1d);
					s2.getTransforms().add(new Translate(gloInter.getX(), gloInter.getY(), gloInter.getZ()));
					s2.setMaterial(new PhongMaterial(Color.GOLD));
					// group.getChildren().add(s2);
				}
			});
			if(counter.get() > 0) {
				/*
				 * SECOND STEP: Check if the ray crosses any of the triangles of
				 * the mesh
				 */
				// triangle mesh
				fr.xs.jtk.math.type.geom.Point3D gloOriginInLoc1 = new fr.xs.jtk.math.type.geom.Point3D((float) gloOriginInLoc.getX(), (float) gloOriginInLoc.getY(), (float) gloOriginInLoc.getZ());
				fr.xs.jtk.math.type.geom.Point3D gloDirection1 = new fr.xs.jtk.math.type.geom.Point3D((float) gloDirection.getX(), (float) gloDirection.getY(), (float) gloDirection.getZ());

				System.out.println("number of intersections: " + cuboid.getIntersections(gloOriginInLoc1, gloDirection1));
			}
		}

		boolean write = false;
		if(write) {
			OBJWriter writer = new OBJWriter((TriangleMesh) cuboid.getMesh(), "cuboid");
			// writer.setMaterialColor(Color.AQUA);
			// writer.setTextureImage(getClass().getResource("res/netCuboid.png").toExternalForm());
			writer.setTextureColors(256 * 256);
			writer.exportMesh();
		}

		return group;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
