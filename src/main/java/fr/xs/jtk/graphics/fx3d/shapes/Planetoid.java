package fr.xs.jtk.graphics.fx3d.shapes;

import fr.xs.jtk.beans.universe.AstralCorpseBean;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class Planetoid extends Sphere {
	
	AstralCorpseBean bean;
	boolean clockRotate;

	public Planetoid(String _name, double _radius) {
		super(_radius, 100);
		bean = new AstralCorpseBean(_name, _radius);
		clockRotate = false;
	}
	public Planetoid(AstralCorpseBean _bean) {
		super(_bean.getRadius(), 100);
		bean = _bean;
		clockRotate = false;
	}

	float a = 0.0f;
	public void animate() {
		selfRotation().play();
		Timeline t = new Timeline(new KeyFrame(Duration.millis(20), e -> {
			a = a + 0.0002f;

			Translate translate = new Translate(Math.cos(a) * (bean.getApoastre() / 2), 0, Math.sin(a) * (bean.getPeriastre() / 2));
			getTransforms().addAll(translate);
		}));
		t.setCycleCount(Timeline.INDEFINITE);
		t.play();
		
	}

	private static final double ROTATE_SECS = 30;

	private RotateTransition selfRotation() {
		RotateTransition rotate = new RotateTransition(Duration.seconds(ROTATE_SECS), this);
		rotate.setAxis(Rotate.Y_AXIS);
		rotate.setFromAngle(clockRotate ? 0 : 360);
		rotate.setToAngle(clockRotate ? 360 : 0);
		rotate.setInterpolator(Interpolator.LINEAR);
		rotate.setCycleCount(RotateTransition.INDEFINITE);

		return rotate;
	}

}
