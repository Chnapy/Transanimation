/*
 * 
 * 
 * 
 */
package Elements.actions;

import Elements.GraphicElement;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

/**
 * MoveAction.java
 *
 */
public class MoveAction extends Action<GraphicElement, TranslateTransition> {

	public final Interpolator interpolator;

	public MoveAction(double dx, double dy, Interpolator interpolator, long duration) {
		super(new TranslateTransition(Duration.millis(duration)), interpolator);
		this.interpolator = interpolator;
		transition.setByX(dx);
		transition.setByY(dy);
	}

}
