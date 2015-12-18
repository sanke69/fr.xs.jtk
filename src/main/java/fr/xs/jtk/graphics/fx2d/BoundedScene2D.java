package fr.xs.jtk.graphics.fx2d;

import fr.xs.jtk.math.domain2d.BoundedReferential2D;
import fr.xs.jtk.math.domain2d.Referential2D;
import fr.xs.jtk.math.type.BoundaryBox;
import fr.xs.jtk.math.type.geom.Point2D;
import javafx.geometry.BoundingBox;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class BoundedScene2D extends FlowPane {

	Canvas canvas;
	GraphicsContext gc2d = null;
	BoundedReferential2D r2d;

	public BoundedScene2D(double _width, double _height) {
		super();

		setPrefSize(_width, _height);
		setStyle("-fx-background-color: rgba(69,169,69,0.85);");

		canvas = new Canvas(_width, _height);
		gc2d   = canvas.getGraphicsContext2D();
		r2d    = new BoundedReferential2D(new BoundaryBox(0, 0, canvas.getWidth(), canvas.getHeight()), new BoundaryBox(0.0, 0.0, 1.0, 1.0), 10);

		getChildren().addAll(canvas);
	}

	public Node parent() {
		return this;
	}

	public Canvas canvas() {
		return canvas;
	}
	public GraphicsContext graphicContext() {
		return gc2d;
	}
	public BoundedReferential2D referential() {
		return r2d;
	}

	public void clear() {
		r2d.setBoundBoxFX(new BoundaryBox(0, 0, canvas.getWidth(), canvas.getHeight()));
		gc2d.clearRect(0, 0, getWidth(), getHeight());
	}

	public void setFill(Color _c) {
		gc2d.setFill(_c);
	}

	public void setStroke(Color _c) {
		gc2d.setStroke(_c);
	}

	public void setLineWidth(double _d) {
		gc2d.setLineWidth(_d);
	}

	public void strokeLine(double _xa, double _ya, double _xb, double _yb) {
		Point2D a = r2d.getPointInFX(_xa, _ya), b = r2d.getPointInFX(_xb, _yb);
		gc2d.strokeLine(a.x, a.y, b.x, b.y);
	}

	public void fillOval(double _xa, double _ya, double _xb, double _yb) {
		Point2D a = r2d.getPointInFX(_xa, _ya), b = r2d.getPointInFX(_xb, _yb);
		gc2d.fillOval(a.x, a.y, b.x, b.y);
	}

	public void strokeOval(double _xa, double _ya, double _xb, double _yb) {
		Point2D a = r2d.getPointInFX(_xa, _ya), b = r2d.getPointInFX(_xb, _yb);
		gc2d.strokeOval(a.x, a.y, b.x, b.y);
	}

	public void fillRect(double _xa, double _ya, double _xb, double _yb) {
		Point2D a = r2d.getPointInFX(_xa, _ya), b = r2d.getPointInFX(_xb, _yb);
		gc2d.fillRoundRect(a.x, a.y, b.x, b.y, 0, 0);
	}

	public void strokeRect(double _xa, double _ya, double _xb, double _yb) {
		Point2D a = r2d.getPointInFX(_xa, _ya), b = r2d.getPointInFX(_xb, _yb);
		gc2d.strokeRoundRect(a.x, a.y, b.x, b.y, 0, 0);
	}

	public void fillRoundRect(double _xa, double _ya, double _xb, double _yb, double _aw, double _ah) {
		Point2D a = r2d.getPointInFX(_xa, _ya), b = r2d.getPointInFX(_xb, _yb);
		gc2d.fillRoundRect(a.x, a.y, b.x, b.y, _aw, _ah);
	}

	public void strokeRoundRect(double _xa, double _ya, double _xb, double _yb, double _aa, double _ab) {
		Point2D a = r2d.getPointInFX(_xa, _ya), b = r2d.getPointInFX(_xb, _yb);
		gc2d.strokeRoundRect(a.x, a.y, b.x, b.y, _aa, _ab);
	}

	public void fillArc(double _xa, double _ya, double _xb, double _yb, double _aw, double _ah, int _mode) {
		Point2D a = r2d.getPointInFX(_xa, _ya), b = r2d.getPointInFX(_xb, _yb);
		switch(_mode) {
		case 1:
			gc2d.fillArc(a.x, a.y, b.x, b.y, _aw, _ah, ArcType.OPEN);
			break;
		case 2:
			gc2d.fillArc(a.x, a.y, b.x, b.y, _aw, _ah, ArcType.CHORD);
			break;
		case 3:
			gc2d.fillArc(a.x, a.y, b.x, b.y, _aw, _ah, ArcType.ROUND);
			break;
		}

	}

	public void strokeArc(double _xa, double _ya, double _xb, double _yb, double _aw, double _ah, int _mode) {
		Point2D a = r2d.getPointInFX(_xa, _ya), b = r2d.getPointInFX(_xb, _yb);
		gc2d.strokeArc(a.x, a.y, b.x, b.y, _aw, _ah, ArcType.OPEN);
		switch(_mode) {
		case 1:
			gc2d.strokeArc(a.x, a.y, b.x, b.y, _aw, _ah, ArcType.OPEN);
			break;
		case 2:
			gc2d.strokeArc(a.x, a.y, b.x, b.y, _aw, _ah, ArcType.CHORD);
			break;
		case 3:
			gc2d.strokeArc(a.x, a.y, b.x, b.y, _aw, _ah, ArcType.ROUND);
			break;
		}
	}

	public void fillPolygon(double[] _x, double[] _y) {
		int n = _x.length;
		double[] x = new double[n];
		double[] y = new double[n];

		for(int i = 0; i < n; ++i) {
			Point2D a = r2d.getPointInFX(_x[i], _y[i]);
			x[i] = a.x;
			y[i] = a.y;
		}

		gc2d.fillPolygon(x, y, n);
	}

	public void strokePolygon(double[] _x, double[] _y) {
		int n = _x.length;
		double[] x = new double[n];
		double[] y = new double[n];

		for(int i = 0; i < n; ++i) {
			Point2D a = r2d.getPointInFX(_x[i], _y[i]);
			x[i] = a.x;
			y[i] = a.y;
		}

		gc2d.strokePolygon(x, y, n);
	}

	public void strokePolyline(double[] _x, double[] _y) {
		int n = _x.length;
		double[] x = new double[n];
		double[] y = new double[n];

		for(int i = 0; i < n; ++i) {
			Point2D a = r2d.getPointInFX(_x[i], _y[i]);
			x[i] = a.x;
			y[i] = a.y;
		}

		gc2d.strokePolyline(x, y, n);
	}

}
