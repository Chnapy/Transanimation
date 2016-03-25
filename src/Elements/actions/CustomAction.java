/*
 * 
 * 
 * 
 */
package Elements.actions;

import Elements.Element;
import Elements.actions.CustomAction.CustomTransition;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.util.Duration;

/**
 * CustomAction.java
 *
 */
public class CustomAction extends Action<Element, CustomTransition> {

	public CustomAction(CustomTransition transition, Interpolator interpolator) {
		super(transition, interpolator);
	}
	
	public static class CustomTransition extends Transition {

		private boolean used;

		public CustomTransition() {
			setCycleDuration(Duration.ONE);
			used = false;
			setOnFinished((t) -> onEnd());
		}

		public CustomTransition(Duration d) {
			this();
			setCycleDuration(d);
		}
		
		public CustomTransition(long d) {
			this(Duration.millis(d));
		}

		protected void onStart() {
		}

		protected void onEnd() {
		}

		protected void onInterpolate(double d) {
		}

		@Override
		protected void interpolate(double d) {
			if (d == 0) {
				return;
			}
			if (!used) {
				used = true;
				onStart();
			}
			onInterpolate(d);
		}

	}

}
