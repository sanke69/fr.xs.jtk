package fr.xs.jtk.math.domain2d;

import fr.xs.jtk.math.type.BoundaryBox;
import fr.xs.jtk.math.type.geom.Point2D;

public class Referential2D {
	BoundaryBox sceneFX, scene2D;

	public Referential2D(BoundaryBox _objFX, BoundaryBox _obj2D) {
		sceneFX      = new BoundaryBox(0, 0, _objFX.getWidth(), _objFX.getHeight());
		scene2D      = new BoundaryBox(_obj2D.getMinX(), _obj2D.getMinY(), _obj2D.getWidth(), _obj2D.getHeight());
	}

	public BoundaryBox getBoundBoxFX() {
		return sceneFX;
	}
	public void setBoundBoxFX(BoundaryBox _objFX) {
		sceneFX = _objFX;
	}
	public BoundaryBox getBoundBox2D() {
		return scene2D;
	}
	public void setBoundBox2D(BoundaryBox _obj2D) {
		scene2D = _obj2D;
	}

	public void slipX(double _right) {
		scene2D = new BoundaryBox(scene2D.getMinX() + _right, scene2D.getMinY(), scene2D.getWidth(), scene2D.getHeight());
	}
	public void slipY(double _top) {
		scene2D = new BoundaryBox(scene2D.getMinX(), scene2D.getMinY() + _top, scene2D.getWidth(), scene2D.getHeight());
	}

	public void scaleX(double _ratio) {
		scene2D = new BoundaryBox(scene2D.getMinX() * _ratio, scene2D.getMinY(), scene2D.getWidth() * _ratio, scene2D.getHeight());
	}
	public void scaleY(double _ratio) {
		scene2D = new BoundaryBox(scene2D.getMinX(), scene2D.getMinY() * _ratio, scene2D.getWidth(), scene2D.getHeight() * _ratio);
	}

	public void moveTo(Point2D _position) {		
		double w = scene2D.getWidth(), h = scene2D.getHeight();
		double  minX = _position.x - w / 2.0,
				maxX = _position.x + w / 2.0,
				minY = _position.y - h / 2.0,
				maxY = _position.y + h / 2.0;
		
		scene2D = new BoundaryBox(minX, minY, maxX - minX, maxY - minY);
	}

	public Point2D getPointInFX(double _x, double _y) {
		double 	sx = sceneFX.getWidth()  / scene2D.getWidth(),
				sy = sceneFX.getHeight() / scene2D.getHeight();
		double 	x0 = scene2D.getMinX() * sx, 
				y0 = scene2D.getMinY() * sy;

		return new Point2D((float) (-x0 + _x * sx), (float) (sceneFX.getHeight() - (-y0 + _y * sy)));
	}
	public Point2D getPointInFX(Point2D _p) {
		return getPointInFX(_p.x, _p.y);
	}
	public Point2D getVectorInFX(double _x, double _y) {
		double 	sx = sceneFX.getWidth()  / scene2D.getWidth(),
				sy = sceneFX.getHeight() / scene2D.getHeight();

		return new Point2D((float) (_x * sx), (float) (_y * sy));
	}
	public Point2D getVectorInFX(Point2D _p) {
		return getVectorInFX(_p.x, _p.y);
	}

	public Point2D getPointIn2D(double _x, double _y) {
		double 	sx = scene2D.getWidth()  / sceneFX.getWidth(),
				sy = scene2D.getHeight() / sceneFX.getHeight();
		double 	x0 = sceneFX.getMinX() * sx, 
				y0 = sceneFX.getMinY() * sy;

		return new Point2D((float) (-x0 + _x * sx), (float) (sceneFX.getHeight() - (-y0 + _y * sy)));
	}
	public Point2D getPointIn2D(Point2D _p) {
		return getPointInFX(_p.x, _p.y);
	}
	public Point2D getVectorIn2D(double _x, double _y) {
		double 	sx = scene2D.getWidth()  / sceneFX.getWidth(),
				sy = scene2D.getHeight() / sceneFX.getHeight();

		return new Point2D((float) (_x * sx), (float) (_y * sy));
	}
	public Point2D getVectorIn2D(Point2D _p) {
		return getVectorInFX(_p.x, _p.y);
	}

}
