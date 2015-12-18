package fr.xs.jtk.graphics.fx.api.splash;

import fr.xs.jtk.graphics.fx.api.controls.gauge.GaugeBar;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class ProgressScreen extends SplashScreen {
	Label text, subtext;
	GaugeBar gaugeBar;
	
	public ProgressScreen() {
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
        
    	gaugeBar = new GaugeBar(GaugeBar.Style.progress);
    	gaugeBar.setLayoutX(10);
    	gaugeBar.setLayoutY(70);

        borderPane.setTop(text);
        BorderPane.setAlignment(text, Pos.CENTER);
        borderPane.setCenter(gaugeBar);
        borderPane.setBottom(subtext);
        BorderPane.setAlignment(subtext, Pos.CENTER);

        Scene newScene = new Scene(borderPane);
		newScene.setFill(Color.TRANSPARENT);
    	return newScene;
    }

    public void setHead(String _text) {
    	Platform.runLater( () -> text.setText(_text) );
    }
    public int getProgression() {
    	return gaugeBar.getValue();
    }
    public void setProgression(int _percent) {
    	gaugeBar.setValue(_percent);
    }
    public void increaseProgression(int _step) {
    	gaugeBar.setValue(gaugeBar.getValue() + _step);
    }
    public void decreaseProgression(int _step) {
    	gaugeBar.setValue(gaugeBar.getValue() - _step);
    }
    public void setTail(String _text) {
    	Platform.runLater( () -> subtext.setText(_text) );
    }

}
