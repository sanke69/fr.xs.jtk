package fr.xs.jtk.graphics.fx3d.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import fr.xs.jtk.graphics.fx3d.base.TestApplication3DPattern;
import fr.xs.jtk.graphics.fx3d.shapes.primitives.BezierMesh;
import fr.xs.jtk.graphics.fx3d.shapes.primitives.PrismMesh;
import fr.xs.jtk.graphics.fx3d.shapes.primitives.helper.InterpolateBezier;
import fr.xs.jtk.math.type.geom.Point3D;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class BezierTest extends TestApplication3DPattern {
	private ArrayList<BezierMesh> beziers;
	private Function<Point3D, Number> dens = p -> p.f;
	private Function<Number, Number> func = t -> t;
	private long lastEffect;
	private Rotate rotateY;

	@Override
	public void configure() {
		getScene().setFill(Color.WHITESMOKE);
		getCamera().setTranslateZ(-30);
	};

	@Override
	public Group testObject3D() {
		Group group = new Group();

		rotateY = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);

		List<Point3D> knots = Arrays.asList(new Point3D(3f, 0f, 0f), new Point3D(0.77171f, 1.68981f, 0.989821f), new Point3D(-0.681387f, 0.786363f, -0.281733f), new Point3D(-2.31757f, -0.680501f, -0.909632f), new Point3D(-0.404353f, -2.81233f, 0.540641f),
				new Point3D(1.1316f, -0.727237f, 0.75575f), new Point3D(1.1316f, 0.727237f, -0.75575f), new Point3D(-0.404353f, 2.81233f, -0.540641f), new Point3D(-2.31757f, 0.680501f, 0.909632f), new Point3D(-0.681387f, -0.786363f, 0.281733f),
				new Point3D(0.77171f, -1.68981f, -0.989821f), new Point3D(3f, 0f, 0f));

		boolean showControlPoints = true;
		boolean showKnots = true;

		InterpolateBezier interpolate = new InterpolateBezier(knots);
		beziers = new ArrayList<>();
		AtomicInteger sp = new AtomicInteger();
		if(showKnots || showControlPoints) {
			interpolate.getSplines().forEach(spline -> {
				Point3D k0 = spline.getPoints().get(0);
				Point3D k1 = spline.getPoints().get(1);
				Point3D k2 = spline.getPoints().get(2);
				Point3D k3 = spline.getPoints().get(3);
				if(showKnots) {
					Sphere s = new Sphere(0.2d);
					s.getTransforms().add(new Translate(k0.x, k0.y, k0.z));
					s.setMaterial(new PhongMaterial(Color.GREENYELLOW));
					group.getChildren().add(s);
					s = new Sphere(0.2d);
					s.getTransforms().add(new Translate(k3.x, k3.y, k3.z));
					s.setMaterial(new PhongMaterial(Color.GREENYELLOW));
					group.getChildren().add(s);
				}
				if(showControlPoints) {
					PrismMesh c = new PrismMesh(0.03d, 1d, 1, k0, k1);
					c.setTextureModeNone(Color.GREEN);
					group.getChildren().add(c);

					c = new PrismMesh(0.03d, 1d, 1, k1, k2);
					c.setTextureModeNone(Color.GREEN);
					group.getChildren().add(c);

					c = new PrismMesh(0.03d, 1d, 1, k2, k3);
					c.setTextureModeNone(Color.GREEN);
					group.getChildren().add(c);

					Sphere s = new Sphere(0.1d);
					s.getTransforms().add(new Translate(k1.x, k1.y, k1.z));
					s.setMaterial(new PhongMaterial(Color.RED));
					group.getChildren().add(s);
					s = new Sphere(0.1d);
					s.getTransforms().add(new Translate(k2.x, k2.y, k2.z));
					s.setMaterial(new PhongMaterial(Color.RED));
					group.getChildren().add(s);
				}
			});
		}
		long time = System.currentTimeMillis();
		interpolate.getSplines().stream().forEach(spline -> {
			BezierMesh bezier = new BezierMesh(spline, 0.1d, 300, 20, 0, 0);
			// bezier.setDrawMode(DrawMode.LINE);
			// bezier.setCullFace(CullFace.NONE);
			// bezier.setSectionType(SectionType.TRIANGLE);

			// NONE
			// bezier.setTextureModeNone(Color.hsb(360d*sp.getAndIncrement()/interpolate.getSplines().size(),
			// 1, 1));
			// IMAGE
			// bezier.setTextureModeImage(getClass().getResource("res/LaminateSteel.jpg").toExternalForm());
			// PATTERN
			// bezier.setTextureModePattern(3d);
			// FUNCTION
			bezier.setTextureModeVertices1D(1530, t -> spline.getKappa(t.doubleValue()));
			// bezier.setTextureModeVertices1D(1530,func);
			// DENSITY
			// bezier.setTextureModeVertices3D(256*256,dens);
			// FACES
			// bezier.setTextureModeFaces(256*256);

			bezier.getTransforms().addAll(new Rotate(0, Rotate.X_AXIS), rotateY);
			beziers.add(bezier);
		});
		System.out.println("time: " + (System.currentTimeMillis() - time)); // 43.815->25.606->15
		group.getChildren().addAll(beziers);

		lastEffect = System.nanoTime();
		AtomicInteger count = new AtomicInteger();
		AnimationTimer timerEffect = new AnimationTimer() {

			@Override
			public void handle(long now) {
				if(now > lastEffect + 1_000_000_000l) {
					// Point3D loc =
					// knot.getPositionAt((count.get()%100)*2d*Math.PI/100d);
					// Point3D dir =
					// knot.getTangentAt((count.get()%100)*2d*Math.PI/100d);
					// cameraTransform.t.setX(loc.x);
					// cameraTransform.t.setY(loc.y);
					// cameraTransform.t.setZ(-loc.z);
					// javafx.geometry.Point3D axis =
					// cameraTransform.rx.getAxis();
					// javafx.geometry.Point3D cross =
					// axis.crossProduct(-dir.x,-dir.y,-dir.z);
					// double angle = axis.angle(-dir.x,-dir.y,-dir.z);
					// cameraTransform.rx.setAngle(angle);
					// cameraTransform.rx.setAxis(new
					// javafx.geometry.Point3D(cross.getX(),-cross.getY(),cross.getZ()));
					// dens =
					// p->(float)(p.x*Math.cos(count.get()%100d*2d*Math.PI/50d)+p.y*Math.sin(count.get()%100d*2d*Math.PI/50d));
					func = t -> Math.pow(t.doubleValue(), (count.get() % 5d));
					beziers.forEach(b -> b.setFunction(func));
					// knot.setP(1+(count.get()%5));
					// knot.setQ(2+(count.get()%15));

					// if(count.get()%100<50){
					// knot.setDrawMode(DrawMode.LINE);
					// } else {
					// knot.setDrawMode(DrawMode.FILL);
					// }
					// beziers.forEach(b->b.setColors((int)Math.pow(2,count.get()%16)));
					// beziers.forEach(b->b.setWireRadius(0.1d+(count.get()%6)/10d));
					// beziers.forEach(b->b.setPatternScale(1d+(count.get()%10)*3d));
					// beziers.forEach(b->b.setSectionType(SectionType.values()[count.get()%SectionType.values().length]));
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
