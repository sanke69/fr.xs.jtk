package fr.xs.jtk.graphics.fx3d;

import fr.xs.jtk.graphics.fx3d.cameras.cameras.QuaternionCamera;
import fr.xs.jtk.graphics.fx3d.tools.VirtualUniverseScene;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;

public abstract class Application3D extends Application {
	VirtualUniverseScene     view3D;
	
	public Application3D() {
		view3D = new VirtualUniverseScene(1280, 768);
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
		view3D.addToUniverse( scene3D() );

		primaryStage.setTitle("FX3D Viewer");
		primaryStage.setScene(view3D);
		primaryStage.show();
	}

}
