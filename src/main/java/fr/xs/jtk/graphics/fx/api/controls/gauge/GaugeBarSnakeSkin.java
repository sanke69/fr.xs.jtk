package fr.xs.jtk.graphics.fx.api.controls.gauge;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;

public class GaugeBarSnakeSkin implements Skin<GaugeBar> {

	private static final int GAUGE_BORDER = 2;
	private static final int GAUGE_MAX_SIZE = 10;

	private final GaugeBar gaugeBar;
	private Group rootNode;
	private final int size = 75;
	
	// For animation
	private Thread animator;
	private long   tics;

	public GaugeBarSnakeSkin(GaugeBar gaugeBar) {
		this.gaugeBar = gaugeBar;
		hookEventHandler();
	}
	
	public void startAnimate() {
		if(animator != null)
			return ;

		tics = 0;
		animator = new Thread(() -> {
			while(tics >= 0) {
				Platform.runLater(() -> redraw());
				try { Thread.sleep(40); } catch(InterruptedException e) { }
			}
		});
		animator.start();
	}
	public void stopAnimate() {
		if(animator == null)
			return ;

		tics = -1;
		animator.interrupt();
		animator = null;
	}

	private void hookEventHandler() {
		this.gaugeBar.addEventHandler(GaugeBar.EVENT_TYPE_CHANGE_VALUE, new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				redraw();
			}
		});
		this.gaugeBar.addEventHandler(GaugeBar.EVENT_TYPE_CHANGE_MAX_VALUE, new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				redraw();
			}
		});
	}

	@Override
	public GaugeBar getSkinnable() {
		return this.gaugeBar;
	}

	@Override
	public Node getNode() {
		if(this.rootNode == null) {
			this.rootNode = new Group();
			redraw();
		}
		return this.rootNode;
	}

	protected void redraw() {
		List<Node> rootChildren = new ArrayList<Node>();
		rootChildren.add(createBackground());
		rootChildren.add(createGauge());
		rootChildren.add(createGaugeBlend());
		rootChildren.add(createBorder());
		this.rootNode.getChildren().setAll(rootChildren);
	}

	@Override
	public void dispose() {
		// nothing to do
	}

	private Node createBackground() {
		return new Circle(this.size, this.size, this.size + 1);
	}

	private Node createGauge() {
		Stop[] stops = new Stop[] { new Stop(0, Color.LIGHTGREEN), new Stop(1, Color.DARKGREEN) };
		Circle circle = new Circle(this.size, this.size, this.size - 2 * GAUGE_BORDER);
		circle.setFill(new LinearGradient(1, 0, 0.5, 1, true, CycleMethod.NO_CYCLE, stops));
		circle.getStyleClass().add("gauge");
		return circle;
	}

	private Node createGaugeBlend() {
		Group group = new Group();

		float arcBlendDegrees = (1.0f - (float) this.gaugeBar.value / this.gaugeBar.maxValue) * 360;
		Arc arcBlend = new Arc(this.size, this.size, this.size, this.size, -90, arcBlendDegrees);
		arcBlend.setType(ArcType.ROUND);
		arcBlend.setFill(Color.BLACK);
		arcBlend.setRotate((tics += 5 % 360));

		Circle circleBlend = new Circle(this.size, this.size, this.size - 2 * GAUGE_MAX_SIZE);
		circleBlend.setFill(Color.BLACK);

		group.getChildren().setAll(arcBlend, circleBlend);
		return group;
	}

	private Node createBorder() {
		Circle circle = new Circle(this.size, this.size, this.size);
		circle.setFill(null);
		circle.setStroke(Color.WHITE);
		return circle;
	}
}