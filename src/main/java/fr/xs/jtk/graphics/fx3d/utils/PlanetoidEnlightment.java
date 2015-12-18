package fr.xs.jtk.graphics.fx3d.utils;

import java.net.URL;

import fr.xs.jtk.format.json.JSONObject;
import fr.xs.jtk.format.json.parser.JSONParser;
import fr.xs.jtk.format.json.parser.ParseException;
import fr.xs.jtk.graphics.fx3d.shapes.Planetoid;
import fr.xs.jtk.helpers.MediaHelper;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

public class PlanetoidEnlightment {

	private static final double MAP_WIDTH = 1024; //8192;
	private static final double MAP_HEIGHT = 512; //4096;

	public static void setMaterial(final Planetoid _planetoid, final String _phongFile) {
		final PhongMaterial material = new PhongMaterial();
		
		String diffFile = "", normFile = "", specFile = "";
		try {
			URL url = MediaHelper.getURLForMedia(_phongFile);

			JSONParser parser = new JSONParser();

			MediaHelper.addToSearchPath(url.getPath().substring(0, url.getPath().lastIndexOf('/')));

			Object obj = parser.parse(MediaHelper.getContentAsString(url));
			if(obj == null)
				return;

			JSONObject jsonObject = (JSONObject) obj;

			if((diffFile = (String) jsonObject.get("diffuse")) != null)
				material.setDiffuseMap(new Image(MediaHelper.getURLForFile(diffFile).toExternalForm(), MAP_WIDTH, MAP_HEIGHT, true, true));
			if((normFile = (String) jsonObject.get("normal")) != null)
				material.setBumpMap(new Image(MediaHelper.getURLForFile(normFile).toExternalForm(), MAP_WIDTH, MAP_HEIGHT, true, true));
			if((specFile = (String) jsonObject.get("specular")) != null)
				material.setSpecularMap(new Image(MediaHelper.getURLForFile(specFile).toExternalForm(), MAP_WIDTH, MAP_HEIGHT, true, true));
		} catch(ParseException e) { e.printStackTrace(); }

		_planetoid.setMaterial(material);
	}

	public static void setTexture(final Planetoid _planetoid, final String _textureFile) {
		final Image         texture  = new Image(MediaHelper.getURLForFile(_textureFile).toExternalForm(), MAP_WIDTH, MAP_HEIGHT, true, true);
		final PhongMaterial material = new PhongMaterial();

		material.setDiffuseMap(texture);
//		material.setBumpMap(texture);
//		material.setSpecularMap(texture);
//		material.setSelfIlluminationMap(texture);
//		material.setDiffuseColor(Color.WHITE);
		material.setSpecularColor(Color.WHITE);

		_planetoid.setMaterial(material);
	}

}
