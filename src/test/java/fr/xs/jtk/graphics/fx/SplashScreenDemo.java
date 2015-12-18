package fr.xs.jtk.graphics.fx;

import fr.xs.jtk.graphics.fx.api.splash.ComputeScreen;
import fr.xs.jtk.graphics.fx.api.splash.ProgressScreen;
import fr.xs.jtk.graphics.fx.api.splash.SplashScreen;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SplashScreenDemo extends Application {
	enum Test { gauge, progress, think };
	static Test test = Test.progress;

	static SplashScreen splash = null;

	public static void main(String[] args) {
		new Thread(() -> SplashScreenDemo.launch(SplashScreenDemo.class)).start();

		try { Thread.sleep(1500); } catch(InterruptedException e) { }

		switch(test) {
		case gauge: 	progress(); break;
		case progress: 	progress(); break;
		case think: 	think(); break;
		}
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.initStyle(StageStyle.UNDECORATED);

		switch(test) {
		case gauge: splash = new ProgressScreen(); break;
		case progress: splash = new ProgressScreen(); break;
		case think: splash = new ComputeScreen(); break;
		}

		primaryStage.setScene(new Scene(new Group(), 1280, 768));
		// primaryStage.show();
	}

	public static void progress() {
		Platform.runLater(() -> splash.show());

		ProgressScreen progression = (ProgressScreen) splash;

		progression.setProgression(0);
		while(progression.getProgression() < 100) {
			progression.increaseProgression(1);

			if(progression.getProgression() < 101) progression.setTail("Conclude");
			if(progression.getProgression() <  95) progression.setTail("Take time to make it real");
			if(progression.getProgression() <  70) progression.setTail("Finalize");
			if(progression.getProgression() <  55) progression.setTail("Configure");
			if(progression.getProgression() <  35) progression.setTail("Copy");
			if(progression.getProgression() <  25) progression.setTail("Decompression");
			if(progression.getProgression() <   5) progression.setTail("Preparation phase");

			try { Thread.sleep(100); } catch(InterruptedException e) { }
		}

		Platform.runLater(() -> splash.hide());
	}
	public static void think() {
		ComputeScreen progression = (ComputeScreen) splash;

		progression.setSnakeSize(10);
		
		progression.startThinking();
		try { Thread.sleep(5000); } catch(InterruptedException e) { }
		progression.stopThinking();
	}

}