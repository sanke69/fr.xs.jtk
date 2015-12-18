package fr.xs.jtk.graphics.fx3d.cameras.controllers;

import fr.xs.jtk.graphics.fx3d.cameras.base.AdvancedCamera;
import fr.xs.jtk.graphics.fx3d.cameras.base.AdvancedCameraController;
import fr.xs.jtk.graphics.fx3d.cameras.cameras.EulerCamera;
import fr.xs.jtk.graphics.fx3d.cameras.cameras.QuaternionCamera;
import fr.xs.jtk.tools.Debugger;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class DefaultEulerController extends AdvancedCameraController {
	private EulerCamera camera;

	public DefaultEulerController() {
		super();
	}

	@Override
	public void setCamera(AdvancedCamera camera) {
		if(!(camera instanceof EulerCamera))
			Debugger.error("Wrong camera type, need QuaternionCamera");
		this.camera = (EulerCamera) camera;
	}
	public void setCamera(EulerCamera camera) {
		this.camera = camera;
	}

	@Override
	public EulerCamera getCamera() {
		return camera;
	}

	@Override
	protected void update() {
		this.camera.update();
	}

	@Override
	protected double getSpeedModifier(KeyEvent event) {
		return event.getCode() == KeyCode.SHIFT ? 10.0 : 1.0;
	}

	@Override
	protected void handleKeyEvent(KeyEvent event, boolean handle) {
		double change = event.isShiftDown() ? 50.0 : 10.0;

		switch(event.getCode()) {
		case Z: 		camera.setTranslateZ(camera.getTranslateZ() + change); break;
		case S: 		camera.setTranslateZ(camera.getTranslateZ() - change); break;
		case Q: 		camera.setTranslateX(camera.getTranslateX() - change); break;
		case D: 		camera.setTranslateX(camera.getTranslateX() + change); break;
		case R: 		camera.setTranslateX(camera.getTranslateY() - change); break;
		case F: 		camera.setTranslateX(camera.getTranslateY() + change); break;
		case SPACE: 	break;
		default: 		break;
		}
	}

	@Override
	protected void handleLeftMousePress(MouseEvent e) {
		System.out.println("Primary Button Pressed!");
	}
	@Override
	protected void handleLeftMouseReleased(MouseEvent t) {
		System.out.println("Primary Button Clicked!");
	}
	@Override
	protected void handleLeftMouseDrag(MouseEvent e, Point2D dragDelta, double modifier) {
		modifier = e.isControlDown() ? 0.1 : e.isShiftDown() ? 50.0 : modifier;

		if(e.isPrimaryButtonDown()) {
			camera.ry.setAngle(((camera.ry.getAngle() + dragDelta.getX() * modifier * 2.0) % 360 + 540) % 360 - 180); // +
			camera.rx.setAngle(((camera.rx.getAngle() - dragDelta.getY() * modifier * 2.0) % 360 + 540) % 360 - 180); // -
		} else if(e.isSecondaryButtonDown()) {
			camera.setTranslateZ( camera.getTranslateZ() + dragDelta.getX() * modifier );
		} else if(e.isMiddleButtonDown()) {
			camera.t.setX(camera.t.getX() + dragDelta.getX() * modifier * 0.3); // -
			camera.t.setY(camera.t.getY() + dragDelta.getY() * modifier * 0.3); // -
		}
	}

	@Override
	protected void handleRightMousePress(MouseEvent e) {
		System.out.println("Secondary Button Pressed!");
	}
	@Override
	protected void handleRightMouseReleased(MouseEvent t) {
		System.out.println("Secondary Button Clicked!");
	}
	@Override
	protected void handleRightMouseDrag(MouseEvent event, Point2D dragDelta, double modifier) {
		// do nothing for now
	}
	
	@Override
	protected void handleMiddleMousePress(MouseEvent e) {
		System.out.println("Middle Button Pressed!");
	}
	@Override
	protected void handleMiddleMouseReleased(MouseEvent t) {
		System.out.println("Middle Button Clicked!");
	}
	@Override
	protected void handleMiddleMouseDrag(MouseEvent event, Point2D dragDelta, double modifier) {
		// do nothing for now
	}

	@Override
	protected void handleMouseMoved(MouseEvent event, Point2D moveDelta, double speed) {
		// do nothing for now
	}

	@Override
	protected void handleScrollEvent(ScrollEvent event) {
		// do nothing for now
	}

}
