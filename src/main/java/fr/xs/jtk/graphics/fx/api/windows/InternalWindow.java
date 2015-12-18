package fr.xs.jtk.graphics.fx.api.windows;

import fr.xs.jtk.math.type.BoundaryBox;
import fr.xs.jtk.math.type.Vector2f;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public class InternalWindow extends Region implements ContentWindow {
	private static final int borderMagnet = 5;
	private boolean RESIZE_BOTTOM;
	private boolean RESIZE_RIGHT;
	
	enum RESIZE_MODE { RESIZE, SCALE };

	class WindowHeader extends BorderPane {
		Label  lblTitle;
		HBox   layoutButton;
		Button btnMaximize;
		Button btnMinimize;
		Button btnClose;

		Node   contentWindow;

		WindowHeader(String _title, InternalWindow _window) {
			super();

			layoutButton = new HBox();
			lblTitle     = new Label(_title);
			btnMaximize  = new Button("+");
			btnMinimize  = new Button("-");
			btnClose     = new Button("x");

			layoutButton.getChildren().addAll(btnMinimize, btnMaximize, btnClose);

			setLeft(lblTitle);
			setRight(layoutButton);
			setStyle("-fx-background-color: #dfe5e6; -fx-padding: 1");

			final WindowHeader This = this;
			final Vector2f paneDim = new Vector2f();
			btnClose.setOnMouseReleased( (e) -> _window.owner.getChildren().remove(_window) );
			btnMaximize.setOnMouseReleased( (e) -> _window.setFullscreen(!_window.getFullscreen()) );
			btnMinimize.setOnMouseReleased( (e) -> {
				if(paneDim.x == 0.0) {
					paneDim.x = (float) _window.pane.getChildren().get(1).getBoundsInParent().getWidth();
					paneDim.y = (float) _window.pane.getChildren().get(1).getBoundsInParent().getHeight();
				}

				if(_window.pane.getChildren().size() == 1) {
					_window.pane.setCenter(contentWindow);
					_window.pane.getChildren().get(1).setVisible(true);
					_window.pane.setPrefHeight(This.getBoundsInParent().getHeight() + paneDim.y);

//					if(true) { _window.pane.getChildren().get(1).setScaleX(1);_window.pane.getChildren().get(1).setScaleY(1); }
				} else {
					contentWindow = _window.pane.getChildren().get(1);
					_window.pane.getChildren().get(1).setVisible(false);
					_window.pane.getChildren().remove(1);
					_window.pane.setPrefHeight(This.getBoundsInParent().getHeight());

//					if(true) { _window.pane.getChildren().get(1).setScaleX(0);_window.pane.getChildren().get(1).setScaleY(0); }
				}
			} );
		}
	}

	final WindowHeader header;
	final BorderPane   pane;
	final Pane         owner;
	Node               content;

	Bounds       bndDefault = null, bndCurrent = null; 
	RESIZE_MODE  mode = RESIZE_MODE.RESIZE;

	boolean	     fullscreen;
	BoundaryBox  lastKnownBounds;

	public InternalWindow(Pane _owner, String _title, Node _content) {
		super();
		owner   = _owner;
		header  = new WindowHeader(_title, this);
		pane    = new BorderPane();
		pane.setTop(header);
		if(_content != null)
			pane.setCenter(content = _content);
		pane.setStyle("-fx-background-color: rgba(100, 100, 100, 1.0); -fx-border-width: 1; -fx-border-color: black");

		getChildren().add(pane);

		addListener();
	}

	public void setScalable(boolean _enable) {
		mode = _enable ? RESIZE_MODE.SCALE : RESIZE_MODE.RESIZE;
	}
	public boolean getFullscreen() {
		return fullscreen;
	}
	public void setFullscreen(boolean _enable) {
		fullscreen = _enable;
		
		if(fullscreen) {
			lastKnownBounds = new BoundaryBox(
					layoutXProperty().doubleValue(),
					layoutYProperty().doubleValue(),
					widthProperty().doubleValue(),
					heightProperty().doubleValue());

			pane.setPrefSize(owner.widthProperty().doubleValue(), owner.heightProperty().doubleValue());
			pane.prefWidthProperty().bind(owner.widthProperty());
			pane.prefHeightProperty().bind(owner.heightProperty());
			layoutXProperty().set(0.0);
			layoutYProperty().set(0.0);
		} else {
			pane.prefWidthProperty().unbind();
			pane.prefHeightProperty().unbind();
			pane.setPrefSize(lastKnownBounds.getWidth(), lastKnownBounds.getHeight());
			layoutXProperty().set(lastKnownBounds.getMinX());
			layoutYProperty().set(lastKnownBounds.getMinY());
		}
		
	}

	public Node getContent() {
		return content;
	}
	public void setContent(Node _content) {
		pane.setCenter(_content);
	}

	public void addListener() {
		final Vector2f dragDelta = new Vector2f();
		header.setOnMousePressed(mouseEvent -> {
			dragDelta.x = (float) (getLayoutX() - mouseEvent.getScreenX());
			dragDelta.y = (float) (getLayoutY() - mouseEvent.getScreenY());

			toFront();
		});
		header.setOnMouseDragged(mouseEvent -> {
			setLayoutX(mouseEvent.getScreenX() + dragDelta.x);
			setLayoutY(mouseEvent.getScreenY() + dragDelta.y);
		});

		setOnMouseClicked( (e) -> toFront() );

		setOnMouseMoved(mouseEvent -> {
			final int deltaMouseY = 140;
			bndCurrent = boundsInParentProperty().get();

			if (Math.abs(mouseEvent.getSceneX() - bndCurrent.getMaxX()) < borderMagnet && Math.abs(mouseEvent.getSceneY() - bndCurrent.getMaxY() - deltaMouseY) < borderMagnet) {
				RESIZE_RIGHT = true;
				RESIZE_BOTTOM = true;
				setCursor(Cursor.SE_RESIZE);
			} else if (Math.abs(mouseEvent.getSceneX() - bndCurrent.getMaxX()) < borderMagnet && Math.abs(mouseEvent.getSceneY() - bndCurrent.getMaxY() - deltaMouseY) > borderMagnet) {
				RESIZE_RIGHT = true;
				RESIZE_BOTTOM = false;
				setCursor(Cursor.W_RESIZE);
			} else if (Math.abs(mouseEvent.getSceneX() - bndCurrent.getMaxX()) > borderMagnet && Math.abs(mouseEvent.getSceneY() - bndCurrent.getMaxY() - deltaMouseY) < borderMagnet) {
				RESIZE_RIGHT = false;
				RESIZE_BOTTOM = true;
				setCursor(Cursor.S_RESIZE);
			} else {
				RESIZE_BOTTOM = false;
				RESIZE_RIGHT = false;
				setCursor(Cursor.DEFAULT);
			}
		});

		setOnMouseDragged(mouseEvent -> {
			if(bndDefault == null)
				bndDefault = boundsInLocalProperty().get();

			Region region = (Region) pane; //getChildren().get(0);
			if(RESIZE_BOTTOM && RESIZE_RIGHT) {
				switch(mode) {
				case RESIZE : region.setPrefSize(mouseEvent.getX(), mouseEvent.getY()); break;
				case SCALE  : region.setScaleX(mouseEvent.getX() / bndDefault.getWidth());
							  region.setScaleY(mouseEvent.getY() / bndDefault.getHeight());
							  break;
				}
			} else if (RESIZE_RIGHT) {
				switch(mode) {
				case RESIZE : region.setPrefWidth(mouseEvent.getX()); break;
				case SCALE  : region.setScaleX(mouseEvent.getX() / bndDefault.getWidth());
							  break;
				}
			} else if (RESIZE_BOTTOM) {
				switch(mode) {
				case RESIZE : region.setPrefHeight(mouseEvent.getY()); break;
				case SCALE  : region.setScaleY(mouseEvent.getY() / bndDefault.getHeight());
							  break;
				}
			}
		});
	}

	@Override
	public Node get() {
		return this;
	}

}
