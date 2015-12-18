package fr.xs.jtk.graphics.fx3d.base;

import fr.xs.jtk.graphics.fx3d.cameras.cameras.EulerCamera;
import fr.xs.jtk.graphics.fx3d.cameras.controllers.DefaultEulerController;
import fr.xs.jtk.graphics.fx3d.tools.CameraView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public abstract class TestApplication3DPattern extends Application {
	private final double sceneWidth = 1280;
	private final double sceneHeight = 768;

	private final StackPane root;
	private final Group worldRoot;

	private final Scene scene;
	private final SubScene subscene;

	private final EulerCamera camera;
	private final DefaultEulerController controller;
	
	public TestApplication3DPattern() {
		root = new StackPane();
		scene = new Scene(root, sceneWidth, sceneHeight, true, SceneAntialiasing.BALANCED);

		worldRoot = new Group();
		subscene = new SubScene(worldRoot, sceneWidth, sceneHeight, true, SceneAntialiasing.BALANCED);

		camera = new EulerCamera();
//		camera.setTranslateZ(-1000);
		
		worldRoot.getChildren().add(camera);

		controller = new DefaultEulerController();
		controller.setScene(scene);
		controller.setSubScene(subscene);
		controller.setCamera(camera);

		scene.setFill(Color.BLACK);
		subscene.setFill(Color.BLACK);
		configure();

//		scene.setCamera(camera);
		subscene.setCamera(camera);

		root.getChildren().addAll(subscene);
	}

	public final Scene getScene() {
		return scene;
	}
	public final SubScene getSubScene() {
		return subscene;
	}
	public final EulerCamera getCamera() {
		return camera;
	}
	public final DefaultEulerController getController() {
		return controller;
	}

	public abstract Group testObject3D();

	public void configure() {
		camera.setTranslateZ(0);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		final Group testObject = testObject3D();

//		controller.setObject(testObject);
		worldRoot.getChildren().addAll(testObject);

		CameraView cameraView = new CameraView(subscene);
		cameraView.setFirstPersonNavigationEabled(true);
		cameraView.setFitWidth(350);
		cameraView.setFitHeight(225);
		cameraView.getRx().setAngle(-45);
		cameraView.getT().setZ(-1500);
		cameraView.getT().setY(-500);
		root.getChildren().addAll(cameraView);
		StackPane.setAlignment(cameraView, Pos.BOTTOM_RIGHT);
		StackPane.setMargin(cameraView, new Insets(5));

		cameraView.startViewing();

		primaryStage.setTitle("FX3D Object View Test");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}
