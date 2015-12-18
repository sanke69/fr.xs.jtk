package fr.xs.jtk.graphics.fx;

import fr.xs.jtk.graphics.fx.api.abstracts.PanelFX;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
@Deprecated
public class ApplicationFX extends Application {

	public static String[] arguments = null;
	protected boolean      fxRunning = false;
	
	protected Stage stage = null;

    @Override
    public void start(Stage primaryStage) {
    	primaryStage.setWidth(1280);
    	primaryStage.setHeight(720);
//    	primaryStage.setResizable(true);
        primaryStage.setScene( scene() );
        primaryStage.getIcons().add(new Image("https://example.com/javaicon.png"));
        primaryStage.show();

        fxRunning = true;

    	Thread notFX = new Thread(() -> run(arguments));
    	notFX.start();

    	stage = primaryStage;
//    	Platform.runLater(() -> run(arguments));
    }

    public Scene scene() {
    	System.out.println("OverRide Me");
    	return null;
    }

    public void run(String[] args) {
    	System.out.println("OverRide Me");
    }

	public static void threadedFX(Class<? extends PanelFX> class1) {
		new JFXPanel();
        Platform.runLater(()-> {
        	Stage stage = new Stage();
        	Scene scene;

			try {
				scene = new Scene(class1.newInstance().getParent());
	        	stage.setScene(scene);
	        	stage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
        });
	}

	public static void waitForKillMainProcess() {
		;
	}

}
