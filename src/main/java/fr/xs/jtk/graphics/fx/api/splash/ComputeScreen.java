package fr.xs.jtk.graphics.fx.api.splash;

import fr.xs.jtk.graphics.fx.api.controls.gauge.GaugeBar;
import fr.xs.jtk.graphics.fx.api.controls.gauge.GaugeBarSnakeSkin;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class ComputeScreen extends SplashScreen {
	Label text, subtext;
	GaugeBar gaugeBar;
	
	public ComputeScreen() {
		super();
        setScene(scene = content());
	}

    protected Scene content() {
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: transparent;");

        text = new Label();
        text.setText("Installation in progress...");
        text.setStyle( "-fx-font-family: Courier;" + "-fx-font-size: 24;");
		
        subtext = new Label();
        subtext.setText("Preparation phase");
        subtext.setStyle( "-fx-font-family: Courier;" + "-fx-font-size: 18;" + "-fx-font-style: italic;");
        
    	gaugeBar = new GaugeBar(GaugeBar.Style.idle);
    	gaugeBar.setLayoutX(10);
    	gaugeBar.setLayoutY(70);

    	gaugeBar.setValue(10);
    	gaugeBar.setMaxValue(100);

//        borderPane.setTop(text);
//        BorderPane.setAlignment(text, Pos.CENTER);
        borderPane.setCenter(gaugeBar);
//        borderPane.setBottom(subtext);
//        BorderPane.setAlignment(subtext, Pos.CENTER);

        Scene newScene = new Scene(borderPane);
		newScene.setFill(Color.TRANSPARENT);
    	return newScene;
    }

    public int getSnakeSize() {
    	return gaugeBar.getValue();
    }
    public void setSnakeSize(int _percent) {
    	gaugeBar.setValue(_percent);
    }

	public void startThinking() {
		GaugeBarSnakeSkin skin = (GaugeBarSnakeSkin) gaugeBar.getSkin();
		skin.startAnimate();
		Platform.runLater(() -> show());
	}
	public void stopThinking() {
		GaugeBarSnakeSkin skin = (GaugeBarSnakeSkin) gaugeBar.getSkin();
		skin.stopAnimate();
		Platform.runLater(() -> hide());
	}

}
