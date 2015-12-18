package fr.xs.jtk.graphics.fx2d.controls;

import javafx.geometry.BoundingBox;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.FlowPane;
 
public class Curve2DView extends FlowPane {
	Canvas          canvas;
    GraphicsContext gc2;

	BoundingBox displayArea;

	double[]  x, y;

	public Curve2DView(int _w, int _h) {
		super();
		displayArea = new BoundingBox(0, -1, 1, 1);

		setPrefSize(_w, _h);

		canvas = new Canvas(_w, _h);
		gc2 = canvas.getGraphicsContext2D();

		getChildren().addAll(canvas);
	}
	public Curve2DView setDrawDomain(double _x_min, double _y_min, double _x_max, double _y_max) {
		displayArea = new BoundingBox(_x_min, _y_min, _x_max - _x_min, _y_max - _y_min);
		return this;
	}
	public void setCurve(double[] _x, double[] _y) {
		int n = _x.length == _y.length ? _x.length : -1;
		if(n == -1)
			return ;

		x = _x.clone();
		y = _y.clone();

        drawCurve(gc2);
	}
	

	public void drawCurve(GraphicsContext gc) {
		double sx = canvas.getWidth()  / (displayArea.getMaxX() - displayArea.getMinX()),
			   sy = canvas.getHeight() / (displayArea.getMaxY() - displayArea.getMinY());
		
		gc.clearRect(0, 0, getWidth(), getHeight());

		double x0 = 0.0, y0 = 0.0;
		
		if((displayArea.getMinX() < 0) && (displayArea.getMaxX() > 0))
			x0 = - displayArea.getMinX() * sx;

		if((displayArea.getMinY() < 0) && (displayArea.getMaxY() > 0))
			y0 = - displayArea.getMinY() * sy; 

		double xa = 0.0, xb = 0.0, ya = 0.0, yb = 0.0;
		for(int i = 0; i < x.length - 1; ++i) {
			xa = x0 + x[i] * sx;
			ya = y0 + y[i] * sy;
			xb = x0 + x[i + 1] * sx;
			yb = y0 + y[i + 1] * sy;

            gc.strokeLine(xa, canvas.getHeight() - ya, xb, canvas.getHeight() - yb);
		}
		
	}

}
