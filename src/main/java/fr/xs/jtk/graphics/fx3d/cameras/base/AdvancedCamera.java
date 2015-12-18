package fr.xs.jtk.graphics.fx3d.cameras.base;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.transform.Affine;

public abstract class AdvancedCamera extends PerspectiveCamera {
	private AdvancedCameraController controller;

	protected final Group  group  = new Group();
	protected final Affine affine = new Affine();

	public AdvancedCamera() {
		super(true);
		setNearClip(0.01);
		setFarClip(1e12);
		setFieldOfView(60.0);
//		setTranslateZ(0);

		this.getTransforms().add(affine);
	}

	public AdvancedCameraController getController() {
		return controller;
	}

	public void setController(AdvancedCameraController controller) {
		controller.setCamera(this);
		this.controller = controller;
	}

	public ObservableList<Node> getChildren() {
		return group.getChildren();
	}

	public Group getGroup() {
		return group;
	}

	public abstract void update();

}
