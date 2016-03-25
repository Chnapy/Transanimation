/*
 * 
 * 
 * 
 */
package Elements.actions;

import javafx.animation.Interpolator;
import javafx.animation.Transition;

/**
 * TempAction.java
 * 
 */
public class TempAction extends Action {

	public TempAction() {
		super(new TempTransition(), Interpolator.DISCRETE);
	}
	
	public static class TempTransition extends Transition {

		@Override
		protected void interpolate(double d) {
		}
		
	}

}
