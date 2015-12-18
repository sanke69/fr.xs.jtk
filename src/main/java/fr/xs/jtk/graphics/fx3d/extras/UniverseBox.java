package fr.xs.jtk.graphics.fx3d.extras;

import java.io.IOException;
import java.net.URL;

import fr.xs.jtk.format.ini.IniFile;
import fr.xs.jtk.helpers.MediaHelper;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;

public class UniverseBox extends Group {

	public enum SkyboxImageType {
		MULTIPLE, SINGLE
	}
	private SkyboxImageType imageType;

	private Image topImg, bottomImg, leftImg, rightImg, frontImg, backImg;

	private final ImageView top = new ImageView(), bottom = new ImageView(), left = new ImageView(), right = new ImageView(), back = new ImageView(), front = new ImageView();
	{
		top.setId("top ");
		bottom.setId("bottom ");
		left.setId("left ");
		right.setId("right ");
		back.setId("back ");
		front.setId("front ");
	}
	private final ImageView[] views = new ImageView[] { top, left, back, right, front, bottom };

	public UniverseBox(double size) {
		super();
		this.size.set(size);

		getChildren().addAll(views);
	}
	public UniverseBox(Image singleImg, double size) {
		this(size);
		this.imageType = SkyboxImageType.SINGLE;

		topImg = singleImg;

		loadImageViews();
	}
	public UniverseBox(Image topImg, Image bottomImg, Image leftImg, Image rightImg, Image frontImg, Image backImg, double size) {
		this(size);
		this.imageType = SkyboxImageType.MULTIPLE;

		this.topImg = topImg;
		this.bottomImg = bottomImg;
		this.leftImg = leftImg;
		this.rightImg = rightImg;
		this.frontImg = frontImg;
		this.backImg = backImg;

		loadImageViews();
	}
	public UniverseBox(String _cubemapName, double size) {
		this(size);
		this.imageType = SkyboxImageType.MULTIPLE;
		
		loadCubemap(MediaHelper.getURLForFile("cubemap/" + _cubemapName + ".cubemap"));

		loadImageViews();
	}
	
	private void loadCubemap(URL cubemapFile) {
		try {
			IniFile file = new IniFile(cubemapFile);

			String toSearchPath = cubemapFile.getPath().substring(0, cubemapFile.getPath().lastIndexOf('/'));
			MediaHelper.addToSearchPath(toSearchPath);
			topImg    = new Image(MediaHelper.getURLForFile(file.getString("CubeMap", "PositiveY", "")).toExternalForm());
		    bottomImg = new Image(MediaHelper.getURLForFile(file.getString("CubeMap", "NegativeY", "")).toExternalForm());
		    leftImg   = new Image(MediaHelper.getURLForFile(file.getString("CubeMap", "PositiveZ", "")).toExternalForm());
		    rightImg  = new Image(MediaHelper.getURLForFile(file.getString("CubeMap", "NegativeZ", "")).toExternalForm());
		    frontImg  = new Image(MediaHelper.getURLForFile(file.getString("CubeMap", "PositiveX", "")).toExternalForm());
		    backImg   = new Image(MediaHelper.getURLForFile(file.getString("CubeMap", "NegativeX", "")).toExternalForm());
			MediaHelper.removeFromSearchPath(toSearchPath);

		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private void loadImageViews() {
		for(ImageView iv : views) {
			iv.setSmooth(true);
			iv.setPreserveRatio(true);
		}

		validateImageType();
	}

	private void layoutViews() {
		for(ImageView v : views) {
			v.setFitWidth(getSize());
			v.setFitHeight(getSize());
		}

		back.setTranslateX(-0.5 * getSize());
		back.setTranslateY(-0.5 * getSize());
		back.setTranslateZ(-0.5 * getSize());

		front.setTranslateX(-0.5 * getSize());
		front.setTranslateY(-0.5 * getSize());
		front.setTranslateZ(0.5 * getSize());
		front.setRotationAxis(Rotate.Z_AXIS);
		front.setRotate(-180);
		front.getTransforms().add(new Rotate(180, front.getFitHeight() / 2, 0, 0, Rotate.X_AXIS));
		front.setTranslateY(front.getTranslateY() - getSize());

		top.setTranslateX(-0.5 * getSize());
		top.setTranslateY(-1 * getSize());
		top.setRotationAxis(Rotate.X_AXIS);
		top.setRotate(-270);

		bottom.setTranslateX(-0.5 * getSize());
		bottom.setTranslateY(0);
		bottom.setRotationAxis(Rotate.X_AXIS);
		bottom.setRotate(90);

		left.setTranslateX(-1 * getSize());
		left.setTranslateY(-0.5 * getSize());
		left.setRotationAxis(Rotate.Y_AXIS);
		left.setRotate(90);

		right.setTranslateX(0);
		right.setTranslateY(-0.5 * getSize());
		right.setRotationAxis(Rotate.Y_AXIS);
		right.setRotate(-90);

	}

	/**
	 * for single image creates viewports and sets all views(image) to singleImg
	 * for multiple... sets images per view.
	 */
	private void validateImageType() {
		switch(imageType) {
		case SINGLE: 	loadSingleImageViewports(); break;
		case MULTIPLE: 	setMultipleImages(); break;
		}
	}

	/**
	 * this will layout the viewports to this style pattern 
	 *  ____ 
	 * |top |
	 * |____|____ ____ ____ 
	 * |left|fwd |rght|back| 
	 * |____|____|____|____| 
	 * |bot |
	 * |____|
	 * 
	 */
	private void loadSingleImageViewports() {
		layoutViews();
		double width = topImg.getWidth(), height = topImg.getHeight();

		// simple chack to see if cells will be square
		if(width / 4 != height / 3) {
			throw new UnsupportedOperationException("Image does not comply with size constraints");
		}
		double cellSize = topImg.getWidth() - topImg.getHeight();

		recalculateSize(cellSize);

		double topx = cellSize, topy = 0,

		botx = cellSize, boty = cellSize * 2,

		leftx = 0, lefty = cellSize,

		rightx = cellSize * 2, righty = cellSize,

		fwdx = cellSize, fwdy = cellSize,

		backx = cellSize * 3, backy = cellSize;

		// add top padding x+, y+, width-, height
		top.setViewport(new Rectangle2D(topx, topy, cellSize, cellSize));

		// add left padding x, y+, width, height-
		left.setViewport(new Rectangle2D(leftx, lefty, cellSize - 1, cellSize - 1));

		// add front padding x+, y+, width-, height
		back.setViewport(new Rectangle2D(fwdx, fwdy, cellSize, cellSize));

		// add right padding x, y+, width, height-
		right.setViewport(new Rectangle2D(rightx, righty, cellSize, cellSize));

		// add back padding x, y+, width, height-
		front.setViewport(new Rectangle2D(backx + 1, backy - 1, cellSize - 1, cellSize - 1));

		// add bottom padding x+, y, width-, height-
		bottom.setViewport(new Rectangle2D(botx, boty, cellSize, cellSize));

		for(ImageView v : views) {
			v.setImage(topImg);
			// System.out.println(v.getId() + v.getViewport() + cellSize);
		}
	}

	private void recalculateSize(double cell) {
		double factor = Math.floor(getSize() / cell);
		setSize(cell * factor);
	}

	private void setMultipleImages() {
		layoutViews();

		back.setImage(frontImg);
		front.setImage(backImg);
		top.setImage(topImg);
		bottom.setImage(bottomImg);
		left.setImage(leftImg);
		right.setImage(rightImg);
	}

	private final DoubleProperty size = new SimpleDoubleProperty() {
		@Override
		protected void invalidated() {
			if(imageType == null) return ;

			switch(imageType) {
			case SINGLE:
				layoutViews();
				break;
			case MULTIPLE:
				break;
			}

		}
	};

	public final double getSize() {
		return size.get();
	}

	public final void setSize(double value) {
		size.set(value);
	}

	public DoubleProperty sizeProperty() {
		return size;
	}

}
