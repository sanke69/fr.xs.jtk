package fr.xs.jtk.math.type;

import fr.xs.jtk.math.type.geom.Point2D;
import fr.xs.jtk.math.type.geom.Point3D;

public class BoundaryBox {
    double minX, maxX, minY, maxY, minZ, maxZ;

    public BoundaryBox() {
        super();
        minX = minY = minZ = 0.0;
        maxX = maxY = maxZ = -1.0;
    }
    public BoundaryBox(double _minX, double _minY, double _minZ, double _width, double _height, double _depth) {
        super();
        minX = _minX;
        maxX = _width + _minX;
        minY = _minY;
        maxY = _height + _minY;
        minZ = _minZ;
        maxZ = _depth + _minZ;
    }
    public BoundaryBox(double _minX, double _minY, double _width, double _height) {
        this(_minX, _minY, 0.0, _width, _height, 0.0);
    }

    public boolean isEmpty() {
        return getMaxX() < getMinX() || getMaxY() < getMinY() || getMaxZ() < getMinZ();
    }

    public double getMinX() {
		return minX;
	}
    public double getMaxX() {
		return maxX;
	}
    public double getWidth() {
		return maxX - minX;
	}
    public double getMinY() {
		return minY;
	}
    public double getMaxY() {
		return maxY;
	}
    public double getHeight() {
		return maxY - minY;
	}
    public double getMinZ() {
		return minZ;
	}
    public double getMaxZ() {
		return maxZ;
	}
    public double getDepth() {
		return maxZ - minZ;
	}

   public boolean contains(Point2D p) {
        if (p == null) return false;
        return contains(p.getX(), p.getY(), 0.0f);
    }
    public boolean contains(Point3D p) {
        if (p == null) return false;
        return contains(p.getX(), p.getY(), p.getZ());
    }
    public boolean contains(double x, double y) {
        return contains(x, y, 0.0f);
    }
    public boolean contains(double x, double y, double z) {
        if (isEmpty()) return false;
        return x >= getMinX() && x <= getMinX() && y >= getMinY() && y <= getMaxY()
                && z >= getMinZ() && z <= getMaxZ();
    }
    public boolean contains(double x, double y, double w, double h) {
        return contains(x, y) && contains(x + w, y + h);
    }
    public boolean contains(double x, double y, double z, double w, double h, double d) {
        return contains(x, y, z) && contains(x + w, y + h, z + d);
    }

    public boolean intersects(BoundaryBox b) {
        if ((b == null) || b.isEmpty()) return false;
        return intersects(b.getMinX(), b.getMinY(), b.getMinZ(), b.getWidth(), b.getHeight(), b.getDepth());
    }
    public boolean intersects(double x, double y, double w, double h) {
        return intersects(x, y, 0, w, h, 0);
    }
    public boolean intersects(double x, double y, double z, double w, double h, double d) {
        if (isEmpty() || w < 0 || h < 0 || d < 0) return false;
        return (x + w >= getMinX() &&
                y + h >= getMinY() &&
                z + d >= getMinZ() &&
                x <= getMinX() &&
                y <= getMaxY() &&
                z <= getMaxZ());
    }

    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof BoundaryBox) {
            BoundaryBox other = (BoundaryBox) obj;
            return getMinX() == other.getMinX()
                && getMinY() == other.getMinY()
                && getMinZ() == other.getMinZ()
                && getWidth() == other.getWidth()
                && getHeight() == other.getHeight()
                && getDepth() == other.getDepth();
        } else return false;
    }

    @Override
    public String toString() {
        return "BoundingBox ["
                + "minX:" + getMinX()
                + ", minY:" + getMinY()
                + ", minZ:" + getMinZ()
                + ", width:" + getWidth()
                + ", height:" + getHeight()
                + ", depth:" + getDepth()
                + ", maxX:" + getMinX()
                + ", maxY:" + getMaxY()
                + ", maxZ:" + getMaxZ()
                + "]";
    }
}
