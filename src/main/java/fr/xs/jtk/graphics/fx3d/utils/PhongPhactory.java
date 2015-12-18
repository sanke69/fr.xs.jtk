package fr.xs.jtk.graphics.fx3d.utils;

import javafx.scene.paint.PhongMaterial;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.image.Image;

public class PhongPhactory {
	
	public static Material fromColour( Color c) {
        final PhongMaterial mat = new PhongMaterial();
        mat.setDiffuseColor(c); // was darker
        mat.setSpecularColor(c.brighter());
        return mat;		
	}
	
	public static Material fromImage( String fileName ) {
		Image img = new Image("file:"+fileName);
		PhongMaterial mat = new PhongMaterial();
		mat.setDiffuseMap(img);
		return mat;		
	}
}
