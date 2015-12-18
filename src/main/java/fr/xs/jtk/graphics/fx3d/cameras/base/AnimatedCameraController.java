package fr.xs.jtk.graphics.fx3d.cameras.base;

import fr.xs.jtk.graphics.fx3d.utils.AnimationPreference;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.util.Duration;

public abstract class AnimatedCameraController extends AdvancedCameraController {
	// Gestion de l'animation
	private AnimationPreference animPref;
	private AnimationTimer      timer;
	private Timeline            timeline;
	private Transition          transition;

	public AnimatedCameraController(AnimationPreference movementType) {
		animPref = movementType;
		switch(animPref) {
		case TIMELINE:
			timeline = new Timeline();
			timeline.setCycleCount(Animation.INDEFINITE);
			break;
		case TIMER:
			timer = new AnimationTimer() {
				@Override
				public void handle(long l) {
					update();
				}
			};
			break;
		case TRANSITION:
			transition = new Transition() {
				{
					setCycleDuration(Duration.seconds(1));
				}

				@Override
				protected void interpolate(double frac) {
					updateTransition(frac);
				}
			};
			transition.setCycleCount(Animation.INDEFINITE);
			break;
		case ANIMATION:
			break;
		}

	}

	public void setCamera(AdvancedCamera camera) {
		this.camera = camera;
		switch(animPref) {
		case TIMELINE:
			timeline.getKeyFrames().addAll(new KeyFrame(Duration.millis(15), e -> {
				new Timeline(new KeyFrame[] { new KeyFrame(Duration.ONE, ev -> {
							update();
						}) }).play();
			}));
			timeline.play();
			break;
		case TIMER:
			timer.start();
			break;
		case TRANSITION:
			transition.play();
			break;
		case ANIMATION:
			break;
		}
	}

	protected abstract void updateTransition(double now);

}
