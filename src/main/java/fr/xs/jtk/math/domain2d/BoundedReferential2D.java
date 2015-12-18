package fr.xs.jtk.math.domain2d;

import fr.xs.jtk.math.type.BoundaryBox;
import fr.xs.jtk.math.type.geom.Point2D;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.BoundingBox;

public class BoundedReferential2D {
	BoundingBox    sceneFX, scene2D, max2D;
	DoubleProperty curScale, maxScale;

	public BoundedReferential2D(BoundaryBox _objFX, BoundaryBox _objMAX, double _maxScale) {
		sceneFX      = new BoundingBox(0, 0, _objFX.getWidth(), _objFX.getHeight());
		max2D        = new BoundingBox(_objMAX.getMinX(), _objMAX.getMinY(), _objMAX.getWidth(), _objMAX.getHeight());
		scene2D      = new BoundingBox(_objMAX.getMinX(), _objMAX.getMinY(), _objMAX.getWidth(), _objMAX.getHeight());
		maxScale     = new SimpleDoubleProperty(_maxScale);
		curScale     = new SimpleDoubleProperty(1.0);
		maxScale.addListener((_obs, _old, _new) -> setScaleMax(maxScale.getValue()) );
		curScale.addListener((_obs, _old, _new) -> setScale(curScale.getValue()) ); 
	}

	public BoundingBox getBoundBoxFX() {
		return sceneFX;
	}
	public void setBoundBoxFX(BoundaryBox _objFX) {
		sceneFX = new BoundingBox(0, 0, _objFX.getWidth(), _objFX.getHeight());
	}
	public BoundingBox getBoundBox2D() {
		return scene2D;
	}
	public void setBoundBox2DMax(BoundaryBox _objMAX) {
		max2D   = new BoundingBox(_objMAX.getMinX(), _objMAX.getMinY(), _objMAX.getWidth(), _objMAX.getHeight());
	}
	public BoundingBox getBoundBoxMAX() {
		return max2D;
	}

	public void slipX(double _right) {
		scene2D = new BoundingBox(scene2D.getMinX() + _right, scene2D.getMinY(), scene2D.getWidth(), scene2D.getHeight());
	}
	public void slipY(double _top) {
		scene2D = new BoundingBox(scene2D.getMinX(), scene2D.getMinY() + _top, scene2D.getWidth(), scene2D.getHeight());
	}

	public DoubleProperty getScale() {
		return curScale;
	}
	public void setScale(double _scale) {
		if(_scale < 1 || _scale > maxScale.getValue())
			return ;

		curScale.set(_scale);

		double  xc = scene2D.getMinX() + scene2D.getWidth() / 2.0,
				yc = scene2D.getMinY() + scene2D.getHeight() / 2.0,
				nw = max2D.getWidth() / _scale,
				nh = max2D.getHeight() / _scale;
		scene2D = new BoundingBox(xc - nw / 2.0, yc - nh / 2.0, nw, nh);
	}
	public DoubleProperty getMaxScale() {
		return maxScale;
	}
	public void setScaleMax(double _max) {
		maxScale.set(_max);
	}

	public void moveTo(Point2D _position) {
		if(!max2D.contains(_position.x, _position.y))
			return ;
		
		double w = scene2D.getWidth(), h = scene2D.getHeight();
		double  minX = _position.x - w / 2.0 > max2D.getMinX() ? _position.x - w / 2.0 : max2D.getMinX(),
				maxX = _position.x + w / 2.0 < max2D.getMaxX() ? _position.x + w / 2.0 : max2D.getMaxX(),
				minY = _position.y - h / 2.0 > max2D.getMinY() ? _position.y - h / 2.0 : max2D.getMinY(),
				maxY = _position.y + h / 2.0 < max2D.getMaxY() ? _position.y + h / 2.0 : max2D.getMaxY();
		
		scene2D = new BoundingBox(minX, minY, maxX - minX, maxY - minY);
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
		return getPointInFX(_p.x, _p.y);
	}

	public Point2D getPointIn2D(double _x, double _y) {
		double 	sx = scene2D.getWidth()  / sceneFX.getWidth(),
				sy = scene2D.getHeight() / sceneFX.getHeight();
		double 	x0 = sceneFX.getMinX() * sx, 
				y0 = sceneFX.getMinY() * sy;

		return new Point2D((float) (-x0 + _x * sx), (float) (sceneFX.getHeight() - (-y0 + _y * sy)));
	}
	public Point2D getPointIn2D(Point2D _p) {
		return getPointIn2D(_p.x, _p.y);
	}
	public Point2D getVectorIn2D(double _x, double _y) {
		double 	sx = scene2D.getWidth()  / sceneFX.getWidth(),
				sy = scene2D.getHeight() / sceneFX.getHeight();

		return new Point2D((float) (_x * sx), (float) (_y * sy));
	}
	public Point2D getVectorIn2D(Point2D _p) {
		return getVectorIn2D(_p.x, _p.y);
	}
	
	public String toString() {
		return  "FX: min=(" + sceneFX.getMinX() + ", " + sceneFX.getMinY() + "), max(=" + sceneFX.getMaxX() + ", " + sceneFX.getMaxY() + ")\n" +
				"2D: min=(" + scene2D.getMinX() + ", " + scene2D.getMinY() + "), max(=" + scene2D.getMaxX() + ", " + scene2D.getMaxY() + ")\n" +
				"MX: min=(" + max2D.getMinX() + ", " + max2D.getMinY() + "), max(=" + max2D.getMaxX() + ", " + max2D.getMaxY() + ")\n" +
				curScale;
	}

}
