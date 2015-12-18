package fr.xs.jtk.graphics.fx3d.test;

import fr.xs.jtk.graphics.fx3d.base.TestApplication3DPattern;
import fr.xs.jtk.graphics.fx3d.shapes.primitives.SurfacePlotMesh;
import fr.xs.jtk.graphics.fx3d.utils.Patterns.CarbonPatterns;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.CullFace;

public class Function2DPlotTest extends TestApplication3DPattern {

	@Override
	public void configure() {
		getScene().setFill(Color.WHITESMOKE);
		getCamera().setTranslateZ(-30);
	};

	@Override
	public Group testObject3D() {
		Group group = new Group();

		SurfacePlotMesh surface = new SurfacePlotMesh(p -> Math.sin(p.magnitude() + 0.00000000000000001) / (p.magnitude() + 0.00000000000000001), 10, 10, 10, 10, 5d);
		// PhongMaterial matTorus = new PhongMaterial(Color.FIREBRICK);
		// torus.setMaterial(matTorus);
		// surface.setDrawMode(DrawMode.LINE);
		surface.setCullFace(CullFace.NONE);
		// NONE
		// surface.setTextureModeNone(Color.FORESTGREEN);
		// IMAGE
		// torus.setTextureModeImage(getClass().getResource("res/grid1.png").toExternalForm());
		// banner.setTextureModeImage(getClass().getResource("res/Duke3DprogressionSmall.jpg").toExternalForm());
		// PATTERN
		surface.setTextureModePattern(CarbonPatterns.CARBON_KEVLAR, 1.0d);
		// DENSITY
		// surface.setTextureModeVertices3D(1530,dens);
		// FACES
		// torus.setTextureModeFaces(256*256);

		group.getChildren().addAll(surface);

		return group;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
