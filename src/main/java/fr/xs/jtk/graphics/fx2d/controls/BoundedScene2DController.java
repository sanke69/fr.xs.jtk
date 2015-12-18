package fr.xs.jtk.graphics.fx2d.controls;

import fr.xs.jtk.graphics.fx.api.controls.fourways.FourWayNavControl;
import fr.xs.jtk.graphics.fx2d.BoundedScene2D;
import fr.xs.jtk.math.type.geom.Point2D;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class BoundedScene2DController extends VBox {
	public FourWayNavControl eyeNav;
	public ScrollBar         zoomBar;

	BoundedScene2D scene2D;

	private double dragStartX, dragStartY;

	public BoundedScene2DController(BoundedScene2D _subscene) {
		super();
		setAlignment(Pos.TOP_CENTER);
		setSpacing(10);
		setPrefSize(55, 275);
		setMaxSize(55, 275);
		setStyle("-fx-background-color: rgba(0,0,0,0.35); -fx-background-radius: 50;");
		StackPane.setAlignment(this, Pos.TOP_RIGHT);

		scene2D = _subscene;

		eyeNav = new FourWayNavControl();
		eyeNav.setListener((Side direction, double amount) -> {
			switch(direction) {
			case TOP :		getScene2D().referential().slipY(  getScene2D().referential().getBoundBox2D().getWidth()  / 6.0);
							break;
			case BOTTOM :	getScene2D().referential().slipY(- getScene2D().referential().getBoundBox2D().getHeight() / 6.0);
							break;
			case LEFT :		getScene2D().referential().slipX(- getScene2D().referential().getBoundBox2D().getWidth()  / 6.0);
							break;
			case RIGHT :	getScene2D().referential().slipX(  getScene2D().referential().getBoundBox2D().getHeight() / 6.0);
							break;
			}
		});

		zoomBar = new ScrollBar();
		zoomBar.setOrientation(Orientation.VERTICAL);
		zoomBar.setRotate(-180.0);
		zoomBar.setBlockIncrement(1);
		zoomBar.setVisibleAmount(1);

		zoomBar.setMin(1);
		zoomBar.maxProperty().bindBidirectional(scene2D.referential().getMaxScale());
		zoomBar.valueProperty().bindBidirectional(scene2D.referential().getScale());

		getChildren().addAll(eyeNav, zoomBar);

		// SCENE EVENT HANDLING FOR CAMERA NAV
		_subscene.parent().addEventHandler(MouseEvent.ANY, (MouseEvent event) -> {
			if(event.getEventType() == MouseEvent.MOUSE_PRESSED) {
				dragStartX = event.getSceneX();
				dragStartY = event.getSceneY();
			} else if(event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
				double xDelta = event.getSceneX() - dragStartX;
				double yDelta = event.getSceneY() - dragStartY;
				
				Point2D d2D = getScene2D().referential().getVectorIn2D(xDelta, yDelta);
				
				getScene2D().referential().slipX(- d2D.x);
				getScene2D().referential().slipY(  d2D.y);
				dragStartX = event.getSceneX();
				dragStartY = event.getSceneY();
			}
		});
		_subscene.parent().addEventHandler(ScrollEvent.ANY, (ScrollEvent event) -> {  
			System.out.println(getScene2D().referential());
			
			double current = zoomBar.getValue(), step = event.getDeltaY() > 0 ? 1 : - 1;
			current = current + step < zoomBar.getMin() ? zoomBar.getMin() : (current + step > zoomBar.getMax() ? zoomBar.getMax() : current + step);

			zoomBar.setValue(current);
		});

	}

	public BoundedScene2D getScene2D() {
		return scene2D;
	}

}
