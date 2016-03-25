/*
 * 
 * 
 * 
 */
package Elements.actions;

import javafx.animation.Interpolator;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 * PauseAction.java
 *
 */
public class PauseAction extends Action {
	
	public PauseAction(long duration) {
		super(new PauseTransition(Duration.millis(duration)), Interpolator.DISCRETE);
	}
	
}
