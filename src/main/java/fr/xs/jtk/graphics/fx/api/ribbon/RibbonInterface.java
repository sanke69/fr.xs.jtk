package fr.xs.jtk.graphics.fx.api.ribbon;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Tab;

public interface RibbonInterface {

	public boolean              isTabAlreadyAdded();
	public Tab                  getTab();
	public Tab                  getTab(boolean _addToRibbonBar);
	public ObservableList<Node> getChildren();

}
