package fr.xs.jtk.graphics.fx3d.controls;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;

public class Axis3D extends Group {

    private Box xAxis,yAxis,zAxis;
    
	public Axis3D() {
		super();
        
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);
        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);
        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);
        final Sphere red = new Sphere(50);
        red.setMaterial(redMaterial);
        final Sphere blue = new Sphere(50);
        blue.setMaterial(blueMaterial);

        
        xAxis = new Box(24.0, 0.05, 0.05);
        yAxis = new Box(0.05, 24.0, 0.05);
        zAxis = new Box(0.05, 0.05, 24.0);
        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);

        getChildren().addAll(xAxis,yAxis,zAxis);
	}

}
