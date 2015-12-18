package fr.xs.jtk.graphics.fx3d.cameras.cameras;

import fr.xs.jtk.graphics.fx3d.cameras.base.AdvancedCamera;
import fr.xs.jtk.math.type.Quaternion;
import fr.xs.jtk.math.type.Vector3f;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;

public class QuaternionCamera extends AdvancedCamera {
	private final Vector3f position = new Vector3f(), backAxis = new Vector3f(), rightAxis = new Vector3f(), upAxis = new Vector3f();

	public QuaternionCamera() {
		super();

		PointLight light = new PointLight(Color.WHITE);
		getChildren().add(light);

		lookAt(0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
	}

	public void lookAt(final Vector3f _from, final Vector3f _to, final Vector3f _up) {
		position.set(_from);
		lookAt(_to, _up);
	}
	public void lookAt(final Vector3f _target, final Vector3f _up) {
		backAxis.set(_target.minus(position).normalize());
		rightAxis.set(Vector3f.cross(backAxis, _up).normalize());
		upAxis.set(Vector3f.cross(rightAxis, backAxis).normalize());
	}

	public void lookAt(float _x, float _y, float _z, float _t_x, float _t_y, float _t_z, float _up_x, float _up_y, float _up_z) {
		position.set(_x, _y, _z);
		lookAt(new Vector3f(_t_x, _t_y, _t_z), new Vector3f(_up_x, _up_y, _up_z));
	}

	public void lookAt(float _t_x, float _t_y, float _t_z, float _up_x, float _up_y, float _up_z) {
		lookAt(new Vector3f(_t_x, _t_y, _t_z), new Vector3f(_up_x, _up_y, _up_z));
	}

	public void headUp(Vector3f _up) {
		_up.normalize();
		backAxis.set(Vector3f.cross(rightAxis, upAxis).normalize());
		rightAxis.set(Vector3f.cross(upAxis, backAxis).normalize());
	}

	public void moveTo(float _x, float _y, float _z) {
		position.set(_x, _y, _z);
	}

	public void moveTo(Vector3f _v) {
		position.set(_v);
	}

	public void move(float _step) {
		backAxis.normalize();
		position.add(backAxis.times(_step));
	}

	public void strafe(float _step) {
		rightAxis.normalize();
		position.add(rightAxis.times(_step));
	}

	public void raise(float _step) {
		upAxis.normalize();
		position.add(upAxis.times(_step));
	}

	public void pitched(float _deg) {
		if(_deg != 0) {
			Quaternion q = new Quaternion(rightAxis, (float) (_deg * Math.PI / 180.0));
			backAxis.set(q.transform(backAxis));
			upAxis.set(q.transform(upAxis));
			backAxis.normalize();
			rightAxis.normalize();
			upAxis.normalize();
		}
	}

	public void rolled(float _deg) {
		if(_deg != 0) {
			Quaternion q = new Quaternion(backAxis, (float) (_deg * Math.PI / 180.0));
			rightAxis.set(q.transform(rightAxis));
			upAxis.set(q.transform(upAxis));
			backAxis.normalize();
			rightAxis.normalize();
			upAxis.normalize();
		}
	}

	public void yawed(float _deg) {
		if(_deg != 0) {
			Quaternion q = new Quaternion(upAxis, (float) (_deg * Math.PI / 180.0));
			rightAxis.set(q.transform(rightAxis));
			backAxis.set(q.transform(backAxis));
			backAxis.normalize();
			rightAxis.normalize();
			upAxis.normalize();
		}
	}

	public void update() {
		affine.setMxx(rightAxis.getX()); 	affine.setMxy(upAxis.getX()); 	affine.setMxz(backAxis.getX());
		affine.setMyx(rightAxis.getY()); 	affine.setMyy(upAxis.getY()); 	affine.setMyz(backAxis.getY());
		affine.setMzx(rightAxis.getZ()); 	affine.setMzy(upAxis.getZ()); 	affine.setMzz(backAxis.getZ());
		affine.setTx(position.getX());
		affine.setTy(position.getY());
		affine.setTz(position.getZ());
	}

}
