
package fr.xs.jtk.math.fx;

import javafx.scene.Group;
import javafx.scene.transform.Affine;
import javafx.scene.transform.MatrixType;
import com.sun.javafx.geom.Vec3d;

public class Pivot extends Group {

	private final double[] idt={1,0,0,0, 0,1,0,0, 0,0,1,0, 0,0,0,1};

	protected Affine matrix = new Affine(idt,MatrixType.MT_3D_4x4,0);

	
	public Pivot() {
		super();
		getTransforms().setAll(matrix);
	}
	
	public Pivot(double x, double y, double z) {
		this();
		matrix.setTx(x);
		matrix.setTy(y);
		matrix.setTz(z);
	}
	
	public void setPosition(double x, double y, double z) {
		matrix.setTx(x);
		matrix.setTy(y);
		matrix.setTz(z);
	}
	
	public Vec3d getPosition() {
		return new Vec3d(matrix.getTx(),matrix.getTy(),matrix.getTz());
	}

	// set to eular rotation retaining translation
	public void setEularRotation(double rx, double ry, double rz) {
		double cx = Math.cos(rx);
        double cy = Math.cos(ry);
        double cz = Math.cos(rz);
        double sx = Math.sin(rx);
        double sy = Math.sin(ry);
        double sz = Math.sin(rz);
		matrix.setMxx(cy*cz);	matrix.setMxy((sx * sy * cz) + (cx * sz));	matrix.setMxz(-(cx * sy * cz) + (sx * sz));
		matrix.setMyx(-cy*sz);	matrix.setMyy(-(sx * sy * sz) + (cx * cz));	matrix.setMyz((cx * sy * sz) + (sx * cz));
		matrix.setMzx(sy);		matrix.setMzy(-sx*cy);						matrix.setMzz(cx*cy);
	}

	// make this pivot face the a point, a bit messed up because came from different axis layout!
    public void lookAt(Vec3d centre, Vec3d up) {

        final Vec3d f = new Vec3d(), s = new Vec3d(), u = new Vec3d();
        final Vec3d t = new Vec3d(), eye = new Vec3d();
        
        eye.set( matrix.getTx(), matrix.getTy(), matrix.getTz());

        f.set(eye);
        f.sub(centre);
        f.normalize();

        up.normalize();

        t.set(f);
        s.cross(t,up);
        s.normalize();

        t.set(s);
        u.cross(f,t);
        u.normalize();
      
        matrix.setMxx( -s.x);	matrix.setMxy( u.x);	matrix.setMxz( -f.x);
        matrix.setMyx( -s.y);    matrix.setMyy( u.y);    matrix.setMyz( -f.y);
        matrix.setMzx( -s.z);    matrix.setMzy( u.z);    matrix.setMzz( -f.z);
    }
	
}
