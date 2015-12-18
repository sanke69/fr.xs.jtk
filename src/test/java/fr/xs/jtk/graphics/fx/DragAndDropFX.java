package fr.xs.jtk.graphics.fx;

import java.io.File;

import fr.xs.jtk.math.type.Vector2f;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DragAndDropFX extends Application {

	private static final Image IMAGE = new Image("http://sp-web.fr/.hidden_resources/medical-device-icon.png");

	public static void main(String[] args) {
		Application.launch(args);
	}

	int shadowSize = 50;

	public Scene defineScene() {
		Label label = new Label("Glisser-Déposer\nles accusés de réception NOEMIE\nici!");
		label.setWrapText(true);
		label.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 16; -fx-text-fill: black;");

		ImageView image = new ImageView(IMAGE);
		image.setOpacity(0.3);

		Button closeBtn = new Button("X");
		closeBtn.setOnAction(e -> Platform.exit());

		StackPane layout = new StackPane();
		layout.getChildren().setAll(image, label);

		BorderPane borderPane = new BorderPane();
		borderPane.setStyle("-fx-background-color: green;");

		HBox box = new HBox();
		box.setAlignment(Pos.CENTER_RIGHT);
		box.getChildren().add(closeBtn);

		borderPane.setTop(layout);
		borderPane.setBottom(box);

		return new Scene(borderPane);
	}

	public Scene addDragAndDropToScene(Scene _scene) {
		_scene.setOnDragOver(e -> {
			Dragboard db = e.getDragboard();
			if (db.hasFiles())
				e.acceptTransferModes(TransferMode.COPY);
			else
				e.consume();
		});
		_scene.setOnDragDropped(e -> {
			Dragboard db = e.getDragboard();
			boolean success = false;
			if (db.hasFiles()) {
				success = true;
				String filePath = null;
				for(File file : db.getFiles()) {
					filePath = file.getAbsolutePath();
					System.out.println(filePath);
				}
			}
			e.setDropCompleted(success);
			e.consume();
		});

		return _scene;
	}

	public void setSizeAndPosition(Stage _stage) {
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

		_stage.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 340);
		_stage.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() - 260);
		_stage.setWidth(240);
		_stage.setHeight(160);

	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setAlwaysOnTop(true);

		setSizeAndPosition(primaryStage);

		Scene scene = defineScene();
/*
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		scene.getRoot().setStyle("-fx-background-color: transparent;");
		scene.setFill(Color.TRANSPARENT);
*/
		final Vector2f dragDelta = new Vector2f();
		scene.getRoot().setOnMousePressed(mouseEvent -> {
			dragDelta.x = (float) (primaryStage.getX() - mouseEvent.getScreenX());
			dragDelta.y = (float) (primaryStage.getY() - mouseEvent.getScreenY());
			scene.getRoot().toFront();
		});
		scene.getRoot().setOnMouseDragged(mouseEvent -> {
			primaryStage.setX(mouseEvent.getScreenX() + dragDelta.x);
			primaryStage.setY(mouseEvent.getScreenY() + dragDelta.y);
		});
		
		addDragAndDropToScene(scene);

		primaryStage.setTitle("XS - Noemie Parser");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
