package fr.xs.jtk.graphics.fx.api.controls;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
 
public class CompassFX extends Application {
	int width, height;

	private void drawBG(GraphicsContext gc) {
		int cx = width / 2;
		int cy = height / 2;
		double nmark = 8;

	    	int rx = (int) (0.9 * (((double) width) / 2.0));
	    	int ry = (int) (0.9 * (((double) height) / 2.0));

	        gc.setFill(Color.GREEN);
	    	gc.fillOval(cx - rx, cy - ry, 2 * rx, 2 * ry);

	    	for (int i = 0; i < nmark; i++) {
	    		double CardinalPoint = (double) i;
	    		double angle = 2.0 * Math.PI * (nmark - CardinalPoint) / nmark - Math.PI;
	    		double rx0, ry0, rx1, ry1;
	    		int     x0,  y0,  x1,  y1;

	    		if(i % 2 == 0) {
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

	    		gc.setStroke(Color.BLUE);
	    		gc.strokeLine(x0, y0, x1, y1);
	    	}
	}
	
	public void drawHand(GraphicsContext gc, double degree) {
		int cx = width / 2;
		int cy = height / 2;

	    gc.setFill(Color.BLACK);

	    double North_angle = 2.0 * Math.PI * (360.0 - degree) / 360.0 - Math.PI;
	    double West_angle = 2.0 * Math.PI * (360.0 - degree) / 360.0 - Math.PI / 2.0;
	    double East_angle = 2.0 * Math.PI * (360.0 - degree) / 360.0 + Math.PI / 2.0;
	    double South_angle = 2.0 * Math.PI * (360.0 - degree) / 360.0;

	    int BigAxeX = (int) (0.8 * (((double) width) / 2.0));
	    int BigAxeY = (int) (0.8 * (((double) height) / 2.0));
	    int SmallAxeX = (int) (0.2 * (((double) width) / 2.0));
	    int SmallAxeY = (int) (0.2 * (((double) height) / 2.0));

	    int Nx = (int) ((double) BigAxeX * Math.sin(North_angle) + (double) cx);
	    int Ny = (int) ((double) BigAxeY * Math.cos(North_angle) + (double) cy);
	    int Sx = (int) ((double) BigAxeX * Math.sin(South_angle) + (double) cx);
	    int Sy = (int) ((double) BigAxeY * Math.cos(South_angle) + (double) cy);
	    int Ex = (int) ((double) SmallAxeX * Math.sin(East_angle) + (double) cx);
	    int Ey = (int) ((double) SmallAxeY * Math.cos(East_angle) + (double) cy);
	    int Wx = (int) ((double) SmallAxeX * Math.sin(West_angle) + (double) cx);
	    int Wy = (int) ((double) SmallAxeY * Math.cos(West_angle) + (double) cy);

	    gc.setFill(Color.RED);
	    gc.fillPolygon(new double[] { Nx, Ex, Wx }, new double[] { Ny, Ey, Wy }, 3);
	    gc.setFill(Color.BLACK);
	    gc.fillPolygon(new double[] { Sx, Ex, Wx }, new double[] { Sy, Ey, Wy }, 3);

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

    private void drawShapes(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.strokeLine(40, 10, 10, 40);
        gc.fillOval(10, 60, 30, 30);
        gc.strokeOval(60, 60, 30, 30);
        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
        gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
        gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
        gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
        gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
        gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
        gc.fillPolygon(new double[]{10, 40, 10, 40},
                       new double[]{210, 210, 240, 240}, 4);
        gc.strokePolygon(new double[]{60, 90, 60, 90},
                         new double[]{210, 210, 240, 240}, 4);
        gc.strokePolyline(new double[]{110, 140, 110, 140},
                          new double[]{210, 210, 240, 240}, 4);
    }
}
