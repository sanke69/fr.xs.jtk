package fr.xs.jtk.graphics.fx3d.test;

import java.util.Random;

import fr.xs.jtk.graphics.fx3d.controls.Axis3D;
import fr.xs.jtk.graphics.fx3d.extras.CubeWorld;
import fr.xs.jtk.graphics.fx3d.shapes.Capsule;
import fr.xs.jtk.graphics.fx3d.shapes.Cone;
import fr.xs.jtk.graphics.fx3d.shapes.Octahedron;
import fr.xs.jtk.graphics.fx3d.shapes.SphereSegment;
import fr.xs.jtk.graphics.fx3d.shapes.Spheroid;
import fr.xs.jtk.graphics.fx3d.shapes.Torus;
import fr.xs.jtk.graphics.fx3d.shapes.Trapezoid;
import fr.xs.jtk.graphics.fx3d.tools.CubeViewer;
import fr.xs.jtk.graphics.fx3d.tools.VirtualUniverseScene;
import fr.xs.jtk.helpers.MediaHelper;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class ABunchOfMeshTest extends Application {
    private final Image 
    top = new Image(MediaHelper.getURLForMedia("RC/cubemap/dark/darkXN.png").toExternalForm()),
    bottom = new Image(MediaHelper.getURLForMedia("RC/cubemap/dark/darkXP.png").toExternalForm()),
    left = new Image(MediaHelper.getURLForMedia("RC/cubemap/dark/darkYN.png").toExternalForm()),
    right = new Image(MediaHelper.getURLForMedia("RC/cubemap/dark/darkYP.png").toExternalForm()),
    front = new Image(MediaHelper.getURLForMedia("RC/cubemap/dark/darkZN.png").toExternalForm()),
    back = new Image(MediaHelper.getURLForMedia("RC/cubemap/dark/darkZP.png").toExternalForm());

    Group rootUniverse;

	@Override
	public void start(Stage stage) {
		VirtualUniverseScene scene = new VirtualUniverseScene(1280, 768);
		scene.addToUniverse(rootUniverse = populate());

		scene.setOnKeyPressed(event -> {
			KeyCode keycode = event.getCode();
			if(keycode == KeyCode.G) {
				scene.removeFromUniverse(rootUniverse);
				scene.addToUniverse(rootUniverse = populate());
			}
		});

		stage.setTitle("Hello World!");
		stage.setScene(scene);
		stage.show();
		// stage.setFullScreen(true);
		// stage.setFullScreenExitHint("");
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	public Group populate() {
		Group g = new Group();

		double size = 100000D;
//		Skybox skyBox = new Skybox( top, bottom, left, right, front, back, size, camera );

		CubeWorld cubeWorld = new CubeWorld(5000, 500, true);
		CubeViewer cubeViewer = new CubeViewer(5000, 500, true);
		

		final PhongMaterial redMaterial = new PhongMaterial();
		redMaterial.setDiffuseColor(Color.DARKRED);
		redMaterial.setSpecularColor(Color.RED);
		final PhongMaterial greenMaterial = new PhongMaterial();
		greenMaterial.setDiffuseColor(Color.DARKGREEN);
		greenMaterial.setSpecularColor(Color.GREEN);
		final PhongMaterial blueMaterial = new PhongMaterial();
		blueMaterial.setDiffuseColor(Color.DARKBLUE);
		blueMaterial.setSpecularColor(Color.BLUE);

		final Sphere red = new Sphere(10);
		red.setMaterial(redMaterial);
		final Sphere green = new Sphere(20);
		green.setMaterial(greenMaterial);
		final Sphere blue = new Sphere(30);
		blue.setMaterial(blueMaterial);

		g.getChildren().add(aBunchOfCapsules());
		g.getChildren().add(aBunchOfCones());
		g.getChildren().add(aBunchOfOctahedron());
		g.getChildren().add(aBunchOfSphereSegment());
		g.getChildren().add(aBunchOfSpheroid());
		g.getChildren().add(aBunchOfTorus());
		g.getChildren().add(aBunchOfTrapezoid());

//		g.getChildren().add(skyBox);
		g.getChildren().add(cubeWorld);
		g.getChildren().add(cubeViewer);
		g.getChildren().add(new Axis3D());
		g.getChildren().addAll(red, green, blue);

		return g;
	}

	protected static Group aBunchOfCapsules() {
		Group capsuleGroup = new Group();
		for(int i = 0; i < 50; i++) {
			Random r = new Random();
			float randomRadius = (float) ((r.nextFloat() * 100) + 25);
			float randomHeight = (float) ((r.nextFloat() * 300) + 75);
			Color randomColor = new Color(r.nextDouble(), r.nextDouble(), r.nextDouble(), r.nextDouble());

			Capsule cap = new Capsule(randomRadius, randomHeight, randomColor);
			cap.setEmissiveLightingColor(randomColor);
			cap.setEmissiveLightingOn(r.nextBoolean());
			cap.setDrawMode(r.nextBoolean() ? DrawMode.FILL : DrawMode.LINE);

			double translationX = Math.random() * 1024 * 1.95;
			if(Math.random() >= 0.5) {
				translationX *= -1;
			}
			double translationY = Math.random() * 1024 * 1.95;
			if(Math.random() >= 0.5) {
				translationY *= -1;
			}
			double translationZ = Math.random() * 1024 * 1.95;
			if(Math.random() >= 0.5) {
				translationZ *= -1;
			}
			Translate translate = new Translate(translationX, translationY, translationZ);
			Rotate rotateX = new Rotate(Math.random() * 360, Rotate.X_AXIS);
			Rotate rotateY = new Rotate(Math.random() * 360, Rotate.Y_AXIS);
			Rotate rotateZ = new Rotate(Math.random() * 360, Rotate.Z_AXIS);

			cap.getTransforms().addAll(translate, rotateX, rotateY, rotateZ);

			capsuleGroup.getChildren().add(cap);
		}

		return capsuleGroup;
	}

	protected static Group aBunchOfCones() {
        Group coneGroup = new Group();        
        for (int i = 0; i < 100; i++) {
            Random r = new Random();
            //A lot of magic numbers in here that just artificially constrain the math
            float randomRadius = (float) ((r.nextFloat()*100) + 25);
            float randomHeight = (float) ((r.nextFloat()*300)+ 75);
            int randomDivisions = (int) ((r.nextFloat()*50) + 5);
            Color randomColor = new Color(r.nextDouble(), r.nextDouble(), r.nextDouble(), r.nextDouble());
            
            Cone cone = new Cone(randomDivisions, randomRadius, randomHeight, randomColor);               
            cone.setEmissiveLightingColor(randomColor);
            cone.setEmissiveLightingOn(r.nextBoolean());
            cone.setDrawMode(r.nextBoolean() ? DrawMode.FILL : DrawMode.LINE);
            
            double translationX = Math.random() * 1024;
            if (Math.random() >= 0.5) {
                translationX *= -1;
            }
            double translationY = Math.random() * 1024;
            if (Math.random() >= 0.5) {
                translationY *= -1;
            }
            double translationZ = Math.random() * 1024;
            if (Math.random() >= 0.5) {
                translationZ *= -1;
            }
            Translate translate = new Translate(translationX, translationY, translationZ);
            Rotate rotateX = new Rotate(Math.random() * 360, Rotate.X_AXIS);
            Rotate rotateY = new Rotate(Math.random() * 360, Rotate.Y_AXIS);
            Rotate rotateZ = new Rotate(Math.random() * 360, Rotate.Z_AXIS);

            cone.getTransforms().addAll(translate, rotateX, rotateY, rotateZ);
            
            coneGroup.getChildren().add(cone);
        }

		return coneGroup;
	}

	protected static Group aBunchOfSphereSegment() {
		Group sphereGroup = new Group();
        for(int i=0;i<30;i++){
            Random r = new Random();
            //A lot of magic numbers in here that just artificially constrain the math
            float randomRadius = (float) ((r.nextFloat()*150) + 10);
            float randomThetaMax = (float) ((r.nextFloat()*360)+ 1);
            float randomThetaMin = (float) ((r.nextFloat())+ 1);
            if(randomThetaMin > randomThetaMax) {
                float swap = randomThetaMin;
                randomThetaMin = randomThetaMax;
                randomThetaMax = swap;
            }
            float randomPolarMax = (float) ((r.nextFloat()*90)+ 1);
            float randomPolarMin = (float) ((r.nextFloat())+ 1);
            if(randomPolarMin > randomPolarMax) {
                float swap = randomPolarMin;
                randomPolarMin = randomPolarMax;
                randomPolarMax = swap;
            }            
            int randomSegments = (int) ((r.nextFloat()*15) + 5);
            Color randomColor = new Color(r.nextDouble(), r.nextDouble(), r.nextDouble(), r.nextDouble());
            boolean ambientRandom = r.nextBoolean();
            boolean fillRandom = r.nextBoolean();
            
            SphereSegment sphereSegment = new SphereSegment(randomRadius, randomColor,
                Math.toRadians(0), Math.toRadians(360),
                Math.toRadians(randomPolarMin), Math.toRadians(randomPolarMax),
                randomSegments,ambientRandom,fillRandom);

                    
            double translationX = Math.random() * 1024;
            if(Math.random() >= 0.5) translationX *= -1;
            double translationY = Math.random() * 1024;
            if(Math.random() >= 0.5) translationY *= -1;
            double translationZ = Math.random() * 1024;
            if(Math.random() >= 0.5) translationZ *= -1;
            Translate translate = new Translate(translationX, translationY, translationZ);
            Rotate rotateX = new Rotate(Math.random()*360, Rotate.X_AXIS);
            Rotate rotateY = new Rotate(Math.random()*360, Rotate.Y_AXIS);
            Rotate rotateZ = new Rotate(Math.random()*360, Rotate.Z_AXIS);
            
            sphereSegment.getTransforms().addAll(translate,rotateX,rotateY,rotateZ);
            sphereSegment.getTransforms().add(translate);
            sphereGroup.getChildren().add(sphereSegment);

        }

		return sphereGroup;
	}

	protected static Group aBunchOfSpheroid() {

		Group spheroidGroup = new Group();
		for(int i = 0; i < 50; i++) {
			Random r = new Random();
			// A lot of magic numbers in here that just artificially constrain
			// the math
			float randomMajorRadius = (float) ((r.nextFloat() * 300) + 50);
			float randomMinorRadius = (float) ((r.nextFloat() * 300) + 50);
			int randomDivisions = (int) ((r.nextFloat() * 64) + 1);
			Color randomColor = new Color(r.nextDouble(), r.nextDouble(), r.nextDouble(), r.nextDouble());

			Spheroid sm = new Spheroid(randomDivisions, randomMajorRadius, randomMinorRadius, randomColor);
			sm.setDrawMode(DrawMode.LINE);
			double translationX = Math.random() * 1024 * 1.95;
			if(Math.random() >= 0.5) {
				translationX *= -1;
			}
			double translationY = Math.random() * 1024 * 1.95;
			if(Math.random() >= 0.5) {
				translationY *= -1;
			}
			double translationZ = Math.random() * 1024 * 1.95;
			if(Math.random() >= 0.5) {
				translationZ *= -1;
			}
			Translate translate = new Translate(translationX, translationY, translationZ);
			Rotate rotateX = new Rotate(Math.random() * 360, Rotate.X_AXIS);
			Rotate rotateY = new Rotate(Math.random() * 360, Rotate.Y_AXIS);
			Rotate rotateZ = new Rotate(Math.random() * 360, Rotate.Z_AXIS);

			sm.getTransforms().addAll(translate, rotateX, rotateY, rotateZ);

			spheroidGroup.getChildren().add(sm);
		}

		return spheroidGroup;
	}

	protected static Group aBunchOfTorus() {
		Group torusGroup = new Group();
		for(int i = 0; i < 30; i++) {
			Random r = new Random();
			// A lot of magic numbers in here that just artificially constrain
			// the math
			float randomRadius = (float) ((r.nextFloat() * 300) + 50);
			float randomTubeRadius = (float) ((r.nextFloat() * 100) + 1);
			int randomTubeDivisions = (int) ((r.nextFloat() * 64) + 1);
			int randomRadiusDivisions = (int) ((r.nextFloat() * 64) + 1);
			Color randomColor = new Color(r.nextDouble(), r.nextDouble(), r.nextDouble(), r.nextDouble());

			Torus torus = new Torus(randomTubeDivisions, randomRadiusDivisions, randomRadius, randomTubeRadius, randomColor);
			torus.setEmissiveLightingColor(randomColor);
			torus.setEmissiveLightingOn(r.nextBoolean());

			double translationX = Math.random() * 1024 * 1.95;
			if(Math.random() >= 0.5) {
				translationX *= -1;
			}
			double translationY = Math.random() * 1024 * 1.95;
			if(Math.random() >= 0.5) {
				translationY *= -1;
			}
			double translationZ = Math.random() * 1024 * 1.95;
			if(Math.random() >= 0.5) {
				translationZ *= -1;
			}
			Translate translate = new Translate(translationX, translationY, translationZ);
			Rotate rotateX = new Rotate(Math.random() * 360, Rotate.X_AXIS);
			Rotate rotateY = new Rotate(Math.random() * 360, Rotate.Y_AXIS);
			Rotate rotateZ = new Rotate(Math.random() * 360, Rotate.Z_AXIS);

			torus.getTransforms().addAll(translate, rotateX, rotateY, rotateZ);
			// torus.getTransforms().add(translate);
			torusGroup.getChildren().add(torus);

		}

		return torusGroup;
	}

	protected static Group aBunchOfTrapezoid() {
		Group trapezoidGroup = new Group();
		for(int i = 0; i < 50; i++) {
			Random r = new Random();
			// A lot of magic numbers in here that just artificially constrain
			// the math
			double randomSmallSize = (double) ((r.nextDouble() * 50) + 10);
			double randomBigSize = (double) ((r.nextDouble() * 100) + 50);
			double randomHeight = (double) ((r.nextDouble() * 50) + 25);
			double randomDepth = (double) ((r.nextDouble() * 50) + 25);

			Color randomColor = new Color(r.nextDouble(), r.nextDouble(), r.nextDouble(), r.nextDouble());

			Trapezoid trapezoid = new Trapezoid(randomSmallSize, randomBigSize, randomHeight, randomDepth, randomColor);
			// Trapezoid trapezoid = new Trapezoid(50, 100, 50, 50,
			// randomColor);
			trapezoid.setEmissiveLightingColor(randomColor);
			trapezoid.setEmissiveLightingOn(r.nextBoolean());
			trapezoid.setDrawMode(r.nextBoolean() ? DrawMode.FILL : DrawMode.LINE);

			double translationX = Math.random() * 1024;
			if(Math.random() >= 0.5) {
				translationX *= -1;
			}
			double translationY = Math.random() * 1024;
			if(Math.random() >= 0.5) {
				translationY *= -1;
			}
			double translationZ = Math.random() * 1024;
			if(Math.random() >= 0.5) {
				translationZ *= -1;
			}
			Translate translate = new Translate(translationX, translationY, translationZ);
			Rotate rotateX = new Rotate(Math.random() * 360, Rotate.X_AXIS);
			Rotate rotateY = new Rotate(Math.random() * 360, Rotate.Y_AXIS);
			Rotate rotateZ = new Rotate(Math.random() * 360, Rotate.Z_AXIS);

			trapezoid.getTransforms().addAll(translate, rotateX, rotateY, rotateZ);

			trapezoidGroup.getChildren().add(trapezoid);
		}

		return trapezoidGroup;
	}

	protected static Group aBunchOfOctahedron() {
		Group octahedronGroup = new Group();
		for(int i = 0; i < 100; i++) {
			Random r = new Random();
			// A lot of magic numbers in here that just artificially constrain
			// the math
			float randomHypotenuse = (float) ((r.nextFloat() * 300) + 25);
			float randomHeight = (float) ((r.nextFloat() * 200) + 25);
			Color randomColor = new Color(r.nextDouble(), r.nextDouble(), r.nextDouble(), r.nextDouble());

			Octahedron octahedron = new Octahedron(randomHypotenuse, randomHeight, randomColor);
			octahedron.setEmissiveLightingColor(randomColor);
			octahedron.setEmissiveLightingOn(r.nextBoolean());
			octahedron.setDrawMode(r.nextBoolean() ? DrawMode.FILL : DrawMode.LINE);

			double translationX = Math.random() * 1024;
			if(Math.random() >= 0.5) {
				translationX *= -1;
			}
			double translationY = Math.random() * 1024;
			if(Math.random() >= 0.5) {
				translationY *= -1;
			}
			double translationZ = Math.random() * 1024;
			if(Math.random() >= 0.5) {
				translationZ *= -1;
			}
			Translate translate = new Translate(translationX, translationY, translationZ);
			Rotate rotateX = new Rotate(Math.random() * 360, Rotate.X_AXIS);
			Rotate rotateY = new Rotate(Math.random() * 360, Rotate.Y_AXIS);
			Rotate rotateZ = new Rotate(Math.random() * 360, Rotate.Z_AXIS);

			octahedron.getTransforms().addAll(translate, rotateX, rotateY, rotateZ);

			octahedronGroup.getChildren().add(octahedron);
		}

		return octahedronGroup;
	}

}
