package fr.xs.jtk.graphics.fx.api.windows;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MutableWindow implements ContentWindow {
	
	Stage 			externalWindowMode = null;
	InternalWindow 	internalWindowMode = null;
	boolean         internal           = true;
	
	Pane            owner;
	Parent          content;
	String 			title;

	public MutableWindow(Pane _owner, String _title) {
		super();

		owner   = _owner;
		title   = _title;
		content = null;
	}
	public MutableWindow(Pane _owner, String _title, Parent _content) {
		this(_owner, _title);

		content = _content;
	}

	private void createExternalWindow() {
		externalWindowMode = new Stage();
		externalWindowMode.setTitle(title);
		externalWindowMode.hide();
	}
	private void createInternalWindow() {
		internalWindowMode = new InternalWindow(owner, title, content);
		internalWindowMode.setLayoutX(50);
		internalWindowMode.setLayoutY(50);
	}

	public void makeInternal() {
		if(externalWindowMode != null && externalWindowMode.isShowing()) {
			externalWindowMode.hide();
		}

		if(internalWindowMode == null)
			createInternalWindow();

		internal = true;
	}
	public void makeExternal() {
		if(externalWindowMode == null)
			createExternalWindow();

		externalWindowMode.setScene(new Scene(content));
		externalWindowMode.sizeToScene();
		externalWindowMode.show();
		internal = false;
	}

	public void setContent(Parent _content) {
		content = _content;
	}
	
	public void show() {
		if(internal) {
			if(internalWindowMode.getParent() == null)
				owner.getChildren().add(internalWindowMode);
		} else {
			externalWindowMode.show();
		}
	}
	public void hide() {
		if(internal) {
			owner.getChildren().remove(internalWindowMode);
		} else {
			externalWindowMode.hide();
		}
	}

	@Override
	public Node get() {
		return content;
	}

}
