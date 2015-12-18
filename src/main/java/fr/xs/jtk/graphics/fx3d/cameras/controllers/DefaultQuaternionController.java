package fr.xs.jtk.graphics.fx3d.cameras.controllers;

import fr.xs.jtk.graphics.fx3d.cameras.base.AdvancedCamera;
import fr.xs.jtk.graphics.fx3d.cameras.base.AdvancedCameraController;
import fr.xs.jtk.graphics.fx3d.cameras.cameras.QuaternionCamera;
import fr.xs.jtk.tools.Debugger;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class DefaultQuaternionController extends AdvancedCameraController {
	private QuaternionCamera camera;
	
	public DefaultQuaternionController() {
		super();
	}

	@Override
	public void setCamera(AdvancedCamera camera) {
		if(!(camera instanceof QuaternionCamera))
			Debugger.error("Wrong camera type, need QuaternionCamera");
		this.camera = (QuaternionCamera) camera;
	}
	public void setCamera(QuaternionCamera camera) {
		this.camera = camera;
	}

	@Override
	public QuaternionCamera getCamera() {
		return camera;
	}

	@Override
	protected void update() {
		this.camera.update();
	}

	@Override
	protected double getSpeedModifier(KeyEvent event) {
		return event.isShiftDown() ? 100.0 : 1.0;
	}

	@Override
	protected void handleKeyEvent(KeyEvent event, boolean handle) {
		int mode = event.getEventType() == KeyEvent.KEY_PRESSED ? 1 : (event.getEventType() == KeyEvent.KEY_RELEASED) ? 0 : -1;
		if(mode == -1)
			return ;
		
		float step = (float) getSpeedModifier(event);
		switch(event.getCode()) {
		case Z: 		this.camera.move(step);
						break;
		case S: 		this.camera.move(- step);
						break;
		case Q: 		this.camera.strafe(- step);
						break;
		case D: 		this.camera.strafe(step);
						break;
		case R: 		this.camera.raise(- step);
						break;
		case F: 		this.camera.raise(step);
						break;
		case A: 		this.camera.rolled(- step);
						break;
		case E: 		this.camera.rolled(step);
						break;
		case C: 		this.camera.lookAt(0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
						break;
		case SPACE: 	this.camera.move(100.0f * step);
						break;
		default : 		break;
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
	protected void handleLeftMouseDrag(MouseEvent event, Point2D dragDelta, double modifier) {
		this.camera.yawed((float) - dragDelta.getX());
		this.camera.pitched((float) dragDelta.getY());
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
	protected void handleMouseMoved(MouseEvent event, Point2D moveDelta, double speed) {
		// do nothing for now
	}

	@Override
	protected void handleScrollEvent(ScrollEvent event) {
		// do nothing for now
	}

}
