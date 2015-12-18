package fr.xs.jtk.graphics.fx3d;

import fr.xs.jtk.graphics.fx3d.cameras.cameras.QuaternionCamera;
import fr.xs.jtk.graphics.fx3d.tools.VirtualUniverseView;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public abstract class Application3D2 extends Application {
	private final StackPane root;
	private final Scene     scene;

	VirtualUniverseView     view3D;
	
	public Application3D2() {
		root   = new StackPane();
		scene  = new Scene(root);
		view3D = new VirtualUniverseView(1280, 768, scene);

		root.getChildren().add(view3D);
		System.out.println(scene.isDepthBuffer());
		configure();
	}

	public final QuaternionCamera getCamera() {
		return (QuaternionCamera) view3D.getCamera();
	}

	public abstract Group scene3D();

	public void configure() {
		;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		view3D.add2Universe( scene3D() );

		primaryStage.setTitle("FX3D Viewer");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
