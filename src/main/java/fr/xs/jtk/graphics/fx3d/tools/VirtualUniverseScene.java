package fr.xs.jtk.graphics.fx3d.tools;

import fr.xs.jtk.graphics.fx3d.cameras.cameras.QuaternionCamera;
import fr.xs.jtk.graphics.fx3d.cameras.controllers.DefaultQuaternionController;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;

public class VirtualUniverseScene extends Scene {
	Group rootWorld;
	
	QuaternionCamera camera;
	public DefaultQuaternionController controller;
	
	public VirtualUniverseScene(double width, double height) {
		super(new Group(), width, height, true, SceneAntialiasing.BALANCED);
		rootWorld = (Group) this.getRoot();

		camera     = new QuaternionCamera();

		controller = new DefaultQuaternionController();
		controller.setScene(this);
		controller.setCamera(camera);

		setFill(Color.BLACK);
		setCamera(camera);

//		rootWorld.getChildren().add(camera);
		rootWorld.getChildren().add(camera.getGroup());
	}

	public final DefaultQuaternionController getController() {
		return controller;
	}

	public void addToUniverse(Node _node) {
		camera.getGroup().getChildren().add(_node);
	}
	public void addAll2Universe(Node... _node) {
		camera.getGroup().getChildren().addAll(_node);
	}
	public void removeFromUniverse(Node _node) {
		camera.getGroup().getChildren().remove(_node);
	}

}
