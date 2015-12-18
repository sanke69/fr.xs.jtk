package fr.xs.jtk.graphics.fx.api.controls;

import javafx.beans.binding.Bindings;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ToggleButtonImage extends ToggleButton {
	private Image selected, unselected;
	private ImageView    toggleImage; 

	public ToggleButtonImage() {
		super();
		toggleImage = new ImageView();
		setGraphic(toggleImage);
	}
	public ToggleButtonImage(Image _sel, Image _unsel) {
		this();

		selected   = _sel;
		unselected = _unsel;

		toggleImage.imageProperty().bind(Bindings
	      .when(selectedProperty())
	        .then(selected)
	        .otherwise(unselected)
	    );
	}

}