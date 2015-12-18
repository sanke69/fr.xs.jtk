package fr.xs.jtk.graphics.fx.api.splash;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SplashScreen extends Stage {
	protected Scene scene = null;

	public SplashScreen() {
		super();

		getIcons().add(new Image("https://example.com/javaicon.png"));
		initStyle(StageStyle.UNDECORATED);
		initStyle(StageStyle.TRANSPARENT);
		setAlwaysOnTop(true);

		setWidth(320);
		setHeight(240);
		setResizable(false);
		
		/**
        centerOnScreen();
        /*/
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        setX((primScreenBounds.getWidth() - getWidth()) / 2);
        setY((primScreenBounds.getHeight() - getHeight()) / 2);
        /**/

		setScene(scene = defaultContent());
	}

	public SplashScreen(Scene _content) {
		this();
		setScene(scene = _content);
	}

	private static Scene defaultContent() {
		BorderPane borderPane = new BorderPane();
		borderPane.setStyle("-fx-background-color: green;");

		borderPane.setCenter(new Label("insert here"));

		Scene newScene = new Scene(borderPane);
		newScene.setFill(Color.TRANSPARENT);
		return newScene;
	}

}
