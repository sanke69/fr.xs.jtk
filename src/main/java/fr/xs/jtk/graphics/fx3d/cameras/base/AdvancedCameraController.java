package fr.xs.jtk.graphics.fx3d.cameras.base;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public abstract class AdvancedCameraController {
	// Gestion de l'environnement 3D
	private   Scene             scene;
	private   SubScene          subScene;
	protected AdvancedCamera    camera;

	private double previousX, previousY, speed = 1.0;

	public AdvancedCameraController() {
		super();
	}

	public AdvancedCamera getCamera() {
		return camera;
	}

	public void setCamera(AdvancedCamera camera) {
		this.camera = camera;
	}

	protected Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
		setEventHandlers(scene);
	}

	protected SubScene getSubScene() {
		return subScene;
	}

	public void setSubScene(SubScene subScene) {
		this.subScene = subScene;
		setEventHandlers(subScene);
	}

	protected void update() {
		camera.update();
	}

	// Abstract Methods
	protected abstract double getSpeedModifier(KeyEvent event);

	protected abstract void handleKeyEvent(KeyEvent event, boolean handle);

	protected abstract void handleLeftMousePress(MouseEvent e);
	protected abstract void handleLeftMouseReleased(MouseEvent e);
	protected abstract void handleLeftMouseDrag(MouseEvent event, Point2D dragDelta, double modifier);

	protected abstract void handleRightMousePress(MouseEvent e);
	protected abstract void handleRightMouseReleased(MouseEvent e);
	protected abstract void handleRightMouseDrag(MouseEvent event, Point2D dragDelta, double modifier);

	protected abstract void handleMiddleMousePress(MouseEvent e);
	protected abstract void handleMiddleMouseReleased(MouseEvent e);
	protected abstract void handleMiddleMouseDrag(MouseEvent event, Point2D dragDelta, double modifier);

	protected abstract void handleMouseMoved(MouseEvent event, Point2D moveDelta, double modifier);

	protected abstract void handleScrollEvent(ScrollEvent event);

	private void handleKeyEvent(KeyEvent t) {
		if(t.getEventType() == KeyEvent.KEY_PRESSED) {
			handleKeyEvent(t, true);
		} else if(t.getEventType() == KeyEvent.KEY_RELEASED) {
			handleKeyEvent(t, true);
		}
		speed = getSpeedModifier(t);

		update();
	}
	private void handleMouseEvent(MouseEvent t) {
		if(t.getEventType() == MouseEvent.MOUSE_PRESSED) {
			switch(t.getButton()) {
			case PRIMARY : 		handleLeftMousePress(t); break;
			case MIDDLE : 		handleMiddleMousePress(t); break;
			case SECONDARY : 	handleRightMousePress(t); break;
			default : 			throw new AssertionError();
			}
			handleMousePress(t);
		} else if(t.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			Point2D d = getMouseDelta(t);

			switch(t.getButton()) {
			case PRIMARY :		handleLeftMouseDrag(t, d, speed); break;
			case MIDDLE : 		handleMiddleMouseDrag(t, d, speed); break;
			case SECONDARY :	handleRightMouseDrag(t, d, speed); break;
			default :			throw new AssertionError();
			}
		} else if(t.getEventType() == MouseEvent.MOUSE_MOVED) {
			handleMouseMoved(t, getMouseDelta(t), speed);
		} else if(t.getEventType() == MouseEvent.MOUSE_CLICKED) {
			switch(t.getButton()) {
			case PRIMARY :  	handleLeftMouseReleased(t); break;
			case MIDDLE :		handleMiddleMouseReleased(t); break;
			case SECONDARY :	handleRightMouseReleased(t); break;
			default: 			throw new AssertionError();
			}
		}

		update();
	}

	private void setEventHandlers(Scene scene) {
		scene.addEventHandler(KeyEvent.ANY, k -> handleKeyEvent(k));
		scene.addEventHandler(MouseEvent.ANY, m -> handleMouseEvent(m));
		scene.addEventHandler(ScrollEvent.ANY, s -> handleScrollEvent(s));
	}

	private void setEventHandlers(SubScene scene) {
		scene.addEventHandler(KeyEvent.ANY, k -> handleKeyEvent(k));
		scene.addEventHandler(MouseEvent.ANY, m -> handleMouseEvent(m));
		scene.addEventHandler(ScrollEvent.ANY, s -> handleScrollEvent(s));
	}

	private void handleMousePress(MouseEvent event) {
		previousX = event.getSceneX();
		previousY = event.getSceneY();
		event.consume();
	}

	private Point2D getMouseDelta(MouseEvent event) {
		Point2D res = new Point2D(event.getSceneX() - previousX, event.getSceneY() - previousY);
		previousX = event.getSceneX();
		previousY = event.getSceneY();
		return res;
	}

}
