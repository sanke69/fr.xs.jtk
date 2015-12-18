package fr.xs.jtk.graphics.fx3d.test;

import java.util.ArrayList;
import java.util.function.Function;

import fr.xs.jtk.graphics.fx3d.base.TestApplication3DPattern;
import fr.xs.jtk.graphics.fx3d.shapes.complex.cloth.ClothMesh;
import fr.xs.jtk.graphics.fx3d.shapes.composites.PolyLine3D;
import fr.xs.jtk.math.type.geom.Point3D;
import javafx.scene.Group;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;

public class ClothMeshTest extends TestApplication3DPattern {

	private Function<Point3D, Number> dens = p -> p.x * p.y * p.z;
	long lastEffect;

	@Override
	public void configure() {
		// getCamera().setTranslateZ(-30);
	};

	@Override
	public Group testObject3D() {
		Group group = new Group();

		ClothMesh cloth = new ClothMesh();        
        cloth.setPerPointMass(10);
        cloth.setBendStrength(0.5);
        cloth.setStretchStrength(1.0);
        cloth.setShearStrength(0.55);
        cloth.setDrawMode(DrawMode.LINE);
        cloth.setCullFace(CullFace.NONE);
//        cloth.setDiffuseMap(new Image("https://kenai.com/attachments/wiki_images/duke/Duke3DprogressionSmall.jpg"));
        cloth.setSpecularPower(5);

        PointLight light2 = new PointLight(Color.GAINSBORO);
        light2.setTranslateZ(-1500);
        PointLight light3 = new PointLight(Color.AZURE);
        light3.setTranslateZ(2500);
        Group g = new Group();
        g.getChildren().addAll(cloth, light2, light3);

        group.getChildren().add(g);
		group.setPickOnBounds(false);

        cloth.startSimulation();

		return group;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
