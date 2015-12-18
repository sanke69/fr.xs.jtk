package fr.xs.jtk.graphics.fx3d;

import javafx.application.Application; 
import javafx.scene.AmbientLight; 
import javafx.scene.PerspectiveCamera; 
import javafx.scene.Scene; 
import javafx.scene.layout.Pane; 
import javafx.scene.paint.Color; 
import javafx.scene.paint.PhongMaterial; 
import javafx.scene.shape.Box; 
import javafx.scene.shape.Cylinder; 
import javafx.scene.shape.DrawMode; 
import javafx.scene.shape.Sphere; 
import javafx.stage.Stage; 
  
public class TesselationTest extends Application { 
  
    @Override 
    public void start(Stage primaryStage) throws Exception { 
        final PhongMaterial red = new PhongMaterial(Color.RED); 
        final PhongMaterial green = new PhongMaterial(Color.GREEN); 
        final PhongMaterial blue = new PhongMaterial(Color.BLUE); 
        final Box cube = new Box(200, 200, 200); 
        cube.setLayoutX(150); 
        cube.setLayoutY(800); 
        cube.setDrawMode(DrawMode.LINE); 
        cube.setMaterial(red); 
        final Cylinder cylinder = new Cylinder(150, 50); 
        cylinder.setLayoutX(500); 
        cylinder.setLayoutY(800); 
        cylinder.setDrawMode(DrawMode.LINE); 
        cylinder.setMaterial(green); 
        final Sphere sphere = new Sphere(100); 
        sphere.setLayoutX(850); 
        sphere.setLayoutY(800); 
        sphere.setDrawMode(DrawMode.LINE); 
        sphere.setMaterial(blue); 
        final AmbientLight light = new AmbientLight(Color.WHITE); 
        final Pane root = new Pane(); 
        root.setStyle("-fx-background-color: transparent;"); 
        root.getChildren().addAll(cube, cylinder, sphere, light); 
        final int[] tesselations = {1, 5, 10, 50, 100}; 
        for (int index = 0; index < tesselations.length; index++) { 
            final int dx = 1000 / tesselations.length; 
            final int tesselation = tesselations[index]; 
            final Sphere tesselatedSphere = new Sphere(75, tesselation); 
            tesselatedSphere.setDrawMode(DrawMode.LINE); 
            root.getChildren().add(tesselatedSphere); 
            tesselatedSphere.setTranslateX(100 + dx * index); 
            tesselatedSphere.setTranslateY(400); 
            final Cylinder tesselatedCylinder = new Cylinder(75, 50, tesselation); 
            tesselatedCylinder.setDrawMode(DrawMode.LINE); 
            root.getChildren().add(tesselatedCylinder); 
            tesselatedCylinder.setTranslateX(100 + dx * index); 
            tesselatedCylinder.setTranslateY(100); 
        } 
        final Scene scene = new Scene(root, 1000, 1000); 
        scene.setFill(Color.BLACK); 
        scene.setCamera(new PerspectiveCamera()); 
        primaryStage.setScene(scene); 
        primaryStage.setTitle("Test_Triangle"); 
        primaryStage.show(); 
    } 
  
    public static void main(String... args) { 
        Application.launch(args); 
    } 
}