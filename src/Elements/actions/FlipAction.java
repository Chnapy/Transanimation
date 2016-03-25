/*
 * 
 * 
 * 
 */
package Elements.actions;

import Elements.Element;
import Elements.actions.CustomAction.CustomTransition;
import javafx.animation.Interpolator;

/**
 * FlipAction.java
 *
 */
public class FlipAction extends Action {

	public FlipAction() {
		super(new FlipTransition(), Interpolator.LINEAR);
	}

	static class FlipTransition extends CustomTransition {

		public FlipTransition() {
			super();
		}

		@Override
		protected void onStart() {
			((Element) getParentTargetNode()).flipAction();
		}

	}

}
