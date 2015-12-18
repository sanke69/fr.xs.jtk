package fr.xs.jtk.graphics.fx3d.test;

import fr.xs.jtk.graphics.fx3d.base.TestApplication3DPattern;
import javafx.scene.Group;

public class PlanetoidTest extends TestApplication3DPattern {

	@Override
	public void configure() {
		// getCamera().setTranslateZ(-30);
	};

	@Override
	public Group testObject3D() {
		/*
		Planetoid earth = new Planetoid(500);
		Group group = new Group(earth);
		
		earth.animate();

		return group;
		*/
		return null;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
