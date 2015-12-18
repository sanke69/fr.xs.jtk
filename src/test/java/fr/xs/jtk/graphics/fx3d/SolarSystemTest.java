package fr.xs.jtk.graphics.fx3d;

import fr.xs.jtk.beans.universe.AstralCorpseBean;
import fr.xs.jtk.graphics.fx3d.extras.UniverseBox;
import fr.xs.jtk.graphics.fx3d.shapes.Planetoid;
import fr.xs.jtk.graphics.fx3d.utils.PhongPhactory;
import fr.xs.jtk.graphics.fx3d.utils.PlanetoidEnlightment;
import fr.xs.jtk.helpers.MediaHelper;
import fr.xs.jtk.math.fx.Pivot;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;

public class SolarSystemTest extends Application3D {
	public static void main(String[] args) {
		System.out.println("3D supported? " + Platform.isSupported(ConditionalFeature.SCENE3D));
		launch(args);
	}

	@Override
	public void configure() {
		getCamera().lookAt(20000.0f, 20000.0f, 250.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
	}

	@Override
	public Group scene3D() {
		Group group = new Group();

		String[] ac = 
			{ /* Our star   */ "Soleil", 
			  /* Planets    */ "Mercure", "Venus", "Earth", "Mars", "Jupiter", "Saturne", "Uranus", "Neptune", 
			  /* Satellites */ "Moon", "Io", 
			  /* Others     */ "Pluton", };

		Planetoid[] astres = new Planetoid[ac.length];

		int i = 0; double x = 0.0, dx = 20.0;
		for(String planetoid : ac) {
			System.out.println(planetoid);
			
			AstralCorpseBean bean = new AstralCorpseBean();
			bean.load(MediaHelper.getURLForFile("/Solar System/" + planetoid + "/" + planetoid + ".ac"));
			bean.setRadius(10 /*bean.getRadius()*/);
			System.out.println(bean);
			
			final Planetoid corpse = new Planetoid(planetoid,  bean.getRadius());
			PlanetoidEnlightment.setTexture(corpse, "Solar System/" + planetoid + "/" + planetoid + ".png");
			corpse.setTranslateX(x += dx);
//			corpse.animate();
			astres[i++] = corpse;
		}

		Pivot p0 = new Pivot(  0,   0,   0);
		Pivot p1 = new Pivot(100, 100, 100);

		AmbientLight ambient = new AmbientLight(Color.WHITE);

//		PointLight light = new PointLight(Color.WHITE);
//		p1.getChildren().add(light);

		group.getChildren().addAll(ambient);
		group.getChildren().addAll(astres);
//		group.getChildren().addAll(new UniverseBox("redsky", 1e5));

		group.getChildren().addAll(buildAxes(p0, 10), buildAxes(p1, 50));

		return group;
	}

	private Group buildAxes(Pivot p, double _l) {
		Group group = new Group();

		final Box xAxis = new Box(_l, 4, 4);
		final Box yAxis = new Box(4, _l, 4);
		final Box zAxis = new Box(4, 4, _l);

		xAxis.setMaterial(PhongPhactory.fromColour(Color.RED));
		yAxis.setMaterial(PhongPhactory.fromColour(Color.GREEN));
		zAxis.setMaterial(PhongPhactory.fromColour(Color.BLUE));

		p.getChildren().addAll(xAxis, yAxis, zAxis);
		p.setVisible(true);
		group.getChildren().addAll(p);

		return group;
	}

}
