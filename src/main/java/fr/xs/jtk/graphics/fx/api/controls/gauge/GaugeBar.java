package fr.xs.jtk.graphics.fx.api.controls.gauge;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.control.Control;

public class GaugeBar extends Control {
	public static final EventType<Event> EVENT_TYPE_CHANGE_VALUE = new EventType<Event>("Change");
	public static final EventType<Event> EVENT_TYPE_CHANGE_MAX_VALUE = new EventType<Event>("ChangeMax");

	public enum Style { idle, progress, modern }

	public Style style;
	protected int maxValue = 100;
	protected int value = this.maxValue;

	public GaugeBar(Style _style) {
		switch(_style) {
		case idle : 	setSkin(new GaugeBarSnakeSkin(this));
						break;
		case progress : setSkin(new GaugeBarHollowDiscSkin(this));
						break;
		case modern :	setSkin(new GaugeBarModernSkin(this));
						break;
		}
	}

	public int getMaxValue() {
		return this.maxValue;
	}

	public void setMaxValue(int maxValue) {
		if(maxValue < this.value)
			throw new IllegalArgumentException("Max value must be bigger than value!");
		this.maxValue = maxValue;

    	Platform.runLater(() -> fireEvent(new Event(Integer.valueOf(maxValue), this, EVENT_TYPE_CHANGE_MAX_VALUE)) );
	}

	public void setValue(int value) {
		if(this.maxValue < value)
			throw new IllegalArgumentException("Value must be smaller than max value!");
		if(value < 0)
			throw new IllegalArgumentException("Value must be bigger than zero!");
		this.value = value;

    	Platform.runLater(() -> fireEvent(new Event(Integer.valueOf(value), this, EVENT_TYPE_CHANGE_VALUE)) );
	}

	public int getValue() {
		return this.value;
	}

}