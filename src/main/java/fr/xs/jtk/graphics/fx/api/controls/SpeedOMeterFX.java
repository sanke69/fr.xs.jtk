package fr.xs.jtk.graphics.fx.api.controls;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SpeedOMeterFX extends Application {

	private final static double ZERO_ANGLE  = (- 0.2) * Math.PI;
	private final static double RANGE_ANGLE = 1.5 * Math.PI;
	private final static double MIN_SPEED = 0;
	private final static double MAX_SPEED = 200;

	int width, height;

	private void drawBG(GraphicsContext gc) {
		int cx = width / 2;
		int cy = height / 2;
		double nmark = (MAX_SPEED - MIN_SPEED) / 10;

    	gc.setFill(Color.BLACK);
    	gc.fillOval(0, 0, width, height);
    	gc.setFill(Color.WHITE);

    	int rx = (int) (0.9 * (((double) width) / 2.0));
    	int ry = (int) (0.9 * (((double) height) / 2.0));
    	gc.fillOval(cx - rx, cy - ry, 2 * rx, 2 * ry);

    	gc.setFill(Color.GRAY);
    	for (int i = 0; i < nmark; i++) {
    		double SpeedMarker = (double) i;
    		double angle = ZERO_ANGLE - RANGE_ANGLE * ((double) (SpeedMarker / nmark));
    		double rx0, ry0, rx1, ry1;
    		int     x0,  y0,  x1,  y1;

    		if(i % 5 == 0) {
				rx0 = (0.55 * (((double) width) / 2.0));
				ry0 = (0.55 * (((double) height) / 2.0));
				rx1 = (0.85 * (((double) width) / 2.0));
				ry1 = (0.85 * (((double) height) / 2.0));
    		} else {
				rx0 = (0.75 * (((double) width) / 2.0));
				ry0 = (0.75 * (((double) height) / 2.0));
				rx1 = (0.85 * (((double) width) / 2.0));
				ry1 = (0.85 * (((double) height) / 2.0));
    		}

    		x0 = (int) (rx0 * Math.sin(angle) + (double) cx);
    		y0 = (int) (ry0 * Math.cos(angle) + (double) cy);
    		x1 = (int) (rx1 * Math.sin(angle) + (double) cx);
    		y1 = (int) (ry1 * Math.cos(angle) + (double) cy);

    		gc.setLineWidth(2.0f);
    		gc.strokeLine(x0, y0, x1, y1);
    	}
	}
	
	public void drawHand(GraphicsContext gc, double speed) {
		int cx = width / 2;
		int cy = height / 2;

	    gc.setFill(Color.BLACK);

	    double Needle_angle = getRadFromSpeed(speed);

	    int BigAxeX = (int) (0.8 * (((double) width) / 2.0));
	    int BigAxeY = (int) (0.8 * (((double) height) / 2.0));
	    int SmallAxeX = (int) (0.05 * (((double) width) / 2.0));
	    int SmallAxeY = (int) (0.05 * (((double) height) / 2.0));

	    int Nx = (int) ((double) BigAxeX * Math.sin(Needle_angle) + (double) cx);
	    int Ny = (int) ((double) BigAxeY * Math.cos(Needle_angle) + (double) cy);
	    int Sx = (int) ((double) SmallAxeX * Math.sin(Needle_angle - Math.PI) + (double) cx);
	    int Sy = (int) ((double) SmallAxeX * Math.cos(Needle_angle - Math.PI) + (double) cy);
	    int Ex = (int) ((double) SmallAxeX * Math.sin(Needle_angle - Math.PI/2.0) + (double) cx);
	    int Ey = (int) ((double) SmallAxeY * Math.cos(Needle_angle - Math.PI/2.0) + (double) cy);
	    int Wx = (int) ((double) SmallAxeX * Math.sin(Needle_angle + Math.PI/2.0) + (double) cx);
	    int Wy = (int) ((double) SmallAxeY * Math.cos(Needle_angle + Math.PI/2.0) + (double) cy);

	    gc.setFill(Color.RED);
	    gc.fillPolygon(new double[] { Nx, Ex, Sx, Wx }, new double[] { Ny, Ey, Sy, Wy }, 4);

	}

	private double getRadFromSpeed(double _speed) {
		if(_speed < MIN_SPEED)
			return ZERO_ANGLE;
		if(_speed > MAX_SPEED)
			return ZERO_ANGLE - RANGE_ANGLE;

		double RAD = ZERO_ANGLE - RANGE_ANGLE * _speed / (MAX_SPEED - MIN_SPEED);
		return RAD;
	}

    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Drawing Operations Test");
        Group root = new Group();
        Canvas canvas = new Canvas(width = 300, height = 250);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawBG(gc);
        drawHand(gc, 5);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
