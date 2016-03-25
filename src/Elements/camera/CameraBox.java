/*
 * 
 * 
 * 
 */
package Elements.camera;

import Elements.Element;
import javafx.animation.Interpolator;
import javafx.animation.SequentialTransition;
import javafx.scene.Group;

/**
 * CameraBox.java
 *
 */
public class CameraBox extends Group implements Element {

//	public Group group;

	protected SequentialTransition sTransition;

	public CameraBox() {
		sTransition = new SequentialTransition(this);
	}

	@Override
	public SequentialTransition getSTransition() {
		return sTransition;
	}

	@Override
	public Element move(double x, double y, Interpolator interpolator, long duration) {
		return Element.super.move(-x, -y, interpolator, duration);
	}
	
	public void setPosition(double x, double y) {
		this.setTranslateX(x);
		this.setTranslateY(y);
	}

	@Override
	public void flipAction() {
		setScaleX(-getScaleX());
	}

}
