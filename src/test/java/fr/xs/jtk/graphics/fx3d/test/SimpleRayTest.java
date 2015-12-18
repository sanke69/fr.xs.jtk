package fr.xs.jtk.graphics.fx3d.test;

import fr.xs.jtk.graphics.fx3d.base.TestApplication3DPattern;
import fr.xs.jtk.graphics.fx3d.geometry.Ray;
import javafx.animation.Interpolator;
import javafx.animation.PauseTransition;
import javafx.animation.Transition;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Sphere;
import javafx.util.Duration;

public class SimpleRayTest extends TestApplication3DPattern {

	private final PhongMaterial red = new PhongMaterial(Color.DARKRED), blue = new PhongMaterial(Color.DARKCYAN), highlight = new PhongMaterial(Color.CHARTREUSE);
	private final Group root = new Group();
	private Sphere target1, target2;
	private boolean fireRay = true;

	private final AmbientLight rayLight = new AmbientLight();

	final Stop[] stops = new Stop[] { new Stop(0, Color.BLACK), new Stop(0.5, Color.DEEPSKYBLUE.darker()), new Stop(1.0, Color.BLACK) };
	final LinearGradient lg = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);

	public void configure() {
//		getScene().setFill(lg);
//		getSubScene().setFill(lg);
        getCamera().setTranslateZ(-5000);
	}

	@Override
	public Group testObject3D() {
		rayLight.getScope().add(getCamera());

//		getTransformer().setTranslate(0, 0, 0);

		PointLight light = new PointLight(Color.GAINSBORO);
		PointLight light2 = new PointLight(Color.YELLOW);
		light2.setTranslateY(-2000);
		// create a target
		target1 = new Sphere(100);
		target1.setTranslateX(300);
		target1.setTranslateY(300);
		target1.setTranslateZ(1000);
		target1.setMaterial(red);
		// create another target
		target2 = new Sphere(100);
		target2.setTranslateX(800);
		target2.setTranslateY(-1200);
		target2.setTranslateZ(-500);
		target2.setMaterial(blue);

		root.getChildren().addAll(getCamera(), target1, target2, light, light2, rayLight);
		root.setAutoSizeChildren(false);

		
		getSubScene().setOnMousePressed(e -> {
			if(fireRay) {
				// use PickResult because it is already transformed
				Point3D o = e.getPickResult().getIntersectedPoint();
				System.out.println(o);
				if(e.isPrimaryButtonDown()) {
					// set Target and Direction
					Point3D t = Point3D.ZERO.add(target2.getTranslateX(), target2.getTranslateY(), target2.getTranslateZ()), d = t.subtract(o);
					// Build the Ray
					Ray r = new Ray(o, d);
					double dist = t.distance(o);
					// If ray intersects node, spawn and animate
					if(target2.getBoundsInParent().contains(r.project(dist))) {
						animateRayTo(r, target2, Duration.seconds(2));
						System.out.println("Target Contains Ray!\n" + r + "\nTarget Bounds: " + target2.getBoundsInParent() + "\nDistance: " + dist + "\n");
					}

					e.consume();
				} // repeat for other target as well
				else if(e.isSecondaryButtonDown()) {
					Point3D tgt = Point3D.ZERO.add(target1.getTranslateX(), target1.getTranslateY(), target1.getTranslateZ()), dir = tgt.subtract(o);

					Ray r = new Ray(o, dir);
					double dist = tgt.distance(o);
					if(target1.getBoundsInParent().contains(r.project(dist))) {
						animateRayTo(r, target1, Duration.seconds(2));

						System.out.println("Target Contains Ray: " + target1.getBoundsInParent().contains(r.project(dist)) + "\n" + r + "\nTarget Bounds: " + target1.getBoundsInParent() + "\nDistance: " + dist + "\n");
					}
					e.consume();
				}
			}
		});

		return root;
	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Creates and launches a custom Transition animation
	 * 
	 * @param r
	 *            The Ray that holds the info
	 * @param tx
	 *            to x
	 * @param ty
	 *            to y
	 * @param tz
	 *            to z
	 * @param dps
	 *            distance per step to move ray
	 * @param time
	 *            length of animation
	 */
	private void animateRayTo(final Ray r, final Sphere target, final Duration time) {

		final Transition t = new Transition() {
			protected Ray ray;
			protected Sphere s;
			protected double dist;

			{
				this.ray = r;

				this.s = new Sphere(5);
				s.setTranslateX((ray.getOrigin()).getX());
				s.setTranslateY((ray.getOrigin()).getY());
				s.setTranslateZ((ray.getOrigin()).getZ());
				s.setMaterial(highlight);
				rayLight.getScope().add(s);
				this.dist = ray.getOrigin().distance(Point3D.ZERO.add(target.getTranslateX(), target.getTranslateY(), target.getTranslateZ()));

				setCycleDuration(time);
				this.setInterpolator(Interpolator.LINEAR);
				this.setOnFinished(e -> {
					if(target.getBoundsInParent().contains(ray.getPosition())) {
						target.setMaterial(highlight);
						// PauseTransition for delay
						PauseTransition t = new PauseTransition(Duration.millis(750));
						t.setOnFinished(pe -> {
							reset();
							root.getChildren().removeAll(s);
							s = null;
						});
						t.playFromStart();
					}
				});
				root.getChildren().add(s);
			}

			@Override
			protected void interpolate(double frac) {
				// frac-> 0.0 - 1.0
				// project ray
				ray.project(dist * frac);
				// set the sphere to ray position
				s.setTranslateX(ray.getPosition().getX());
				s.setTranslateY(ray.getPosition().getY());
				s.setTranslateZ(ray.getPosition().getZ());
			}

		};
		t.playFromStart();
	}

	// resets materisl on targets
	private void reset() {
		target1.setMaterial(red);
		target2.setMaterial(blue);
	}
}
